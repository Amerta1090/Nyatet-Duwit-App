package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.dao.TransactionSplitDao
import com.nyatetduwit.data.local.entity.TransactionSplitEntity
import com.nyatetduwit.domain.model.TransactionSplit
import com.nyatetduwit.domain.repository.TransactionSplitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TransactionSplitRepositoryImpl @Inject constructor(
    private val splitDao: TransactionSplitDao,
) : TransactionSplitRepository {

    override fun getSplitsByTransaction(transactionId: String): Flow<List<TransactionSplit>> {
        return splitDao.getSplitsByTransaction(transactionId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getSplitsByTransactionSync(transactionId: String): List<TransactionSplit> {
        return splitDao.getSplitsByTransactionSync(transactionId).map { it.toDomain() }
    }

    override suspend fun saveSplits(transactionId: String, splits: List<TransactionSplit>) {
        splitDao.deleteByTransaction(transactionId)
        if (splits.isNotEmpty()) {
            splitDao.insertAll(splits.map { it.toEntity(transactionId) })
        }
    }

    override suspend fun deleteByTransaction(transactionId: String) {
        splitDao.deleteByTransaction(transactionId)
    }

    private fun TransactionSplitEntity.toDomain(): TransactionSplit {
        return TransactionSplit(
            id = id,
            transactionId = transactionId,
            categoryId = categoryId,
            amount = amount,
            notes = notes,
        )
    }

    private fun TransactionSplit.toEntity(transactionId: String): TransactionSplitEntity {
        return TransactionSplitEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            transactionId = transactionId,
            categoryId = categoryId,
            amount = amount,
            notes = notes,
        )
    }
}
