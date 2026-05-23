package com.nyatetduwit.domain.model

data class TransactionSplit(
    val id: String,
    val transactionId: String,
    val categoryId: String,
    val amount: Long,
    val notes: String? = null,
)
