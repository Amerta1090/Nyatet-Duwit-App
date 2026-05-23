package com.nyatetduwit.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.repository.SettingsRepository
import com.nyatetduwit.domain.usecase.account.GetTotalBalanceUseCase
import com.nyatetduwit.domain.usecase.category.GetCategoriesUseCase
import com.nyatetduwit.domain.usecase.habit.DailyAllowance
import com.nyatetduwit.domain.usecase.habit.HabitTracker
import com.nyatetduwit.domain.usecase.habit.ProgressData
import com.nyatetduwit.domain.usecase.habit.StreakData
import com.nyatetduwit.domain.usecase.habit.WeeklyCheckInData
import com.nyatetduwit.domain.usecase.transaction.GetMonthlyExpenseUseCase
import com.nyatetduwit.domain.usecase.transaction.GetMonthlyIncomeUseCase
import com.nyatetduwit.domain.usecase.transaction.GetRecentTransactionsUseCase
import com.nyatetduwit.domain.usecase.transaction.GetSumByTypeAndDateRangeUseCase
import com.nyatetduwit.domain.usecase.transaction.GetTopCategoriesByExpenseUseCase
import com.nyatetduwit.domain.usecase.transaction.GetTransactionCountUseCase
import com.nyatetduwit.domain.model.Budget
import com.nyatetduwit.domain.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class DashboardUiState(
    val totalBalance: Long = 0L,
    val monthlyIncome: Long = 0L,
    val monthlyExpense: Long = 0L,
    val topCategories: List<TopCategoryItem> = emptyList(),
    val recentTransactions: List<Transaction> = emptyList(),
    val isBalanceVisible: Boolean = true,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val weeklyCheckIn: WeeklyCheckInData? = null,
    val showWeeklyCheckIn: Boolean = false,
    val streakData: StreakData = StreakData(),
    val progressData: ProgressData? = null,
    val dailyAllowance: DailyAllowance? = null,
)

