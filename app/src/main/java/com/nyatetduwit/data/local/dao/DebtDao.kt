package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.DebtEntity
import com.nyatetduwit.data.local.entity.DebtPaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtDao {

    @Query("SELECT * FROM debts WHERE is_active = 1 ORDER BY due_date ASC")
    fun getAllActiveDebts(): Flow<List<DebtEntity>>

    @Query("SELECT * FROM debts ORDER BY created_at DESC")
    fun getAllDebts(): Flow<List<DebtEntity>>

    @Query("SELECT * FROM debts WHERE id = :id")
    suspend fun getDebtById(id: String): DebtEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDebt(debt: DebtEntity)

    @Update
    suspend fun updateDebt(debt: DebtEntity)

    @Delete
    suspend fun deleteDebt(debt: DebtEntity)

    @Query("UPDATE debts SET is_active = 0, updated_at = :updatedAt WHERE id = :id")
    suspend fun deactivateDebt(id: String, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE debts SET remaining_amount = remaining_amount - :amount, updated_at = :updatedAt WHERE id = :id")
    suspend fun deductRemaining(id: String, amount: Long, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE debts SET remaining_amount = remaining_amount + :amount, updated_at = :updatedAt WHERE id = :id")
    suspend fun addRemaining(id: String, amount: Long, updatedAt: Long = System.currentTimeMillis())

    @Query("SELECT * FROM debt_payments WHERE debt_id = :debtId ORDER BY date DESC")
    fun getPaymentsByDebt(debtId: String): Flow<List<DebtPaymentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: DebtPaymentEntity)

    @Delete
    suspend fun deletePayment(payment: DebtPaymentEntity)

    @Query("SELECT COALESCE(SUM(amount), 0) FROM debt_payments WHERE debt_id = :debtId")
    suspend fun getTotalPaidByDebt(debtId: String): Long

    @Query("SELECT * FROM debts ORDER BY created_at DESC")
    suspend fun getAllDebtsSync(): List<DebtEntity>

    @Query("DELETE FROM debts")
    suspend fun deleteAllDebts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDebts(debts: List<DebtEntity>)

    @Query("DELETE FROM debt_payments")
    suspend fun deleteAllPayments()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPayments(payments: List<DebtPaymentEntity>)
}
