package com.nyatetduwit.domain.usecase.template

import com.nyatetduwit.domain.model.Template
import com.nyatetduwit.domain.repository.TemplateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTemplatesUseCase @Inject constructor(
    private val repository: TemplateRepository
) {
    operator fun invoke(): Flow<List<Template>> {
        return repository.getAllTemplates()
    }
}

class GetPinnedTemplatesUseCase @Inject constructor(
    private val repository: TemplateRepository
) {
    operator fun invoke(): Flow<List<Template>> {
        return repository.getPinnedTemplates()
    }
}

class GetTemplateByIdUseCase @Inject constructor(
    private val repository: TemplateRepository
) {
    operator fun invoke(id: String): Flow<Template?> {
        return repository.getTemplateById(id)
    }
}

class GetRecentTemplatesUseCase @Inject constructor(
    private val repository: TemplateRepository
) {
    operator fun invoke(limit: Int = 5): Flow<List<Template>> {
        return repository.getRecentTemplates(limit)
    }
}

class GetTemplatesByTypeUseCase @Inject constructor(
    private val repository: TemplateRepository
) {
    operator fun invoke(type: String): Flow<List<Template>> {
        return repository.getTemplatesByType(type)
    }
}

class AddTemplateUseCase @Inject constructor(
    private val repository: TemplateRepository
) {
    suspend operator fun invoke(template: Template) {
        repository.addTemplate(template)
    }
}

class UpdateTemplateUseCase @Inject constructor(
    private val repository: TemplateRepository
) {
    suspend operator fun invoke(template: Template) {
        repository.updateTemplate(template)
    }
}

class DeleteTemplateUseCase @Inject constructor(
    private val repository: TemplateRepository
) {
    suspend operator fun invoke(template: Template) {
        repository.deleteTemplate(template)
    }
}

class IncrementTemplateUsageUseCase @Inject constructor(
    private val repository: TemplateRepository
) {
    suspend operator fun invoke(id: String) {
        repository.incrementUsageCount(id)
    }
}

class ToggleTemplatePinUseCase @Inject constructor(
    private val repository: TemplateRepository
) {
    suspend operator fun invoke(id: String, isPinned: Boolean) {
        repository.togglePin(id, isPinned)
    }
}

class CreateTemplateFromTransactionUseCase @Inject constructor(
    private val repository: TemplateRepository
) {
    suspend operator fun invoke(transactionId: String, name: String): Template {
        return repository.createFromTransaction(transactionId, name)
    }
}