data class TopCategoryItem(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: String,
    val categoryColor: String,
    val total: Long,
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getTotalBalanceUseCase: GetTotalBalanceUseCase,
    private val getSumByTypeAndDateRangeUseCase: GetSumByTypeAndDateRangeUseCase,
    private val getTopCategoriesByExpenseUseCase: GetTopCategoriesByExpenseUseCase,
    private val getRecentTransactionsUseCase: GetRecentTransactionsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val settingsRepository: SettingsRepository,
    private val habitTracker: HabitTracker,
    private val getMonthlyExpenseUseCase: GetMonthlyExpenseUseCase,
    private val getMonthlyIncomeUseCase: GetMonthlyIncomeUseCase,
    private val getTransactionCountUseCase: GetTransactionCountUseCase,
    private val budgetRepository: BudgetRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val currentMonthStart: Long
    private val currentMonthEnd: Long
    private val currentWeekStart: Long
    private val currentWeekEnd: Long
    private var loadDataJob: Job? = null

    init {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        currentMonthStart = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.MILLISECOND, -1)
        currentMonthEnd = calendar.timeInMillis

        val weekCal = Calendar.getInstance()
        weekCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        weekCal.set(Calendar.HOUR_OF_DAY, 0)
        weekCal.set(Calendar.MINUTE, 0)
        weekCal.set(Calendar.SECOND, 0)
        weekCal.set(Calendar.MILLISECOND, 0)
        currentWeekStart = weekCal.timeInMillis

        weekCal.add(Calendar.DAY_OF_WEEK, 6)
        weekCal.set(Calendar.HOUR_OF_DAY, 23)
        weekCal.set(Calendar.MINUTE, 59)
        weekCal.set(Calendar.SECOND, 59)
        currentWeekEnd = weekCal.timeInMillis

        loadBalanceVisibility()
        startLoadingData()
    }

    private fun startLoadingData() {
        loadDataJob?.cancel()
        loadDataJob = loadDashboardData()
    }

    private fun loadBalanceVisibility() {
        viewModelScope.launch {
            settingsRepository.isBalanceVisible.collect { isVisible ->
                _uiState.update { it.copy(isBalanceVisible = isVisible) }
            }
        }
    }

    private fun loadDashboardData(): Job {
        val totalBalanceFlow = getTotalBalanceUseCase()
        val monthlyIncomeFlow = getSumByTypeAndDateRangeUseCase(TransactionType.INCOME.value, currentMonthStart, currentMonthEnd)
        val monthlyExpenseFlow = getSumByTypeAndDateRangeUseCase(TransactionType.EXPENSE.value, currentMonthStart, currentMonthEnd)
        val topCategoriesFlow = getTopCategoriesByExpenseUseCase(currentMonthStart, currentMonthEnd, 3)
        val recentTransactionsFlow = getRecentTransactionsUseCase(5)
        val categoriesFlow = getCategoriesUseCase()

        return viewModelScope.launch {
            combine(
                totalBalanceFlow,
                monthlyIncomeFlow,
                monthlyExpenseFlow,
            ) { totalBalance, monthlyIncome, monthlyExpense ->
                Triple(totalBalance, monthlyIncome, monthlyExpense)
            }.combine(topCategoriesFlow) { (totalBalance, monthlyIncome, monthlyExpense), topCategoriesRaw ->
                Quad(totalBalance, monthlyIncome, monthlyExpense, topCategoriesRaw)
            }.combine(recentTransactionsFlow) { quad, recentTransactions ->
                Quint(quad.totalBalance, quad.monthlyIncome, quad.monthlyExpense, quad.topCategoriesRaw, recentTransactions)
            }.combine(categoriesFlow) { quint, allCategories ->
                val categoryMap = allCategories.associateBy { it.id }

                val topCategories = quint.topCategoriesRaw.map { (categoryId, total) ->
                    val category = categoryMap[categoryId]
                    TopCategoryItem(
                        categoryId = categoryId,
                        categoryName = category?.name ?: "Lainnya",
                        categoryIcon = category?.icon ?: "category",
                        categoryColor = category?.color ?: "#808080",
                        total = total,
                    )
                }

                DashboardUiState(
                    totalBalance = quint.totalBalance,
                    monthlyIncome = quint.monthlyIncome,
                    monthlyExpense = quint.monthlyExpense,
                    topCategories = topCategories,
                    recentTransactions = quint.recentTransactions,
                    isBalanceVisible = _uiState.value.isBalanceVisible,
                    isLoading = false,
                )
            }.catch {
                _uiState.update { it.copy(isLoading = false) }
            }.collect { state ->
                _uiState.update { state.copy(weeklyCheckIn = _uiState.value.weeklyCheckIn, showWeeklyCheckIn = _uiState.value.showWeeklyCheckIn, streakData = _uiState.value.streakData, progressData = _uiState.value.progressData, dailyAllowance = _uiState.value.dailyAllowance) }
            }

            loadV2Features()
        }
    }

    private suspend fun loadV2Features() {
        val streak = habitTracker.updateStreak()
        _uiState.update { it.copy(streakData = streak) }

        val showCheckin = habitTracker.shouldShowWeeklyCheckin()
        if (showCheckin) {
            val weekExpense = getMonthlyExpenseUseCase(currentWeekStart, currentWeekEnd)
            val weekIncome = getMonthlyIncomeUseCase(currentWeekStart, currentWeekEnd)
            val weekCount = getTransactionCountUseCase(currentWeekStart, currentWeekEnd)
            val topCats = getTopCategoriesByExpenseUseCase(currentWeekStart, currentWeekEnd, 1).first()

            val previousWeekStart = currentWeekStart - 7 * 24 * 60 * 60 * 1000L
            val previousWeekEnd = currentWeekStart - 1
            val prevExpense = getMonthlyExpenseUseCase(previousWeekStart, previousWeekEnd)

            val vsPercent = if (prevExpense > 0) {
                ((weekExpense - prevExpense) * 100 / prevExpense).toInt()
            } else 0

            val topCatName = if (topCats.isNotEmpty()) {
                topCats.first().let { (catId, _) ->
                    getCategoriesUseCase().first().find { it.id == catId }?.name
                }
            } else null

            val weekCal = Calendar.getInstance()
            val sdf = java.text.SimpleDateFormat("d MMM", java.util.Locale("id"))
            val weekStartDate = sdf.format(Date(currentWeekStart))
            val weekEndDate = sdf.format(Date(currentWeekEnd))

            val checkInData = WeeklyCheckInData(
                weekLabel = "$weekStartDate - $weekEndDate",
                totalExpense = weekExpense,
                totalIncome = weekIncome,
                transactionCount = weekCount,
                topCategoryName = topCatName,
                topCategoryAmount = topCats.firstOrNull()?.second ?: 0,
                vsPreviousWeekPercent = vsPercent,
                daysActive = getTransactionCountUseCase(currentWeekStart, currentWeekEnd),
            )

            _uiState.update { it.copy(weeklyCheckIn = checkInData, showWeeklyCheckIn = true) }
        }

        val budgetTotal = sumOfBudgets()
        val currentExpense = getMonthlyExpenseUseCase(currentMonthStart, currentMonthEnd)
        val allowance = habitTracker.getDailyAllowance(budgetTotal, currentExpense)
        _uiState.update { it.copy(dailyAllowance = allowance) }
    }

    private suspend fun sumOfBudgets(): Long {
        return budgetRepository.getAllBudgets().first().sumOf { budget -> budget.amount }
    }

    fun dismissWeeklyCheckIn() {
        habitTracker.markWeeklyCheckinShown()
        _uiState.update { it.copy(showWeeklyCheckIn = false) }
    }

    private data class Triple<A, B, C>(val a: A, val b: B, val c: C)
    private data class Quad<A, B, C, D>(val totalBalance: A, val monthlyIncome: B, val monthlyExpense: C, val topCategoriesRaw: D)
    private data class Quint<A, B, C, D, E>(val totalBalance: A, val monthlyIncome: B, val monthlyExpense: C, val topCategoriesRaw: D, val recentTransactions: E)

    fun toggleBalanceVisibility() {
        val newValue = !_uiState.value.isBalanceVisible
        _uiState.update { it.copy(isBalanceVisible = newValue) }
        viewModelScope.launch {
            settingsRepository.setBalanceVisible(newValue)
        }
    }

    fun refresh() {
        _uiState.update { it.copy(isRefreshing = true) }
        startLoadingData()
        viewModelScope.launch {
            kotlinx.coroutines.delay(500)
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
}
