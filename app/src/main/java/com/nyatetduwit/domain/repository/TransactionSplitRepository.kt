package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.TransactionSplit
import kotlinx.coroutines.flow.Flow

interface TransactionSplitRepository {
    fun getSplitsByTransaction(transactionId: String): Flow<List<TransactionSplit>>
    suspend fun getSplitsByTransactionSync(transactionId: String): List<TransactionSplit>
    suspend fun saveSplits(transactionId: String, splits: List<TransactionSplit>)
    suspend fun deleteByTransaction(transactionId: String)
}
