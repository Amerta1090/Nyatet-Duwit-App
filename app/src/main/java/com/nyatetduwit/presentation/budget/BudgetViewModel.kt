package com.nyatetduwit.presentation.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Budget
import com.nyatetduwit.domain.model.BudgetPeriod
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.repository.BudgetRepository
import com.nyatetduwit.domain.usecase.budget.*
import com.nyatetduwit.domain.usecase.category.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    getBudgetsUseCase: GetBudgetsUseCase,
    getCategoriesUseCase: GetCategoriesUseCase,
    private val budgetRepository: BudgetRepository,
    private val addBudgetUseCase: AddBudgetUseCase,
    private val updateBudgetUseCase: UpdateBudgetUseCase,
    private val deleteBudgetUseCase: DeleteBudgetUseCase,
    private val deactivateBudgetUseCase: DeactivateBudgetUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> = _uiState.asStateFlow()

    val budgets: StateFlow<List<Budget>> = getBudgetsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categories: StateFlow<List<Category>> = getCategoriesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadBudget()
        loadBudgetProgress()
    }

    fun loadBudget(budgetId: String? = null) {
        if (budgetId == null) {
            _uiState.update {
                it.copy(
                    formState = BudgetFormState(),
                    isLoading = false,
                )
            }
            return
        }

        viewModelScope.launch {
            val budget = getBudgetByIdUseCase(budgetId)
            if (budget != null) {
                _uiState.update {
                    it.copy(
                        formState = BudgetFormState(
                            id = budget.id,
                            categoryId = budget.categoryId,
                            amount = budget.amount,
                            period = budget.period,
                        ),
                        isLoading = false,
                    )
                }
            }
        }
    }

    private suspend fun getBudgetByIdUseCase(id: String): Budget? {
        return budgetRepository.getBudgetById(id)
    }

    fun onCategoryChange(categoryId: String?) {
        _uiState.update {
            it.copy(formState = it.formState.copy(categoryId = categoryId, isTotalBudget = categoryId == null))
        }
    }

    fun onToggleBudgetType(isTotal: Boolean) {
        _uiState.update {
            it.copy(formState = it.formState.copy(isTotalBudget = isTotal))
        }
    }

    fun onAmountChange(amount: Long) {
        _uiState.update {
            it.copy(formState = it.formState.copy(amount = amount))
        }
    }

    fun onPeriodChange(period: BudgetPeriod) {
        _uiState.update {
            it.copy(formState = it.formState.copy(period = period))
        }
    }

    fun saveBudget() {
        val formState = _uiState.value.formState
        if (formState.amount <= 0) {
            _uiState.update { it.copy(error = "Nominal budget harus lebih dari 0") }
            return
        }

        viewModelScope.launch {
            try {
                val now = Calendar.getInstance()
                val startDate = Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_MONTH, 1)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis

                val endDate = Calendar.getInstance().apply {
                    timeInMillis = startDate
                    add(Calendar.MONTH, 1)
                    add(Calendar.MILLISECOND, -1)
                }.timeInMillis

                val budget = Budget(
                    id = if (formState.id.isEmpty()) UUID.randomUUID().toString() else formState.id,
                    categoryId = if (formState.isTotalBudget) null else formState.categoryId,
                    amount = formState.amount,
                    period = formState.period,
                    startDate = startDate,
                    endDate = endDate,
                    isActive = true,
                    createdAt = if (formState.id.isEmpty()) System.currentTimeMillis() else 0,
                    updatedAt = System.currentTimeMillis(),
                )

                if (formState.id.isEmpty()) {
                    addBudgetUseCase(budget)
                } else {
                    updateBudgetUseCase(budget)
                }

                _uiState.update {
                    it.copy(
                        formState = BudgetFormState(),
                        isSuccess = true,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message, isLoading = false)
                }
            }
        }
    }

    fun deleteBudget(budget: Budget, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                deleteBudgetUseCase(budget)
                _uiState.update { it.copy(isSuccess = true) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun clearSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }

    private fun loadBudgetProgress() {
        viewModelScope.launch {
            combine(
                budgets,
                categories,
            ) { budgetList, categoryList ->
                val categoryMap = categoryList.associateBy { it.id }
                val progressItems = budgetList.map { budget ->
                    val spent = budgetRepository.getBudgetProgress(
                        categoryId = budget.categoryId,
                        startDate = budget.startDate,
                        endDate = budget.endDate,
                    ).first
                    val category = if (budget.categoryId != null) {
                        categoryMap[budget.categoryId]
                    } else {
                        null
                    }
                    BudgetProgressItem(
                        budgetId = budget.id,
                        categoryId = budget.categoryId,
                        categoryName = category?.name ?: "Total Budget",
                        categoryIcon = category?.icon ?: "account_balance_wallet",
                        categoryColor = category?.color ?: "#4F6B4E",
                        budgetAmount = budget.amount,
                        spentAmount = spent,
                        period = budget.period,
                    )
                }
                _uiState.update {
                    it.copy(
                        budgetProgress = progressItems,
                        isLoading = false,
                    )
                }
            }.catch {
                _uiState.update { it.copy(isLoading = false) }
            }.collect()
        }
    }
}

data class BudgetUiState(
    val formState: BudgetFormState = BudgetFormState(),
    val budgetProgress: List<BudgetProgressItem> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isSuccess: Boolean = false,
)

data class BudgetFormState(
    val id: String = "",
    val categoryId: String? = null,
    val amount: Long = 0L,
    val period: BudgetPeriod = BudgetPeriod.MONTHLY,
    val isTotalBudget: Boolean = true,
)

data class BudgetProgressItem(
    val budgetId: String,
    val categoryId: String?,
    val categoryName: String,
    val categoryIcon: String,
    val categoryColor: String,
    val budgetAmount: Long,
    val spentAmount: Long,
    val period: BudgetPeriod,
) {
    val percentage: Float
        get() = if (budgetAmount > 0) (spentAmount.toFloat() / budgetAmount.toFloat()) else 0f

    val remaining: Long
        get() = budgetAmount - spentAmount

    val warningLevel: BudgetWarningLevel
        get() = when {
            percentage >= 1.0f -> BudgetWarningLevel.EXCEEDED
            percentage >= 0.8f -> BudgetWarningLevel.WARNING
            else -> BudgetWarningLevel.NORMAL
        }
}

enum class BudgetWarningLevel {
    NORMAL,
    WARNING,
    EXCEEDED
}
