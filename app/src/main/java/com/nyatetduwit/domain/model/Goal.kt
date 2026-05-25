package com.nyatetduwit.domain.model

data class Goal(
    val id: String,
    val name: String,
    val targetAmount: Long,
    val currentAmount: Long = 0L,
    val deadline: Long? = null,
    val icon: String = "flag",
    val color: String = "#1A5C53",
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
) {
    val progress: Float
        get() = if (targetAmount > 0) (currentAmount.toFloat() / targetAmount).coerceIn(0f, 1f) else 0f

    val remainingAmount: Long
        get() = (targetAmount - currentAmount).coerceAtLeast(0)

    val isCompleted: Boolean
        get() = currentAmount >= targetAmount
}
