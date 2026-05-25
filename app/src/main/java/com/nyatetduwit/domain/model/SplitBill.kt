package com.nyatetduwit.domain.model

data class SplitBill(
    val id: String,
    val title: String,
    val totalAmount: Long,
    val paidBy: String,
    val date: Long = System.currentTimeMillis(),
    val notes: String? = null,
    val isSettled: Boolean = false,
    val persons: List<SplitBillPerson> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
) {
    val myShare: Long get() = if (persons.isNotEmpty()) totalAmount / (persons.size + 1) else totalAmount
    val isFullyPaid: Boolean get() = persons.all { it.isPaid }
}

data class SplitBillPerson(
    val id: String,
    val splitBillId: String,
    val name: String,
    val amount: Long,
    val isPaid: Boolean = false,
)
