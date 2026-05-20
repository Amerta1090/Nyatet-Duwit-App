package com.nyatetduwit.domain.model

data class RecurringTransaction(
    val id: String,
    val templateTransactionId: String,
    val frequency: RecurringFrequency = RecurringFrequency.MONTHLY,
    val startDate: Long,
    val endDate: Long?,
    val nextDue: Long,
    val isActive: Boolean = true,
    val lastProcessed: Long?,
    val skippedDates: List<Long> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)

enum class RecurringFrequency(val value: String) {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    YEARLY("yearly");

    companion object {
        fun fromValue(value: String): RecurringFrequency {
            return entries.find { it.value == value } ?: MONTHLY
        }
    }
}
