package com.nyatetduwit.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.usecase.account.GetAccountsUseCase
import com.nyatetduwit.domain.usecase.category.GetCategoriesUseCase
import com.nyatetduwit.domain.usecase.template.CreateTemplateFromTransactionUseCase
import com.nyatetduwit.domain.usecase.transaction.SearchAndFilterTransactionsUseCase
import com.nyatetduwit.domain.usecase.transaction.SoftDeleteTransactionUseCase
import com.nyatetduwit.domain.usecase.transaction.RestoreTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class TransactionFilterState(
    val query: String = "",
    val type: TransactionType? = null,
    val categoryId: String? = null,
    val accountId: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null,
) {
    val hasActiveFilters: Boolean
        get() = query.isNotBlank() || type != null || categoryId != null || accountId != null || startDate != null || endDate != null
}

data class TransactionGroupedByDate(
    val dateLabel: String,
    val date: Long,
    val transactions: List<Transaction>,
)

data class TransactionListUiState(
    val transactions: List<TransactionGroupedByDate> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val filterState: TransactionFilterState = TransactionFilterState(),
    val accounts: List<Account> = emptyList(),
    val categories: List<Category> = emptyList(),
)

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val searchAndFilterUseCase: SearchAndFilterTransactionsUseCase,
    private val softDeleteTransactionUseCase: SoftDeleteTransactionUseCase,
    private val restoreTransactionUseCase: RestoreTransactionUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val createTemplateFromTransactionUseCase: CreateTemplateFromTransactionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionListUiState())
    val uiState: StateFlow<TransactionListUiState> = _uiState.asStateFlow()

    private val _showUndoSnackbar = MutableStateFlow(false)
    val showUndoSnackbar: StateFlow<Boolean> = _showUndoSnackbar.asStateFlow()

    private var lastDeletedTransactionId: String? = null
    private var searchJob: Job? = null

    init {
        loadAccounts()
        loadCategories()
        loadTransactions()
    }

    private fun loadAccounts() {
        viewModelScope.launch {
            getAccountsUseCase().collect { accounts ->
                _uiState.update { it.copy(accounts = accounts) }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
        }
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            val filter = _uiState.value.filterState
            searchAndFilterUseCase(
                query = if (filter.query.isBlank()) null else filter.query,
                type = filter.type?.value,
                categoryId = filter.categoryId,
                accountId = filter.accountId,
                startDate = filter.startDate,
                endDate = filter.endDate,
            )
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = e.message ?: "Terjadi kesalahan"
                        )
                    }
                }
                .collect { transactions ->
                    val grouped = groupTransactionsByDate(transactions)
                    _uiState.update {
                        it.copy(
                            transactions = grouped,
                            isLoading = false,
                        )
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update {
            it.copy(filterState = it.filterState.copy(query = query))
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            loadTransactions()
        }
    }

    fun applyFilter(
        type: TransactionType? = _uiState.value.filterState.type,
        categoryId: String? = _uiState.value.filterState.categoryId,
        accountId: String? = _uiState.value.filterState.accountId,
        startDate: Long? = _uiState.value.filterState.startDate,
        endDate: Long? = _uiState.value.filterState.endDate,
    ) {
        _uiState.update {
            it.copy(
                filterState = it.filterState.copy(
                    type = type,
                    categoryId = categoryId,
                    accountId = accountId,
                    startDate = startDate,
                    endDate = endDate,
                )
            )
        }
        loadTransactions()
    }

    fun clearFilters() {
        _uiState.update {
            it.copy(filterState = TransactionFilterState())
        }
        loadTransactions()
    }

    private fun groupTransactionsByDate(transactions: List<Transaction>): List<TransactionGroupedByDate> {
        val grouped = mutableMapOf<Long, MutableList<Transaction>>()
        val calendar = Calendar.getInstance()

        transactions.forEach { transaction ->
            calendar.timeInMillis = transaction.dateTime
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val dateKey = calendar.timeInMillis
            if (!grouped.containsKey(dateKey)) {
                grouped[dateKey] = mutableListOf()
            }
            grouped[dateKey]!!.add(transaction)
        }

        return grouped.entries
            .sortedByDescending { it.key }
            .map { (date, transactions) ->
                TransactionGroupedByDate(
                    dateLabel = getDateLabel(date),
                    date = date,
                    transactions = transactions,
                )
            }
    }

    private fun getDateLabel(dateInMillis: Long): String {
        val calendar = Calendar.getInstance()
        val today = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val yesterday = today - (24 * 60 * 60 * 1000)
        val lastWeek = today - (7 * 24 * 60 * 60 * 1000)

        return when (dateInMillis) {
            today -> "Hari Ini"
            yesterday -> "Kemarin"
            in lastWeek..today -> {
                val sdf = SimpleDateFormat("EEEE", Locale("id"))
                sdf.format(Date(dateInMillis))
            }
            else -> {
                val sdf = SimpleDateFormat("d MMM yyyy", Locale("id"))
                sdf.format(Date(dateInMillis))
            }
        }
    }

    fun deleteTransaction(transactionId: String) {
        viewModelScope.launch {
            lastDeletedTransactionId = transactionId
            softDeleteTransactionUseCase(transactionId)
            _showUndoSnackbar.value = true
        }
    }

    fun undoDelete() {
        lastDeletedTransactionId?.let { id ->
            viewModelScope.launch {
                restoreTransactionUseCase(id)
                _showUndoSnackbar.value = false
                lastDeletedTransactionId = null
            }
        }
    }

    fun dismissUndo() {
        _showUndoSnackbar.value = false
        lastDeletedTransactionId = null
    }

    fun saveAsTemplate(transactionId: String, name: String) {
        viewModelScope.launch {
            try {
                createTemplateFromTransactionUseCase(transactionId, name)
            } catch (e: Exception) {
                // Handle error silently or show snackbar
            }
        }
    }
}
