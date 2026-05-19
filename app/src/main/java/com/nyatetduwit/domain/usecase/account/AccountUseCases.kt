package com.nyatetduwit.domain.usecase.account

import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(): Flow<List<Account>> {
        return repository.getAllAccounts()
    }
}

class GetAccountByIdUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(id: String): Flow<Account?> {
        return repository.getAccountById(id)
    }
}

class GetTotalBalanceUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(): Flow<Long> {
        return repository.getTotalBalance()
    }
}

class AddAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(account: Account) {
        repository.addAccount(account)
    }
}

class UpdateAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(account: Account) {
        repository.updateAccount(account)
    }
}

class DeleteAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(account: Account) {
        repository.deleteAccount(account)
    }
}

class CheckAccountTransactionsUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(accountId: String): Boolean {
        return repository.hasTransactions(accountId)
    }
}
