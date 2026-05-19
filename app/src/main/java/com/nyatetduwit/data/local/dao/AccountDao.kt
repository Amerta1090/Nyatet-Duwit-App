package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts WHERE is_hidden = 0 ORDER BY order_index ASC")
    fun getAllAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts ORDER BY order_index ASC")
    fun getAllAccountsWithHidden(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccountById(id: String): AccountEntity?

    @Query("SELECT COUNT(*) FROM accounts WHERE id = :id")
    suspend fun accountExists(id: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountEntity)

    @Update
    suspend fun update(account: AccountEntity)

    @Delete
    suspend fun delete(account: AccountEntity)

    @Query("SELECT COALESCE(SUM(balance), 0) FROM accounts")
    fun getTotalBalance(): Flow<Long>

    @Query("SELECT COALESCE(SUM(balance), 0) FROM accounts WHERE id = :accountId")
    fun getAccountBalance(accountId: String): Flow<Long>
}
