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
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val currentMonthStart: Long
    private val currentMonthEnd: Long
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
                _uiState.update { state }
            }
        }
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
