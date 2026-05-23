package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.TransactionTag
import kotlinx.coroutines.flow.Flow

interface TransactionTagRepository {
    fun getTagsByTransaction(transactionId: String): Flow<List<TransactionTag>>
    suspend fun getTagsByTransactionSync(transactionId: String): List<TransactionTag>
    fun getAllTagNames(): Flow<List<String>>
    suspend fun saveTags(transactionId: String, tags: List<TransactionTag>)
    suspend fun deleteByTransaction(transactionId: String)
}
