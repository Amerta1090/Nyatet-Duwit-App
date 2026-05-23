package com.nyatetduwit.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.Template
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionSplit
import com.nyatetduwit.domain.model.TransactionTag
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.repository.SettingsRepository
import com.nyatetduwit.domain.usecase.account.GetAccountsUseCase
import com.nyatetduwit.domain.usecase.category.GetCategoriesByTypeUseCase
import com.nyatetduwit.domain.usecase.template.GetPinnedTemplatesUseCase
import com.nyatetduwit.domain.usecase.template.GetTemplateByIdUseCase
import com.nyatetduwit.domain.usecase.template.IncrementTemplateUsageUseCase
import com.nyatetduwit.domain.usecase.transaction.AddTransactionUseCase
import com.nyatetduwit.domain.usecase.transaction.BalanceUpdateService
import com.nyatetduwit.domain.usecase.transaction.GetSplitsByTransactionUseCase
import com.nyatetduwit.domain.usecase.transaction.GetTagsByTransactionUseCase
import com.nyatetduwit.domain.usecase.transaction.GetTransactionByIdUseCase
import com.nyatetduwit.domain.usecase.transaction.SaveSplitsUseCase
import com.nyatetduwit.domain.usecase.transaction.SaveTagsUseCase
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
    val enableSplit: Boolean = false,
    val splits: List<SplitEntry> = emptyList(),
    val tags: List<String> = emptyList(),
    val tagInput: String = "",
    val attachmentPath: String? = null,
)

data class SplitEntry(
    val id: String = UUID.randomUUID().toString(),
    val categoryId: String = "",
    val amount: Long = 0L,
    val notes: String? = null,
)

