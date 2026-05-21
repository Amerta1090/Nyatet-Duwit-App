package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAllTransactions(): Flow<List<Transaction>>
    fun getTransactionById(id: String): Flow<Transaction?>
    fun getTransactionsByAccount(accountId: String): Flow<List<Transaction>>
    fun getTransactionsByType(type: String): Flow<List<Transaction>>
    fun getTransactionsByDateRange(startDate: Long, endDate: Long): Flow<List<Transaction>>
    fun getTransactionsByCategory(categoryId: String): Flow<List<Transaction>>
    fun getRecentTransactions(limit: Int): Flow<List<Transaction>>
    fun getSumByTypeAndDateRange(type: String, startDate: Long, endDate: Long): Flow<Long>
    fun getSumByAccountAndDateRange(accountId: String, startDate: Long, endDate: Long): Flow<Long>
    fun getTopCategoriesByExpense(startDate: Long, endDate: Long, limit: Int): Flow<List<Pair<String, Long>>>
    fun searchTransactions(query: String): Flow<List<Transaction>>
    fun filterTransactions(
        type: String?,
        categoryId: String?,
        accountId: String?,
        startDate: Long?,
        endDate: Long?
    ): Flow<List<Transaction>>
    fun searchAndFilterTransactions(
        query: String?,
        type: String?,
        categoryId: String?,
        accountId: String?,
        startDate: Long?,
        endDate: Long?
    ): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    suspend fun softDeleteTransaction(id: String)
    suspend fun restoreTransaction(id: String)
    suspend fun purgeSoftDeleted(threshold: Long)
    suspend fun hasTransactions(accountId: String): Boolean
}
