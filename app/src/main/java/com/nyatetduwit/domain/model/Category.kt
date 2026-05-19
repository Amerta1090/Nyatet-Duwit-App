package com.nyatetduwit.domain.model

data class Category(
    val id: String,
    val name: String,
    val type: CategoryType,
    val icon: String,
    val color: String,
    val parentId: String?,
    val isDefault: Boolean,
    val orderIndex: Int,
    val createdAt: Long,
)

enum class CategoryType(val value: String) {
    INCOME("income"),
    EXPENSE("expense");

    companion object {
        fun fromValue(value: String): CategoryType {
            return entries.find { it.value == value } ?: EXPENSE
        }
    }
}
