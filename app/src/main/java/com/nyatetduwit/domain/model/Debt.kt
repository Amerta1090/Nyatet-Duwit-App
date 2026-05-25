package com.nyatetduwit.domain.model

data class Debt(
    val id: String,
    val type: DebtType,
    val personName: String,
    val amount: Long,
    val remainingAmount: Long,
    val dueDate: Long? = null,
    val notes: String? = null,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
) {
    val paidAmount: Long get() = amount - remainingAmount
    val progress: Float get() = if (amount > 0) paidAmount.toFloat() / amount else 0f
    val isPaid: Boolean get() = remainingAmount <= 0
}

enum class DebtType(val value: String) {
    OWE("owe"),
    LENT("lent");

    companion object {
        fun fromValue(value: String): DebtType {
            return entries.find { it.value == value } ?: OWE
        }
    }
}
