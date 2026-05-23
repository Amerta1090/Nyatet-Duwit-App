package com.nyatetduwit.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionSplit
import com.nyatetduwit.domain.model.TransactionTag
import com.nyatetduwit.domain.usecase.account.GetAccountByIdUseCase
import com.nyatetduwit.domain.usecase.category.GetCategoryByIdUseCase
import com.nyatetduwit.domain.usecase.transaction.BalanceUpdateService
import com.nyatetduwit.domain.usecase.transaction.GetSplitsByTransactionUseCase
import com.nyatetduwit.domain.usecase.transaction.GetTagsByTransactionUseCase
import com.nyatetduwit.domain.usecase.transaction.GetTransactionByIdUseCase
import com.nyatetduwit.domain.usecase.transaction.SoftDeleteTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TransactionDetailUiState(
    val transaction: Transaction? = null,
    val account: Account? = null,
    val toAccount: Account? = null,
    val category: Category? = null,
    val splits: List<TransactionSplit> = emptyList(),
    val tags: List<TransactionTag> = emptyList(),
    val splitCategories: Map<String, Category?> = emptyMap(),
    val isLoading: Boolean = true,
    val showDeleteConfirm: Boolean = false,
)

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val softDeleteTransactionUseCase: SoftDeleteTransactionUseCase,
    private val balanceUpdateService: BalanceUpdateService,
    private val getSplitsByTransactionUseCase: GetSplitsByTransactionUseCase,
    private val getTagsByTransactionUseCase: GetTagsByTransactionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionDetailUiState())
    val uiState: StateFlow<TransactionDetailUiState> = _uiState.asStateFlow()

    fun loadTransaction(transactionId: String) {
        viewModelScope.launch {
            getTransactionByIdUseCase(transactionId).collect { transaction ->
                transaction?.let { tx ->
                    _uiState.update { it.copy(transaction = tx, isLoading = false) }

                    getAccountByIdUseCase(tx.accountId).collect { account ->
                        _uiState.update { it.copy(account = account) }
                    }

                    if (tx.type == com.nyatetduwit.domain.model.TransactionType.TRANSFER) {
                        tx.toAccountId?.let { toId ->
                            getAccountByIdUseCase(toId).collect { toAccount ->
                                _uiState.update { it.copy(toAccount = toAccount) }
                            }
                        }
                    }

                    tx.categoryId?.let { catId ->
                        getCategoryByIdUseCase(catId).collect { category ->
                            _uiState.update { it.copy(category = category) }
                        }
                    }

                    getSplitsByTransactionUseCase(transactionId).collect { splits ->
                        val splitCategories = mutableMapOf<String, Category?>()
                        splits.forEach { split ->
                            if (split.categoryId !in splitCategories) {
                                val cat = getCategoryByIdUseCase(split.categoryId).first()
                                splitCategories[split.categoryId] = cat
                            }
                        }
                        _uiState.update { it.copy(splits = splits, splitCategories = splitCategories) }
                    }

                    getTagsByTransactionUseCase(transactionId).collect { tags ->
                        _uiState.update { it.copy(tags = tags) }
                    }
                } ?: run {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        _uiState.update { it.copy(showDeleteConfirm = true) }
    }

    fun confirmDelete() {
        viewModelScope.launch {
            _uiState.value.transaction?.let { transaction ->
                balanceUpdateService.onTransactionDeleted(transaction)
                softDeleteTransactionUseCase(transaction.id)
            }
            _uiState.update { it.copy(showDeleteConfirm = false) }
        }
    }

    fun dismissDeleteDialog() {
        _uiState.update { it.copy(showDeleteConfirm = false) }
    }
}
