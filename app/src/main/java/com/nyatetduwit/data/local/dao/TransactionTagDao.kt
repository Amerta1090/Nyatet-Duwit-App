package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.TransactionTagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionTagDao {

    @Query("SELECT * FROM transaction_tags WHERE transaction_id = :transactionId ORDER BY tag_name ASC")
    fun getTagsByTransaction(transactionId: String): Flow<List<TransactionTagEntity>>

    @Query("SELECT * FROM transaction_tags WHERE transaction_id = :transactionId ORDER BY tag_name ASC")
    suspend fun getTagsByTransactionSync(transactionId: String): List<TransactionTagEntity>

    @Query("SELECT DISTINCT tag_name FROM transaction_tags ORDER BY tag_name ASC")
    fun getAllTagNames(): Flow<List<String>>

    @Query("SELECT DISTINCT tag_name FROM transaction_tags ORDER BY tag_name ASC")
    suspend fun getAllTagNamesSync(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tag: TransactionTagEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tags: List<TransactionTagEntity>)

    @Delete
    suspend fun delete(tag: TransactionTagEntity)

    @Query("DELETE FROM transaction_tags WHERE transaction_id = :transactionId")
    suspend fun deleteByTransaction(transactionId: String)

    @Query("SELECT * FROM transaction_tags")
    suspend fun getAllTagsSync(): List<TransactionTagEntity>

    @Query("DELETE FROM transaction_tags")
    suspend fun deleteAll()
}
