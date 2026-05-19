package com.nyatetduwit.domain.usecase.transaction

import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<Transaction>> {
        return repository.getAllTransactions()
    }
}

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(id: String): Flow<Transaction?> {
        return repository.getTransactionById(id)
    }
}

class GetTransactionsByAccountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(accountId: String): Flow<List<Transaction>> {
        return repository.getTransactionsByAccount(accountId)
    }
}

class GetTransactionsByTypeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(type: String): Flow<List<Transaction>> {
        return repository.getTransactionsByType(type)
    }
}

class GetTransactionsByDateRangeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(startDate: Long, endDate: Long): Flow<List<Transaction>> {
        return repository.getTransactionsByDateRange(startDate, endDate)
    }
}

class GetTransactionsByCategoryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(categoryId: String): Flow<List<Transaction>> {
        return repository.getTransactionsByCategory(categoryId)
    }
}

class GetRecentTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(limit: Int = 5): Flow<List<Transaction>> {
        return repository.getRecentTransactions(limit)
    }
}

class GetSumByTypeAndDateRangeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(type: String, startDate: Long, endDate: Long): Flow<Long> {
        return repository.getSumByTypeAndDateRange(type, startDate, endDate)
    }
}

class GetSumByAccountAndDateRangeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(accountId: String, startDate: Long, endDate: Long): Flow<Long> {
        return repository.getSumByAccountAndDateRange(accountId, startDate, endDate)
    }
}

class GetTopCategoriesByExpenseUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(
        startDate: Long,
        endDate: Long,
        limit: Int = 3
    ): Flow<List<Pair<String, Long>>> {
        return repository.getTopCategoriesByExpense(startDate, endDate, limit)
    }
}

class AddTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.addTransaction(transaction)
    }
}

class UpdateTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.updateTransaction(transaction)
    }
}

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.deleteTransaction(transaction)
    }
}

class SoftDeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: String) {
        repository.softDeleteTransaction(id)
    }
}

class RestoreTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: String) {
        repository.restoreTransaction(id)
    }
}

class PurgeSoftDeletedUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(threshold: Long) {
        repository.purgeSoftDeleted(threshold)
    }
}

class CheckAccountTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(accountId: String): Boolean {
        return repository.hasTransactions(accountId)
    }
}
