package com.nyatetduwit.presentation.recurring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.*
import com.nyatetduwit.domain.repository.RecurringTransactionRepository
import com.nyatetduwit.domain.repository.TransactionRepository
import com.nyatetduwit.domain.usecase.recurring.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RecurringTransactionViewModel @Inject constructor(
    getRecurringTransactionsUseCase: GetRecurringTransactionsUseCase,
    getAllRecurringTransactionsUseCase: GetAllRecurringTransactionsUseCase,
    private val recurringTransactionRepository: RecurringTransactionRepository,
    private val transactionRepository: TransactionRepository,
    private val addRecurringTransactionUseCase: AddRecurringTransactionUseCase,
    private val updateRecurringTransactionUseCase: UpdateRecurringTransactionUseCase,
    private val deleteRecurringTransactionUseCase: DeleteRecurringTransactionUseCase,
    private val deactivateRecurringTransactionUseCase: DeactivateRecurringTransactionUseCase,
    private val skipRecurringInstanceUseCase: SkipRecurringInstanceUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecurringTransactionUiState())
    val uiState: StateFlow<RecurringTransactionUiState> = _uiState.asStateFlow()

    val recurringTransactions: StateFlow<List<RecurringTransaction>> = getRecurringTransactionsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allRecurringTransactions: StateFlow<List<RecurringTransaction>> = getAllRecurringTransactionsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadRecurring()
    }

    fun loadRecurring(recurringId: String? = null) {
        if (recurringId == null) {
            _uiState.update {
                it.copy(
                    formState = RecurringFormState(),
                    isLoading = false,
                )
            }
            return
        }

        viewModelScope.launch {
            val recurring = recurringTransactionRepository.getRecurringById(recurringId)
            if (recurring != null) {
                val template = transactionRepository.getTransactionById(recurring.templateTransactionId).first()
                _uiState.update {
                    it.copy(
                        formState = RecurringFormState(
                            id = recurring.id,
                            templateTransactionId = recurring.templateTransactionId,
                            transactionType = template?.type ?: TransactionType.EXPENSE,
                            amount = template?.amount ?: 0L,
                            categoryId = template?.categoryId,
                            accountId = template?.accountId ?: "",
                            toAccountId = template?.toAccountId,
                            notes = template?.notes,
                            frequency = recurring.frequency,
                            startDate = recurring.startDate,
                            endDate = recurring.endDate,
                        ),
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun onFrequencyChange(frequency: RecurringFrequency) {
        _uiState.update {
            it.copy(formState = it.formState.copy(frequency = frequency))
        }
    }

    fun onAmountChange(amount: Long) {
        _uiState.update {
            it.copy(formState = it.formState.copy(amount = amount))
        }
    }

    fun onCategoryChange(categoryId: String?) {
        _uiState.update {
            it.copy(formState = it.formState.copy(categoryId = categoryId))
        }
    }

    fun onAccountChange(accountId: String) {
        _uiState.update {
            it.copy(formState = it.formState.copy(accountId = accountId))
        }
    }

    fun onToAccountChange(toAccountId: String?) {
        _uiState.update {
            it.copy(formState = it.formState.copy(toAccountId = toAccountId))
        }
    }

    fun onNotesChange(notes: String) {
        _uiState.update {
            it.copy(formState = it.formState.copy(notes = notes))
        }
    }

    fun onTypeChange(type: TransactionType) {
        _uiState.update {
            it.copy(formState = it.formState.copy(transactionType = type))
        }
    }

    fun saveRecurring() {
        val formState = _uiState.value.formState
        if (formState.amount <= 0) {
            _uiState.update { it.copy(error = "Nominal harus lebih dari 0") }
            return
        }
        if (formState.accountId.isEmpty()) {
            _uiState.update { it.copy(error = "Pilih akun terlebih dahulu") }
            return
        }

        viewModelScope.launch {
            try {
                val templateTransaction = Transaction(
                    id = UUID.randomUUID().toString(),
                    type = formState.transactionType,
                    amount = formState.amount,
                    accountId = formState.accountId,
                    categoryId = formState.categoryId,
                    toAccountId = formState.toAccountId,
                    notes = formState.notes,
                    dateTime = System.currentTimeMillis(),
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                )

                val nextDue = calculateNextDue(formState.frequency, System.currentTimeMillis())

                val recurring = RecurringTransaction(
                    id = formState.id,
                    templateTransactionId = templateTransaction.id,
                    frequency = formState.frequency,
                    startDate = System.currentTimeMillis(),
                    endDate = formState.endDate,
                    nextDue = nextDue,
                    isActive = true,
                    lastProcessed = null,
                    skippedDates = emptyList(),
                    createdAt = if (formState.id.isEmpty()) System.currentTimeMillis() else 0,
                    updatedAt = System.currentTimeMillis(),
                )

                if (formState.id.isEmpty()) {
                    addRecurringTransactionUseCase(recurring)
                    transactionRepository.addTransaction(templateTransaction)
                } else {
                    updateRecurringTransactionUseCase(recurring)
                    transactionRepository.updateTransaction(templateTransaction)
                }

                _uiState.update {
                    it.copy(
                        formState = RecurringFormState(),
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

    fun deactivateRecurring(id: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                deactivateRecurringTransactionUseCase(id)
                _uiState.update { it.copy(isSuccess = true) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteRecurring(recurring: RecurringTransaction, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                deleteRecurringTransactionUseCase(recurring)
                _uiState.update { it.copy(isSuccess = true) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun skipInstance(id: String) {
        viewModelScope.launch {
            try {
                skipRecurringInstanceUseCase(id, System.currentTimeMillis())
                val recurring = recurringTransactionRepository.getRecurringById(id)
                if (recurring != null) {
                    val nextDue = calculateNextDue(recurring.frequency, recurring.nextDue)
                    recurringTransactionRepository.updateRecurring(recurring.copy(nextDue = nextDue))
                }
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

    private fun calculateNextDue(frequency: RecurringFrequency, from: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = from
        when (frequency) {
            RecurringFrequency.DAILY -> calendar.add(Calendar.DAY_OF_MONTH, 1)
            RecurringFrequency.WEEKLY -> calendar.add(Calendar.WEEK_OF_YEAR, 1)
            RecurringFrequency.MONTHLY -> calendar.add(Calendar.MONTH, 1)
            RecurringFrequency.YEARLY -> calendar.add(Calendar.YEAR, 1)
        }
        return calendar.timeInMillis
    }
}

data class RecurringTransactionUiState(
    val formState: RecurringFormState = RecurringFormState(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isSuccess: Boolean = false,
)

data class RecurringFormState(
    val id: String = "",
    val templateTransactionId: String = "",
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val amount: Long = 0L,
    val categoryId: String? = null,
    val accountId: String = "",
    val toAccountId: String? = null,
    val notes: String? = null,
    val frequency: RecurringFrequency = RecurringFrequency.MONTHLY,
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long? = null,
)
