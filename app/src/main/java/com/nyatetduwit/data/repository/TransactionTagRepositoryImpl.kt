package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.dao.TransactionTagDao
import com.nyatetduwit.data.local.entity.TransactionTagEntity
import com.nyatetduwit.domain.model.TransactionTag
import com.nyatetduwit.domain.repository.TransactionTagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TransactionTagRepositoryImpl @Inject constructor(
    private val tagDao: TransactionTagDao,
) : TransactionTagRepository {

    override fun getTagsByTransaction(transactionId: String): Flow<List<TransactionTag>> {
        return tagDao.getTagsByTransaction(transactionId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTagsByTransactionSync(transactionId: String): List<TransactionTag> {
        return tagDao.getTagsByTransactionSync(transactionId).map { it.toDomain() }
    }

    override fun getAllTagNames(): Flow<List<String>> {
        return tagDao.getAllTagNames()
    }

    override suspend fun saveTags(transactionId: String, tags: List<TransactionTag>) {
        tagDao.deleteByTransaction(transactionId)
        if (tags.isNotEmpty()) {
            tagDao.insertAll(tags.map { it.toEntity(transactionId) })
        }
    }

    override suspend fun deleteByTransaction(transactionId: String) {
        tagDao.deleteByTransaction(transactionId)
    }

    private fun TransactionTagEntity.toDomain(): TransactionTag {
        return TransactionTag(
            id = id,
            transactionId = transactionId,
            tagName = tagName,
        )
    }

    private fun TransactionTag.toEntity(transactionId: String): TransactionTagEntity {
        return TransactionTagEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            transactionId = transactionId,
            tagName = tagName,
        )
    }
}
