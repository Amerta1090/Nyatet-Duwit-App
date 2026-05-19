package com.nyatetduwit.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.repository.SettingsRepository
import com.nyatetduwit.domain.usecase.account.GetTotalBalanceUseCase
import com.nyatetduwit.domain.usecase.category.GetCategoriesUseCase
import com.nyatetduwit.domain.usecase.transaction.GetRecentTransactionsUseCase
import com.nyatetduwit.domain.usecase.transaction.GetSumByTypeAndDateRangeUseCase
import com.nyatetduwit.domain.usecase.transaction.GetTopCategoriesByExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    getTotalBalanceUseCase: GetTotalBalanceUseCase,
    getSumByTypeAndDateRangeUseCase: GetSumByTypeAndDateRangeUseCase,
    getTopCategoriesByExpenseUseCase: GetTopCategoriesByExpenseUseCase,
    getRecentTransactionsUseCase: GetRecentTransactionsUseCase,
    getCategoriesUseCase: GetCategoriesUseCase,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val currentMonthStart: Long
    private val currentMonthEnd: Long

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

        loadBalanceVisibility()
        loadDashboardData(
            getTotalBalanceUseCase = getTotalBalanceUseCase,
            getSumByTypeAndDateRangeUseCase = getSumByTypeAndDateRangeUseCase,
            getTopCategoriesByExpenseUseCase = getTopCategoriesByExpenseUseCase,
            getRecentTransactionsUseCase = getRecentTransactionsUseCase,
            getCategoriesUseCase = getCategoriesUseCase,
        )
    }

    private fun loadBalanceVisibility() {
        viewModelScope.launch {
            settingsRepository.isBalanceVisible.collect { isVisible ->
                _uiState.update { it.copy(isBalanceVisible = isVisible) }
            }
        }
    }

    private fun loadDashboardData(
        getTotalBalanceUseCase: GetTotalBalanceUseCase,
        getSumByTypeAndDateRangeUseCase: GetSumByTypeAndDateRangeUseCase,
        getTopCategoriesByExpenseUseCase: GetTopCategoriesByExpenseUseCase,
        getRecentTransactionsUseCase: GetRecentTransactionsUseCase,
        getCategoriesUseCase: GetCategoriesUseCase,
    ) {
        val totalBalanceFlow = getTotalBalanceUseCase()
        val monthlyIncomeFlow = getSumByTypeAndDateRangeUseCase(TransactionType.INCOME.value, currentMonthStart, currentMonthEnd)
        val monthlyExpenseFlow = getSumByTypeAndDateRangeUseCase(TransactionType.EXPENSE.value, currentMonthStart, currentMonthEnd)
        val topCategoriesFlow = getTopCategoriesByExpenseUseCase(currentMonthStart, currentMonthEnd, 3)
        val recentTransactionsFlow = getRecentTransactionsUseCase(5)
        val categoriesFlow = getCategoriesUseCase()

        viewModelScope.launch {
            combine(
                totalBalanceFlow,
                monthlyIncomeFlow,
                monthlyExpenseFlow,
                topCategoriesFlow,
                recentTransactionsFlow,
                categoriesFlow,
            ) { totalBalance, monthlyIncome, monthlyExpense, topCategoriesRaw, recentTransactions, allCategories ->
                val categoryMap = allCategories.associateBy { it.id }

                val topCategories = topCategoriesRaw.map { (categoryId, total) ->
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
                    totalBalance = totalBalance,
                    monthlyIncome = monthlyIncome,
                    monthlyExpense = monthlyExpense,
                    topCategories = topCategories,
                    recentTransactions = recentTransactions,
                    isBalanceVisible = _uiState.value.isBalanceVisible,
                    isLoading = false,
                )
            }.catch {
                _uiState.update { it.copy(isLoading = false) }
            }.collect { state ->
                _uiState.update { state }
            }
        }
    }

    fun toggleBalanceVisibility() {
        val newValue = !_uiState.value.isBalanceVisible
        _uiState.update { it.copy(isBalanceVisible = newValue) }
        viewModelScope.launch {
            settingsRepository.setBalanceVisible(newValue)
        }
    }

    fun refresh() {
        _uiState.update { it.copy(isRefreshing = true) }
        viewModelScope.launch {
            kotlinx.coroutines.delay(500)
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
}
