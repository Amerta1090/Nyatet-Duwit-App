package com.nyatetduwit.domain.usecase.transaction

import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.repository.AccountRepository
import com.nyatetduwit.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class BalanceUpdateService @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) {

    suspend fun onTransactionAdded(transaction: com.nyatetduwit.domain.model.Transaction) {
        when (transaction.type) {
            TransactionType.INCOME -> {
                val account = accountRepository.getAccountById(transaction.accountId).first()
                account?.let {
                    val updated = it.copy(balance = it.balance + transaction.amount)
                    accountRepository.updateAccount(updated)
                }
            }
            TransactionType.EXPENSE -> {
                val account = accountRepository.getAccountById(transaction.accountId).first()
                account?.let {
                    val updated = it.copy(balance = it.balance - transaction.amount)
                    accountRepository.updateAccount(updated)
                }
            }
            TransactionType.TRANSFER -> {
                transaction.toAccountId?.let { toId ->
                    val fromAccount = accountRepository.getAccountById(transaction.accountId).first()
                    fromAccount?.let {
                        val updatedFrom = it.copy(balance = it.balance - transaction.amount)
                        accountRepository.updateAccount(updatedFrom)
                    }

                    val toAccount = accountRepository.getAccountById(toId).first()
                    toAccount?.let {
                        val updatedTo = it.copy(balance = it.balance + transaction.amount)
                        accountRepository.updateAccount(updatedTo)
                    }
                }
            }
        }
    }

    suspend fun onTransactionUpdated(
        oldTransaction: com.nyatetduwit.domain.model.Transaction,
        newTransaction: com.nyatetduwit.domain.model.Transaction,
    ) {
        onTransactionDeleted(oldTransaction)
        onTransactionAdded(newTransaction)
    }

    suspend fun onTransactionDeleted(transaction: com.nyatetduwit.domain.model.Transaction) {
        when (transaction.type) {
            TransactionType.INCOME -> {
                val account = accountRepository.getAccountById(transaction.accountId).first()
                account?.let {
                    val updated = it.copy(balance = it.balance - transaction.amount)
                    accountRepository.updateAccount(updated)
                }
            }
            TransactionType.EXPENSE -> {
                val account = accountRepository.getAccountById(transaction.accountId).first()
                account?.let {
                    val updated = it.copy(balance = it.balance + transaction.amount)
                    accountRepository.updateAccount(updated)
                }
            }
            TransactionType.TRANSFER -> {
                transaction.toAccountId?.let { toId ->
                    val fromAccount = accountRepository.getAccountById(transaction.accountId).first()
                    fromAccount?.let {
                        val updatedFrom = it.copy(balance = it.balance + transaction.amount)
                        accountRepository.updateAccount(updatedFrom)
                    }

                    val toAccount = accountRepository.getAccountById(toId).first()
                    toAccount?.let {
                        val updatedTo = it.copy(balance = it.balance - transaction.amount)
                        accountRepository.updateAccount(updatedTo)
                    }
                }
            }
        }
    }
}
