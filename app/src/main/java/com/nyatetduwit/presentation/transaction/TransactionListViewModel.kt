package com.nyatetduwit.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.usecase.transaction.GetTransactionsUseCase
import com.nyatetduwit.domain.usecase.transaction.SoftDeleteTransactionUseCase
import com.nyatetduwit.domain.usecase.transaction.RestoreTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class TransactionListUiState(
    val transactions: List<TransactionGroupedByDate> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null,
)

data class TransactionGroupedByDate(
    val dateLabel: String,
    val date: Long,
    val transactions: List<Transaction>,
)

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val softDeleteTransactionUseCase: SoftDeleteTransactionUseCase,
    private val restoreTransactionUseCase: RestoreTransactionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionListUiState())
    val uiState: StateFlow<TransactionListUiState> = _uiState.asStateFlow()

    private val _showUndoSnackbar = MutableStateFlow(false)
    val showUndoSnackbar: StateFlow<Boolean> = _showUndoSnackbar.asStateFlow()

    private var lastDeletedTransactionId: String? = null

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            getTransactionsUseCase()
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
}
