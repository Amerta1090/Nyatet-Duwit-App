package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.Debt
import com.nyatetduwit.domain.model.DebtPayment
import kotlinx.coroutines.flow.Flow

interface DebtRepository {
    fun getAllActiveDebts(): Flow<List<Debt>>
    fun getAllDebts(): Flow<List<Debt>>
    suspend fun getDebtById(id: String): Debt?
    suspend fun addDebt(debt: Debt)
    suspend fun updateDebt(debt: Debt)
    suspend fun deleteDebt(debt: Debt)
    suspend fun deactivateDebt(id: String)
    fun getPaymentsByDebt(debtId: String): Flow<List<DebtPayment>>
    suspend fun addPayment(payment: DebtPayment)
    suspend fun deletePayment(payment: DebtPayment)
    suspend fun getTotalPaidByDebt(debtId: String): Long
}
