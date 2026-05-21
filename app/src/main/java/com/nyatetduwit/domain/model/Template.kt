package com.nyatetduwit.domain.model

data class Template(
    val id: String,
    val name: String,
    val type: TransactionType,
    val amount: Long,
    val categoryId: String?,
    val accountId: String?,
    val notes: String?,
    val usageCount: Int,
    val lastUsed: Long?,
    val isPinned: Boolean,
    val createdAt: Long,
)
