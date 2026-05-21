package com.nyatetduwit.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.AccountType
import com.nyatetduwit.domain.usecase.account.AddAccountUseCase
import com.nyatetduwit.domain.usecase.account.CheckAccountTransactionsUseCase
import com.nyatetduwit.domain.usecase.account.DeleteAccountUseCase
import com.nyatetduwit.domain.usecase.account.GetAccountByIdUseCase
import com.nyatetduwit.domain.usecase.account.GetAccountsUseCase
import com.nyatetduwit.domain.usecase.account.GetTotalBalanceUseCase
import com.nyatetduwit.domain.usecase.account.UpdateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    getAccountsUseCase: GetAccountsUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    getTotalBalanceUseCase: GetTotalBalanceUseCase,
    private val addAccountUseCase: AddAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val checkAccountTransactionsUseCase: CheckAccountTransactionsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    val accounts: StateFlow<List<Account>> = getAccountsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalBalance: StateFlow<Long> = getTotalBalanceUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    init {
        loadAccount()
    }

    fun loadAccount(accountId: String? = null) {
        if (accountId == null) {
            _uiState.update {
                it.copy(
                    formState = AccountFormState(),
                    isLoading = false,
                )
            }
            return
        }

        viewModelScope.launch {
            getAccountByIdUseCase(accountId)
                .catch { e ->
                    _uiState.update {
                        it.copy(error = e.message, isLoading = false)
                    }
                }
                .collect { account ->
                    if (account != null) {
                        _uiState.update {
                            it.copy(
                                formState = AccountFormState(
                                    id = account.id,
                                    name = account.name,
                                    type = account.type,
                                    balance = account.balance,
                                    icon = account.icon,
                                    color = account.color,
                                    isHidden = account.isHidden,
                                ),
                                isLoading = false,
                            )
                        }
                    }
                }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update {
            it.copy(formState = it.formState.copy(name = name))
        }
    }

    fun onTypeChange(type: AccountType) {
        _uiState.update {
            it.copy(formState = it.formState.copy(type = type))
        }
    }

    fun onBalanceChange(balance: Long) {
        _uiState.update {
            it.copy(formState = it.formState.copy(balance = balance))
        }
    }

    fun onIconChange(icon: String) {
        _uiState.update {
            it.copy(formState = it.formState.copy(icon = icon))
        }
    }

    fun onColorChange(color: String) {
        _uiState.update {
            it.copy(formState = it.formState.copy(color = color))
        }
    }

    fun onHiddenChange(isHidden: Boolean) {
        _uiState.update {
            it.copy(formState = it.formState.copy(isHidden = isHidden))
        }
    }

    fun saveAccount() {
        val formState = _uiState.value.formState
        if (formState.name.isBlank()) {
            _uiState.update { it.copy(error = "Nama akun tidak boleh kosong") }
            return
        }

        viewModelScope.launch {
            try {
                val account = Account(
                    id = if (formState.id.isEmpty()) UUID.randomUUID().toString() else formState.id,
                    name = formState.name.trim(),
                    type = formState.type,
                    balance = formState.balance,
                    icon = formState.icon,
                    color = formState.color,
                    isHidden = formState.isHidden,
                    orderIndex = 0,
                    createdAt = if (formState.id.isEmpty()) System.currentTimeMillis() else 0,
                    updatedAt = System.currentTimeMillis(),
                )

                if (formState.id.isEmpty()) {
                    addAccountUseCase(account)
                } else {
                    updateAccountUseCase(account)
                }

                _uiState.update {
                    it.copy(
                        formState = AccountFormState(),
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

    fun deleteAccount(account: Account, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                deleteAccountUseCase(account)
                _uiState.update { it.copy(isSuccess = true) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun checkTransactions(accountId: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val hasTransactions = checkAccountTransactionsUseCase(accountId)
            callback(hasTransactions)
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun clearSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }

    fun reorderAccount(fromIndex: Int, toIndex: Int) {
        viewModelScope.launch {
            try {
                val currentAccounts = accounts.value.toMutableList()
                if (fromIndex < 0 || fromIndex >= currentAccounts.size || toIndex < 0 || toIndex >= currentAccounts.size) return@launch

                val movedItem = currentAccounts.removeAt(fromIndex)
                currentAccounts.add(toIndex, movedItem)

                currentAccounts.forEachIndexed { index, account ->
                    if (account.orderIndex != index) {
                        updateAccountUseCase(account.copy(orderIndex = index, updatedAt = System.currentTimeMillis()))
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}

data class AccountUiState(
    val formState: AccountFormState = AccountFormState(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isSuccess: Boolean = false,
)

data class AccountFormState(
    val id: String = "",
    val name: String = "",
    val type: AccountType = AccountType.CASH,
    val balance: Long = 0L,
    val icon: String = "account_balance_wallet",
    val color: String = "#4F6B4E",
    val isHidden: Boolean = false,
)
