package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query(
        """
        SELECT * FROM transactions 
        WHERE is_deleted = 0 
        ORDER BY date_time DESC
        """
    )
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT * FROM transactions 
        WHERE id = :id
        """
    )
    suspend fun getTransactionById(id: String): TransactionEntity?

    @Query(
        """
        SELECT * FROM transactions 
        WHERE account_id = :accountId AND is_deleted = 0 
        ORDER BY date_time DESC
        """
    )
    fun getTransactionsByAccount(accountId: String): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT * FROM transactions 
        WHERE type = :type AND is_deleted = 0 
        ORDER BY date_time DESC
        """
    )
    fun getTransactionsByType(type: String): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT * FROM transactions 
        WHERE date_time >= :startDate AND date_time <= :endDate AND is_deleted = 0 
        ORDER BY date_time DESC
        """
    )
    fun getTransactionsByDateRange(startDate: Long, endDate: Long): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT * FROM transactions 
        WHERE category_id = :categoryId AND is_deleted = 0 
        ORDER BY date_time DESC
        """
    )
    fun getTransactionsByCategory(categoryId: String): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query(
        """
        UPDATE transactions 
        SET is_deleted = 1, deleted_at = :deletedAt 
        WHERE id = :id
        """
    )
    suspend fun softDelete(id: String, deletedAt: Long = System.currentTimeMillis())

    @Query(
        """
        UPDATE transactions 
        SET is_deleted = 0, deleted_at = NULL 
        WHERE id = :id
        """
    )
    suspend fun restoreTransaction(id: String)

    @Query(
        """
        DELETE FROM transactions 
        WHERE is_deleted = 1 AND deleted_at < :threshold
        """
    )
    suspend fun purgeSoftDeleted(threshold: Long = System.currentTimeMillis())

    @Query(
        """
        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        WHERE type = :type AND is_deleted = 0 
        AND date_time >= :startDate AND date_time <= :endDate
        """
    )
    fun getSumByTypeAndDateRange(type: String, startDate: Long, endDate: Long): Flow<Long>

    @Query(
        """
        SELECT COALESCE(SUM(amount), 0) FROM transactions 
        WHERE account_id = :accountId AND is_deleted = 0 
        AND date_time >= :startDate AND date_time <= :endDate
        """
    )
    fun getSumByAccountAndDateRange(accountId: String, startDate: Long, endDate: Long): Flow<Long>

    @Query(
        """
        SELECT COUNT(*) FROM transactions 
        WHERE account_id = :accountId AND is_deleted = 0
        """
    )
    suspend fun countTransactionsByAccount(accountId: String): Int

    @Query(
        """
        SELECT * FROM transactions 
        WHERE is_deleted = 0 
        ORDER BY date_time DESC 
        LIMIT :limit
        """
    )
    fun getRecentTransactions(limit: Int = 5): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT category_id, SUM(amount) as total 
        FROM transactions 
        WHERE type = 'expense' AND is_deleted = 0 
        AND date_time >= :startDate AND date_time <= :endDate
        GROUP BY category_id 
        ORDER BY total DESC 
        LIMIT :limit
        """
    )
    fun getTopCategoriesByExpense(
        startDate: Long,
        endDate: Long,
        limit: Int = 3
    ): Flow<List<CategoryExpenseSummary>>
}

data class CategoryExpenseSummary(
    @ColumnInfo(name = "category_id")
    val categoryId: String,
    @ColumnInfo(name = "total")
    val total: Long,
)
