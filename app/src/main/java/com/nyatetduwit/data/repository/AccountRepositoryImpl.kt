package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.dao.AccountDao
import com.nyatetduwit.data.local.entity.AccountEntity
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.AccountType
import com.nyatetduwit.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
) : AccountRepository {

    override fun getAllAccounts(): Flow<List<Account>> {
        return accountDao.getAllAccounts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getAccountById(id: String): Flow<Account?> {
        return accountDao.getAllAccountsWithHidden().map { entities ->
            entities.find { it.id == id }?.toDomain()
        }
    }

    override fun getTotalBalance(): Flow<Long> {
        return accountDao.getTotalBalance()
    }

    override suspend fun addAccount(account: Account) {
        accountDao.insert(account.toEntity())
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.update(account.toEntity())
    }

    override suspend fun deleteAccount(account: Account) {
        accountDao.delete(account.toEntity())
    }

    override suspend fun hasTransactions(accountId: String): Boolean {
        return accountDao.accountExists(accountId) > 0
    }

    private fun AccountEntity.toDomain(): Account {
        return Account(
            id = id,
            name = name,
            type = AccountType.fromValue(type),
            balance = balance,
            icon = icon,
            color = color,
            isHidden = isHidden,
            orderIndex = orderIndex,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    private fun Account.toEntity(): AccountEntity {
        return AccountEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            name = name,
            type = type.value,
            balance = balance,
            icon = icon,
            color = color,
            isHidden = isHidden,
            orderIndex = orderIndex,
            createdAt = if (id.isEmpty()) System.currentTimeMillis() else createdAt,
            updatedAt = System.currentTimeMillis(),
        )
    }
}
