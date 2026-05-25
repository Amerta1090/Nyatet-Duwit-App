package com.nyatetduwit.domain.model

data class DebtPayment(
    val id: String,
    val debtId: String,
    val amount: Long,
    val date: Long = System.currentTimeMillis(),
    val notes: String? = null,
)
