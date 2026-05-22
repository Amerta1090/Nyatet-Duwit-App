package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.RecurringTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecurringTransactionDao {

    @Query("SELECT * FROM recurring_transactions WHERE is_active = 1 ORDER BY next_due ASC")
    fun getAllActiveRecurring(): Flow<List<RecurringTransactionEntity>>

    @Query("SELECT * FROM recurring_transactions ORDER BY next_due ASC")
    fun getAllRecurring(): Flow<List<RecurringTransactionEntity>>

    @Query("SELECT * FROM recurring_transactions WHERE id = :id")
    suspend fun getRecurringById(id: String): RecurringTransactionEntity?

    @Query(
        """
        SELECT * FROM recurring_transactions 
        WHERE is_active = 1 AND next_due <= :now
        """
    )
    suspend fun getDueRecurring(now: Long = System.currentTimeMillis()): List<RecurringTransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recurring: RecurringTransactionEntity)

    @Update
    suspend fun update(recurring: RecurringTransactionEntity)

    @Delete
    suspend fun delete(recurring: RecurringTransactionEntity)

    @Query("UPDATE recurring_transactions SET is_active = 0 WHERE id = :id")
    suspend fun deactivate(id: String)

    @Query("UPDATE recurring_transactions SET next_due = :nextDue WHERE id = :id")
    suspend fun updateNextDue(id: String, nextDue: Long)

    @Query("UPDATE recurring_transactions SET last_processed = :lastProcessed WHERE id = :id")
    suspend fun updateLastProcessed(id: String, lastProcessed: Long)

    @Query("UPDATE recurring_transactions SET skipped_dates = :skippedDates WHERE id = :id")
    suspend fun updateSkippedDates(id: String, skippedDates: String)

    @Query("SELECT * FROM recurring_transactions ORDER BY next_due ASC")
    suspend fun getAllRecurringSync(): List<RecurringTransactionEntity>

    @Query("DELETE FROM recurring_transactions")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recurring: List<RecurringTransactionEntity>)
}
