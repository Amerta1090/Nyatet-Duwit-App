package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.TransactionSplitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionSplitDao {

    @Query("SELECT * FROM transaction_splits WHERE transaction_id = :transactionId ORDER BY amount DESC")
    fun getSplitsByTransaction(transactionId: String): Flow<List<TransactionSplitEntity>>

    @Query("SELECT * FROM transaction_splits WHERE transaction_id = :transactionId ORDER BY amount DESC")
    suspend fun getSplitsByTransactionSync(transactionId: String): List<TransactionSplitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(split: TransactionSplitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(splits: List<TransactionSplitEntity>)

    @Update
    suspend fun update(split: TransactionSplitEntity)

    @Delete
    suspend fun delete(split: TransactionSplitEntity)

    @Query("DELETE FROM transaction_splits WHERE transaction_id = :transactionId")
    suspend fun deleteByTransaction(transactionId: String)

    @Query("SELECT * FROM transaction_splits")
    suspend fun getAllSplitsSync(): List<TransactionSplitEntity>

    @Query("DELETE FROM transaction_splits")
    suspend fun deleteAll()
}
