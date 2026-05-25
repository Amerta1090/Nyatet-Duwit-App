package com.nyatetduwit.domain.model

data class Investment(
    val id: String,
    val name: String,
    val type: InvestmentType,
    val currentValue: Long,
    val costBasis: Long = 0L,
    val currencyCode: String = "IDR",
    val accountId: String? = null,
    val notes: String? = null,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
) {
    val profitLoss: Long get() = currentValue - costBasis
    val profitLossPercent: Float
        get() = if (costBasis > 0) (profitLoss.toFloat() / costBasis) * 100f else 0f
    val isProfitable: Boolean get() = profitLoss >= 0
}

enum class InvestmentType(val value: String) {
    STOCK("stock"),
    MUTUAL_FUND("mutual_fund"),
    CRYPTO("crypto"),
    BOND("bond"),
    GOLD("gold"),
    PROPERTY("property"),
    DEPOSIT("deposit"),
    OTHER("other");

    companion object {
        fun fromValue(value: String): InvestmentType {
            return entries.find { it.value == value } ?: OTHER
        }
    }
}
