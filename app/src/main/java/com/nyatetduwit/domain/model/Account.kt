package com.nyatetduwit.domain.model

data class Account(
    val id: String,
    val name: String,
    val type: AccountType,
    val balance: Long,
    val icon: String,
    val color: String,
    val currencyCode: String = "IDR",
    val isHidden: Boolean,
    val orderIndex: Int,
    val createdAt: Long,
    val updatedAt: Long,
    val syncStatus: String = "local_only",
    val lastSyncedAt: Long? = null,
    val version: Int = 1,
)

enum class AccountType(val value: String) {
    CASH("cash"),
    BANK("bank"),
    E_WALLET("e_wallet");

    companion object {
        fun fromValue(value: String): AccountType {
            return entries.find { it.value == value } ?: CASH
        }
    }
}
