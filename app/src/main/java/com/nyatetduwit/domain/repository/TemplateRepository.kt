package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.Template
import kotlinx.coroutines.flow.Flow

interface TemplateRepository {
    fun getAllTemplates(): Flow<List<Template>>
    fun getPinnedTemplates(): Flow<List<Template>>
    fun getTemplateById(id: String): Flow<Template?>
    fun getRecentTemplates(limit: Int): Flow<List<Template>>
    fun getTemplatesByType(type: String): Flow<List<Template>>
    suspend fun addTemplate(template: Template)
    suspend fun updateTemplate(template: Template)
    suspend fun deleteTemplate(template: Template)
    suspend fun incrementUsageCount(id: String)
    suspend fun togglePin(id: String, isPinned: Boolean)
    suspend fun createFromTransaction(
        transactionId: String,
        name: String,
    ): Template
}
