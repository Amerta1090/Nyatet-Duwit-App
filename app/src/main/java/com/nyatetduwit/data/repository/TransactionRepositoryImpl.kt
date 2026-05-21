package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.dao.CategoryExpenseSummary
import com.nyatetduwit.data.local.dao.DailyExpenseSummary
import com.nyatetduwit.data.local.dao.TransactionDao
import com.nyatetduwit.data.local.entity.TransactionEntity
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
) : TransactionRepository {

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTransactionById(id: String): Flow<Transaction?> {
        return transactionDao.getAllTransactions().map { entities ->
            entities.find { it.id == id }?.toDomain()
        }
    }

    override fun getTransactionsByAccount(accountId: String): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByAccount(accountId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTransactionsByType(type: String): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByType(type).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTransactionsByDateRange(
        startDate: Long,
        endDate: Long
    ): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByDateRange(startDate, endDate).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTransactionsByCategory(categoryId: String): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByCategory(categoryId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getRecentTransactions(limit: Int): Flow<List<Transaction>> {
        return transactionDao.getRecentTransactions(limit).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getSumByTypeAndDateRange(
        type: String,
        startDate: Long,
        endDate: Long
    ): Flow<Long> {
        return transactionDao.getSumByTypeAndDateRange(type, startDate, endDate)
    }

    override fun getSumByAccountAndDateRange(
        accountId: String,
        startDate: Long,
        endDate: Long
    ): Flow<Long> {
        return transactionDao.getSumByAccountAndDateRange(accountId, startDate, endDate)
    }

    override fun getTopCategoriesByExpense(
        startDate: Long,
        endDate: Long,
        limit: Int
    ): Flow<List<Pair<String, Long>>> {
        return transactionDao.getTopCategoriesByExpense(startDate, endDate, limit).map { summaries ->
            summaries.map { it.categoryId to it.total }
        }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insert(transaction.toEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.update(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.delete(transaction.toEntity())
    }

    override suspend fun softDeleteTransaction(id: String) {
        transactionDao.softDelete(id)
    }

    override suspend fun restoreTransaction(id: String) {
        transactionDao.restoreTransaction(id)
    }

    override suspend fun purgeSoftDeleted(threshold: Long) {
        transactionDao.purgeSoftDeleted(threshold)
    }

    override suspend fun hasTransactions(accountId: String): Boolean {
        return transactionDao.countTransactionsByAccount(accountId) > 0
    }

    override fun searchTransactions(query: String): Flow<List<Transaction>> {
        return transactionDao.searchTransactions(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun filterTransactions(
        type: String?,
        categoryId: String?,
        accountId: String?,
        startDate: Long?,
        endDate: Long?
    ): Flow<List<Transaction>> {
        return transactionDao.filterTransactions(type, categoryId, accountId, startDate, endDate).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun searchAndFilterTransactions(
        query: String?,
        type: String?,
        categoryId: String?,
        accountId: String?,
        startDate: Long?,
        endDate: Long?
    ): Flow<List<Transaction>> {
        return transactionDao.searchAndFilterTransactions(query, type, categoryId, accountId, startDate, endDate).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getDailyExpenseTrend(startDate: Long, endDate: Long): Flow<List<Pair<String, Long>>> {
        return transactionDao.getDailyExpenseTrend(startDate, endDate).map { summaries ->
            summaries.map { it.day to it.total }
        }
    }

    override suspend fun getBiggestExpense(startDate: Long, endDate: Long): Transaction? {
        return transactionDao.getBiggestExpense(startDate, endDate)?.toDomain()
    }

    override suspend fun getActiveDaysCount(startDate: Long, endDate: Long): Int {
        return transactionDao.getActiveDaysCount(startDate, endDate)
    }

    override suspend fun getTransactionCount(startDate: Long, endDate: Long): Int {
        return transactionDao.getTransactionCount(startDate, endDate)
    }

    private fun TransactionEntity.toDomain(): Transaction {
        return Transaction(
            id = id,
            type = TransactionType.fromValue(type),
            amount = amount,
            accountId = accountId,
            categoryId = categoryId,
            toAccountId = toAccountId,
            notes = notes,
            dateTime = dateTime,
            createdAt = createdAt,
            updatedAt = updatedAt,
            isDeleted = isDeleted,
            deletedAt = deletedAt,
        )
    }

    private fun Transaction.toEntity(): TransactionEntity {
        return TransactionEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            type = type.value,
            amount = amount,
            accountId = accountId,
            categoryId = categoryId,
            toAccountId = toAccountId,
            notes = notes,
            dateTime = dateTime,
            createdAt = if (id.isEmpty()) System.currentTimeMillis() else createdAt,
            updatedAt = System.currentTimeMillis(),
            isDeleted = isDeleted,
            deletedAt = deletedAt,
        )
    }
}
