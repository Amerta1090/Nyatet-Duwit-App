package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAllAccounts(): Flow<List<Account>>
    fun getAccountById(id: String): Flow<Account?>
    fun getTotalBalance(): Flow<Long>
    suspend fun addAccount(account: Account)
    suspend fun updateAccount(account: Account)
    suspend fun deleteAccount(account: Account)
    suspend fun hasTransactions(accountId: String): Boolean
}