data class TransactionUiState(
    val accounts: List<Account> = emptyList(),
    val categories: List<Category> = emptyList(),
    val templates: List<Template> = emptyList(),
    val formState: TransactionFormState = TransactionFormState(),
    val existingSplits: List<TransactionSplit> = emptyList(),
    val existingTags: List<TransactionTag> = emptyList(),
    val allTagNames: List<String> = emptyList(),
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
    private val getTemplateByIdUseCase: GetTemplateByIdUseCase,
    private val incrementTemplateUsageUseCase: IncrementTemplateUsageUseCase,
    private val settingsRepository: SettingsRepository,
    private val getSplitsByTransactionUseCase: GetSplitsByTransactionUseCase,
    private val getTagsByTransactionUseCase: GetTagsByTransactionUseCase,
    private val saveSplitsUseCase: SaveSplitsUseCase,
    private val saveTagsUseCase: SaveTagsUseCase,
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
        loadAllTags()
    }

    private fun loadAllTags() {
        viewModelScope.launch {
            val repo = _uiState.value.allTagNames
        }
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
                                attachmentPath = it.attachmentPath,
                            )
                        )
                    }
                    getSplitsByTransactionUseCase(transactionId).collect { splits ->
                        _uiState.update { it.copy(existingSplits = splits) }
                    }
                    getTagsByTransactionUseCase(transactionId).collect { tags ->
                        _uiState.update { it.copy(existingTags = tags) }
                    }
                }
            }
        }
    }

    fun loadTemplateForApply(templateId: String) {
        viewModelScope.launch {
            getTemplateByIdUseCase(templateId).collect { template ->
                template?.let { applyTemplate(it) }
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
                    categoryId = if (type == TransactionType.TRANSFER) null else currentState.formState.categoryId,
                    enableSplit = false,
                    splits = emptyList(),
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

    fun setAttachmentPath(path: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(attachmentPath = path)
            )
        }
    }

    fun toggleSplit() {
        val current = _uiState.value.formState
        if (current.enableSplit) {
            _uiState.update { state ->
                state.copy(formState = state.formState.copy(enableSplit = false, splits = emptyList()))
            }
        } else {
            _uiState.update { state ->
                state.copy(
                    formState = state.formState.copy(
                        enableSplit = true,
                        splits = listOf(
                            SplitEntry(categoryId = state.formState.categoryId ?: "", amount = state.formState.amount)
                        )
                    )
                )
            }
        }
    }

    fun updateSplit(index: Int, split: SplitEntry) {
        _uiState.update { state ->
            val newSplits = state.formState.splits.toMutableList()
            if (index in newSplits.indices) {
                newSplits[index] = split
            }
            state.copy(formState = state.formState.copy(splits = newSplits))
        }
    }

    fun addSplit() {
        _uiState.update { state ->
            state.copy(
                formState = state.formState.copy(
                    splits = state.formState.splits + SplitEntry()
                )
            )
        }
    }

    fun removeSplit(index: Int) {
        _uiState.update { state ->
            val newSplits = state.formState.splits.toMutableList()
            if (index in newSplits.indices && newSplits.size > 1) {
                newSplits.removeAt(index)
            }
            state.copy(formState = state.formState.copy(splits = newSplits))
        }
    }

    fun setTagInput(input: String) {
        _uiState.update { state ->
            state.copy(formState = state.formState.copy(tagInput = input))
        }
    }

    fun addTag(tag: String) {
        val trimmed = tag.trim().lowercase().removePrefix("#")
        if (trimmed.isBlank()) return
        _uiState.update { state ->
            val currentTags = state.formState.tags
            if (trimmed in currentTags) return@update state
            state.copy(
                formState = state.formState.copy(
                    tags = currentTags + trimmed,
                    tagInput = "",
                )
            )
        }
    }

    fun removeTag(tag: String) {
        _uiState.update { state ->
            state.copy(
                formState = state.formState.copy(
                    tags = state.formState.tags - tag
                )
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
                        enableSplit = false,
                        splits = emptyList(),
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

            if (formState.enableSplit && formState.splits.isNotEmpty()) {
                val splitTotal = formState.splits.sumOf { it.amount }
                if (splitTotal != formState.amount) {
                    _uiState.update { it.copy(formState = formState.copy(isError = true, errorMessage = "Total split (${formatAmount(splitTotal)}) harus sama dengan nominal (${formatAmount(formState.amount)})")) }
                    return@launch
                }
                val firstSplit = formState.splits.firstOrNull()
                if (firstSplit != null && firstSplit.categoryId.isEmpty()) {
                    _uiState.update { it.copy(formState = formState.copy(isError = true, errorMessage = "Pilih kategori untuk setiap split")) }
                    return@launch
                }
            }

            _uiState.update { it.copy(formState = formState.copy(isLoading = true, isError = false)) }

            try {
                val transactionId = existingId ?: UUID.randomUUID().toString()
                val transaction = Transaction(
                    id = transactionId,
                    type = formState.type,
                    amount = formState.amount,
                    accountId = formState.accountId,
                    categoryId = if (formState.type != TransactionType.TRANSFER && !formState.enableSplit) formState.categoryId
                        else if (formState.type != TransactionType.TRANSFER && formState.enableSplit && formState.splits.isNotEmpty()) null
                        else null,
                    toAccountId = if (formState.type == TransactionType.TRANSFER) formState.toAccountId else null,
                    notes = formState.notes,
                    dateTime = formState.dateTime,
                    createdAt = 0,
                    updatedAt = 0,
                    attachmentPath = formState.attachmentPath,
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

                if (formState.enableSplit && formState.splits.isNotEmpty()) {
                    val splits = formState.splits.map { split ->
                        TransactionSplit(
                            id = UUID.randomUUID().toString(),
                            transactionId = transactionId,
                            categoryId = split.categoryId,
                            amount = split.amount,
                            notes = split.notes,
                        )
                    }
                    saveSplitsUseCase(transactionId, splits)
                } else if (existingId != null) {
                    saveSplitsUseCase(existingId, emptyList())
                }

                if (formState.tags.isNotEmpty()) {
                    val tags = formState.tags.map { tagName ->
                        TransactionTag(
                            id = UUID.randomUUID().toString(),
                            transactionId = transactionId,
                            tagName = tagName,
                        )
                    }
                    saveTagsUseCase(transactionId, tags)
                } else if (existingId != null) {
                    saveTagsUseCase(existingId, emptyList())
                }

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

    private fun formatAmount(amount: Long): String {
        return "Rp ${java.text.NumberFormat.getNumberInstance(java.util.Locale("id")).format(amount)}"
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
