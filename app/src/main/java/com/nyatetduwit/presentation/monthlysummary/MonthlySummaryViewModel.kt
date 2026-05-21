package com.nyatetduwit.presentation.monthlysummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.usecase.category.GetCategoriesUseCase
import com.nyatetduwit.domain.usecase.transaction.GetActiveDaysCountUseCase
import com.nyatetduwit.domain.usecase.transaction.GetBiggestExpenseUseCase
import com.nyatetduwit.domain.usecase.transaction.GetDailyExpenseTrendUseCase
import com.nyatetduwit.domain.usecase.transaction.GetMonthComparisonUseCase
import com.nyatetduwit.domain.usecase.transaction.GetSumByTypeAndDateRangeUseCase
import com.nyatetduwit.domain.usecase.transaction.GetTopCategoriesByExpenseUseCase
import com.nyatetduwit.domain.usecase.transaction.GetTransactionCountUseCase
import com.nyatetduwit.domain.usecase.transaction.MonthComparison
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class MonthlySummaryUiState(
    val selectedYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    val selectedMonth: Int = Calendar.getInstance().get(Calendar.MONTH),
    val totalIncome: Long = 0L,
    val totalExpense: Long = 0L,
    val netSavings: Long = 0L,
    val topCategories: List<TopCategoryItem> = emptyList(),
    val dailyTrend: List<DailyTrendItem> = emptyList(),
    val biggestExpense: Transaction? = null,
    val comparison: MonthComparison? = null,
    val activeDays: Int = 0,
    val transactionCount: Int = 0,
    val isLoading: Boolean = true,
)

data class TopCategoryItem(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: String,
    val categoryColor: String,
    val total: Long,
)

data class DailyTrendItem(
    val day: String,
    val dayNumber: Int,
    val amount: Long,
)

@HiltViewModel
class MonthlySummaryViewModel @Inject constructor(
    private val getSumByTypeAndDateRangeUseCase: GetSumByTypeAndDateRangeUseCase,
    private val getTopCategoriesByExpenseUseCase: GetTopCategoriesByExpenseUseCase,
    private val getDailyExpenseTrendUseCase: GetDailyExpenseTrendUseCase,
    private val getBiggestExpenseUseCase: GetBiggestExpenseUseCase,
    private val getMonthComparisonUseCase: GetMonthComparisonUseCase,
    private val getActiveDaysCountUseCase: GetActiveDaysCountUseCase,
    private val getTransactionCountUseCase: GetTransactionCountUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MonthlySummaryUiState())
    val uiState: StateFlow<MonthlySummaryUiState> = _uiState.asStateFlow()

    init {
        loadMonthlySummary()
    }

    fun setMonth(year: Int, month: Int) {
        _uiState.update { it.copy(selectedYear = year, selectedMonth = month, isLoading = true) }
        loadMonthlySummary()
    }

    fun goToPreviousMonth() {
        val current = _uiState.value
        val calendar = Calendar.getInstance()
        calendar.set(current.selectedYear, current.selectedMonth, 1)
        calendar.add(Calendar.MONTH, -1)
        setMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))
    }

    fun goToNextMonth() {
        val current = _uiState.value
        val calendar = Calendar.getInstance()
        calendar.set(current.selectedYear, current.selectedMonth, 1)
        calendar.add(Calendar.MONTH, 1)
        val now = Calendar.getInstance()
        if (calendar.get(Calendar.YEAR) > now.get(Calendar.YEAR) ||
            (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) > now.get(Calendar.MONTH))
        ) {
            return
        }
        setMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))
    }

    private fun loadMonthlySummary() {
        val state = _uiState.value
        val calendar = Calendar.getInstance()
        calendar.set(state.selectedYear, state.selectedMonth, 1, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val monthStart = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.MILLISECOND, -1)
        val monthEnd = calendar.timeInMillis

        val prevCalendar = Calendar.getInstance()
        prevCalendar.set(state.selectedYear, state.selectedMonth, 1, 0, 0, 0)
        prevCalendar.set(Calendar.MILLISECOND, 0)
        prevCalendar.add(Calendar.MONTH, -1)
        val prevMonthStart = prevCalendar.timeInMillis

        prevCalendar.add(Calendar.MONTH, 1)
        prevCalendar.add(Calendar.MILLISECOND, -1)
        val prevMonthEnd = prevCalendar.timeInMillis

        viewModelScope.launch {
            try {
                val categoriesFlow = getCategoriesUseCase()
                val topCategoriesRawFlow = getTopCategoriesByExpenseUseCase(monthStart, monthEnd, 5)

                combine(topCategoriesRawFlow, categoriesFlow) { topRaw, allCategories ->
                    val categoryMap = allCategories.associateBy { it.id }
                    topRaw.map { (categoryId, total) ->
                        val category = categoryMap[categoryId]
                        TopCategoryItem(
                            categoryId = categoryId,
                            categoryName = category?.name ?: "Lainnya",
                            categoryIcon = category?.icon ?: "category",
                            categoryColor = category?.color ?: "#808080",
                            total = total,
                        )
                    }
                }.collect { topCategories ->
                    _uiState.update { it.copy(topCategories = topCategories) }
                }
            } catch (e: Exception) {
            }
        }

        viewModelScope.launch {
            try {
                getDailyExpenseTrendUseCase(monthStart, monthEnd).collect { trend ->
                    val dailyTrendItems = trend.map { (day, amount) ->
                        val dayNumber = day.split("-").lastOrNull()?.toIntOrNull() ?: 0
                        DailyTrendItem(day = day, dayNumber = dayNumber, amount = amount)
                    }
                    _uiState.update { it.copy(dailyTrend = dailyTrendItems) }
                }
            } catch (e: Exception) {
            }
        }

        viewModelScope.launch {
            try {
                val biggestExpense = getBiggestExpenseUseCase(monthStart, monthEnd)
                _uiState.update { it.copy(biggestExpense = biggestExpense) }
            } catch (e: Exception) {
            }
        }

        viewModelScope.launch {
            try {
                getMonthComparisonUseCase(monthStart, monthEnd, prevMonthStart, prevMonthEnd).collect { comparison ->
                    _uiState.update { it.copy(comparison = comparison) }
                }
            } catch (e: Exception) {
            }
        }

        viewModelScope.launch {
            try {
                val activeDays = getActiveDaysCountUseCase(monthStart, monthEnd)
                val transactionCount = getTransactionCountUseCase(monthStart, monthEnd)
                _uiState.update { it.copy(activeDays = activeDays, transactionCount = transactionCount) }
            } catch (e: Exception) {
            }
        }

        viewModelScope.launch {
            try {
                val incomeFlow = getSumByTypeAndDateRangeUseCase(TransactionType.INCOME.value, monthStart, monthEnd)
                val expenseFlow = getSumByTypeAndDateRangeUseCase(TransactionType.EXPENSE.value, monthStart, monthEnd)

                combine(incomeFlow, expenseFlow) { income, expense ->
                    Pair(income, expense)
                }.collect { (income, expense) ->
                    _uiState.update {
                        it.copy(
                            totalIncome = income,
                            totalExpense = expense,
                            netSavings = income - expense,
                            isLoading = false,
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
