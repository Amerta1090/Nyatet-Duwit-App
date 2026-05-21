package com.nyatetduwit.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.Template
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.repository.SettingsRepository
import com.nyatetduwit.domain.usecase.account.GetAccountsUseCase
import com.nyatetduwit.domain.usecase.category.GetCategoriesByTypeUseCase
import com.nyatetduwit.domain.usecase.template.GetPinnedTemplatesUseCase
import com.nyatetduwit.domain.usecase.template.IncrementTemplateUsageUseCase
import com.nyatetduwit.domain.usecase.transaction.AddTransactionUseCase
import com.nyatetduwit.domain.usecase.transaction.BalanceUpdateService
import com.nyatetduwit.domain.usecase.transaction.GetTransactionByIdUseCase
import com.nyatetduwit.domain.usecase.transaction.UpdateTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class TransactionFormState(
    val type: TransactionType = TransactionType.EXPENSE,
    val amount: Long = 0L,
    val accountId: String = "",
    val toAccountId: String = "",
    val categoryId: String? = null,
    val notes: String? = null,
    val dateTime: Long = System.currentTimeMillis(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
)

data class TransactionUiState(
    val accounts: List<Account> = emptyList(),
    val categories: List<Category> = emptyList(),
    val templates: List<Template> = emptyList(),
    val formState: TransactionFormState = TransactionFormState(),
)

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getCategoriesByTypeUseCase: GetCategoriesByTypeUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val balanceUpdateService: BalanceUpdateService,
    private val getPinnedTemplatesUseCase: GetPinnedTemplatesUseCase,
    private val incrementTemplateUsageUseCase: IncrementTemplateUsageUseCase,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    private val _showUndoSnackbar = MutableStateFlow(false)
    val showUndoSnackbar: StateFlow<Boolean> = _showUndoSnackbar.asStateFlow()

    private var lastDeletedTransaction: Transaction? = null

    init {
        loadAccounts()
        loadCategories()
        loadTemplates()
    }

    private fun loadTemplates() {
        viewModelScope.launch {
            getPinnedTemplatesUseCase().collect { templates ->
                _uiState.update { it.copy(templates = templates) }
            }
        }
    }

    fun loadTransactionForEdit(transactionId: String) {
        viewModelScope.launch {
            getTransactionByIdUseCase(transactionId).collect { transaction ->
                transaction?.let {
                    _uiState.update { currentState ->
                        currentState.copy(
                            formState = currentState.formState.copy(
                                type = it.type,
                                amount = it.amount,
                                accountId = it.accountId,
                                toAccountId = it.toAccountId ?: "",
                                categoryId = it.categoryId,
                                notes = it.notes,
                                dateTime = it.dateTime,
                            )
                        )
                    }
                }
            }
        }
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            getAccountsUseCase().collect { accounts ->
                _uiState.update { it.copy(accounts = accounts) }
                if (accounts.isNotEmpty() && _uiState.value.formState.accountId.isEmpty()) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            formState = currentState.formState.copy(
                                accountId = accounts.first().id
                            )
                        )
                    }
                }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val currentType = _uiState.value.formState.type
            val categoryType = when (currentType) {
                TransactionType.INCOME -> com.nyatetduwit.domain.model.CategoryType.INCOME
                TransactionType.EXPENSE -> com.nyatetduwit.domain.model.CategoryType.EXPENSE
                TransactionType.TRANSFER -> null
            }

            categoryType?.let { type ->
                getCategoriesByTypeUseCase(type).collect { categories ->
                    _uiState.update { it.copy(categories = categories) }
                }
            } ?: run {
                _uiState.update { it.copy(categories = emptyList()) }
            }
        }
    }

    fun setType(type: TransactionType) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    type = type,
                    categoryId = if (type == TransactionType.TRANSFER) null else currentState.formState.categoryId
                )
            )
        }
        loadCategories()
    }

    fun setAmount(amount: Long) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(amount = amount)
            )
        }
    }

    fun appendToAmount(digits: String) {
        val currentAmount = _uiState.value.formState.amount
        val newAmount = "${currentAmount}$digits".toLongOrNull() ?: 0L
        setAmount(newAmount)
    }

    fun backspaceAmount() {
        val currentAmount = _uiState.value.formState.amount.toString()
        if (currentAmount.length > 1) {
            setAmount(currentAmount.dropLast(1).toLongOrNull() ?: 0L)
        } else {
            setAmount(0L)
        }
    }

    fun setAccountId(accountId: String) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(accountId = accountId)
            )
        }
    }

    fun setToAccountId(accountId: String) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(toAccountId = accountId)
            )
        }
    }

    fun setCategoryId(categoryId: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(categoryId = categoryId)
            )
        }
    }

    fun setNotes(notes: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(notes = notes)
            )
        }
    }

    fun setDateTime(dateTime: Long) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(dateTime = dateTime)
            )
        }
    }

    fun applyTemplate(template: Template) {
        viewModelScope.launch {
            incrementTemplateUsageUseCase(template.id)
            _uiState.update { currentState ->
                currentState.copy(
                    formState = currentState.formState.copy(
                        type = template.type,
                        amount = template.amount,
                        accountId = template.accountId ?: currentState.formState.accountId,
                        toAccountId = "",
                        categoryId = template.categoryId,
                        notes = template.notes,
                    )
                )
            }
            loadCategories()
        }
    }

    fun saveTransaction(existingId: String? = null) {
        viewModelScope.launch {
            val formState = _uiState.value.formState

            if (formState.amount <= 0L) {
                _uiState.update { it.copy(formState = formState.copy(isError = true, errorMessage = "Nominal harus lebih dari 0")) }
                return@launch
            }

            if (formState.accountId.isEmpty()) {
                _uiState.update { it.copy(formState = formState.copy(isError = true, errorMessage = "Pilih akun")) }
                return@launch
            }

            if (formState.type == TransactionType.TRANSFER && formState.toAccountId.isEmpty()) {
                _uiState.update { it.copy(formState = formState.copy(isError = true, errorMessage = "Pilih akun tujuan")) }
                return@launch
            }

            if (formState.type == TransactionType.TRANSFER && formState.accountId == formState.toAccountId) {
                _uiState.update { it.copy(formState = formState.copy(isError = true, errorMessage = "Akun asal dan tujuan tidak boleh sama")) }
                return@launch
            }

            _uiState.update { it.copy(formState = formState.copy(isLoading = true, isError = false)) }

            try {
                val transaction = Transaction(
                    id = existingId ?: UUID.randomUUID().toString(),
                    type = formState.type,
                    amount = formState.amount,
                    accountId = formState.accountId,
                    categoryId = if (formState.type != TransactionType.TRANSFER) formState.categoryId else null,
                    toAccountId = if (formState.type == TransactionType.TRANSFER) formState.toAccountId else null,
                    notes = formState.notes,
                    dateTime = formState.dateTime,
                    createdAt = 0,
                    updatedAt = 0,
                )

                if (existingId != null) {
                    val oldTransaction = getTransactionByIdUseCase(existingId).first()
                    oldTransaction?.let {
                        balanceUpdateService.onTransactionDeleted(it)
                    }
                    updateTransactionUseCase(transaction)
                } else {
                    addTransactionUseCase(transaction)
                }

                balanceUpdateService.onTransactionAdded(transaction)
                settingsRepository.setLastTransactionDate(System.currentTimeMillis())

                _uiState.update {
                    it.copy(
                        formState = formState.copy(
                            isLoading = false,
                            isSuccess = true
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        formState = formState.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = e.message ?: "Terjadi kesalahan"
                        )
                    )
                }
            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            lastDeletedTransaction = transaction
            balanceUpdateService.onTransactionDeleted(transaction)
            _showUndoSnackbar.value = true
        }
    }

    fun undoDelete() {
        lastDeletedTransaction?.let { transaction ->
            viewModelScope.launch {
                balanceUpdateService.onTransactionAdded(transaction)
                _showUndoSnackbar.value = false
                lastDeletedTransaction = null
            }
        }
    }

    fun clearSuccess() {
        _uiState.update { it.copy(formState = it.formState.copy(isSuccess = false)) }
    }

    fun clearError() {
        _uiState.update { it.copy(formState = it.formState.copy(isError = false, errorMessage = null)) }
    }
}
