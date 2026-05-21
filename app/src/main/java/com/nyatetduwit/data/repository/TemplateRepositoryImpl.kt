package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.dao.TemplateDao
import com.nyatetduwit.data.local.dao.TransactionDao
import com.nyatetduwit.data.local.entity.TemplateEntity
import com.nyatetduwit.domain.model.Template
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.repository.TemplateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TemplateRepositoryImpl @Inject constructor(
    private val templateDao: TemplateDao,
    private val transactionDao: TransactionDao,
) : TemplateRepository {

    override fun getAllTemplates(): Flow<List<Template>> {
        return templateDao.getAllTemplates().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getPinnedTemplates(): Flow<List<Template>> {
        return templateDao.getPinnedTemplates().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTemplateById(id: String): Flow<Template?> {
        return templateDao.getAllTemplates().map { entities ->
            entities.find { it.id == id }?.toDomain()
        }
    }

    override fun getRecentTemplates(limit: Int): Flow<List<Template>> {
        return templateDao.getRecentTemplates(limit).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTemplatesByType(type: String): Flow<List<Template>> {
        return templateDao.getTemplatesByType(type).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addTemplate(template: Template) {
        templateDao.insert(template.toEntity())
    }

    override suspend fun updateTemplate(template: Template) {
        templateDao.update(template.toEntity())
    }

    override suspend fun deleteTemplate(template: Template) {
        templateDao.delete(template.toEntity())
    }

    override suspend fun incrementUsageCount(id: String) {
        templateDao.incrementUsageCount(id)
    }

    override suspend fun togglePin(id: String, isPinned: Boolean) {
        templateDao.togglePin(id, isPinned)
    }

    override suspend fun createFromTransaction(
        transactionId: String,
        name: String,
    ): Template {
        val transaction = transactionDao.getTransactionById(transactionId)
            ?: throw IllegalArgumentException("Transaction not found")

        val template = Template(
            id = UUID.randomUUID().toString(),
            name = name,
            type = TransactionType.fromValue(transaction.type),
            amount = transaction.amount,
            categoryId = transaction.categoryId,
            accountId = transaction.accountId,
            notes = transaction.notes,
            usageCount = 0,
            lastUsed = null,
            isPinned = false,
            createdAt = System.currentTimeMillis(),
        )

        templateDao.insert(template.toEntity())
        return template
    }

    private fun TemplateEntity.toDomain(): Template {
        return Template(
            id = id,
            name = name,
            type = TransactionType.fromValue(type),
            amount = amount,
            categoryId = categoryId,
            accountId = accountId,
            notes = notes,
            usageCount = usageCount,
            lastUsed = lastUsed,
            isPinned = isPinned,
            createdAt = createdAt,
        )
    }

    private fun Template.toEntity(): TemplateEntity {
        return TemplateEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            name = name,
            type = type.value,
            amount = amount,
            categoryId = categoryId,
            accountId = accountId,
            notes = notes,
            usageCount = usageCount,
            lastUsed = lastUsed,
            isPinned = isPinned,
            createdAt = if (id.isEmpty()) System.currentTimeMillis() else createdAt,
        )
    }
}
