package com.nyatetduwit.domain.model

data class Budget(
    val id: String,
    val categoryId: String?,
    val amount: Long,
    val period: BudgetPeriod = BudgetPeriod.MONTHLY,
    val startDate: Long,
    val endDate: Long,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)

enum class BudgetPeriod(val value: String) {
    MONTHLY("monthly"),
    WEEKLY("weekly"),
    YEARLY("yearly");

    companion object {
        fun fromValue(value: String): BudgetPeriod {
            return entries.find { it.value == value } ?: MONTHLY
        }
    }
}
