package com.nyatetduwit.domain.model

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Long,
    val accountId: String,
    val categoryId: String?,
    val toAccountId: String?,
    val notes: String?,
    val dateTime: Long,
    val createdAt: Long,
    val updatedAt: Long,
    val isDeleted: Boolean = false,
    val deletedAt: Long? = null,
    val currencyCode: String = "IDR",
    val exchangeRate: Double = 1.0,
    val attachmentPath: String? = null,
    val syncStatus: String = "local_only",
    val lastSyncedAt: Long? = null,
    val version: Int = 1,
)

enum class TransactionType(val value: String) {
    INCOME("income"),
    EXPENSE("expense"),
    TRANSFER("transfer");

    companion object {
        fun fromValue(value: String): TransactionType {
            return entries.find { it.value == value } ?: EXPENSE
        }
    }
}
