package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.SplitBill
import kotlinx.coroutines.flow.Flow

interface SplitBillRepository {
    fun getAllBills(): Flow<List<SplitBill>>
    suspend fun getBillById(id: String): SplitBill?
    suspend fun addBill(bill: SplitBill)
    suspend fun updateBill(bill: SplitBill)
    suspend fun deleteBill(bill: SplitBill)
    suspend fun markSettled(id: String)
    suspend fun markPersonPaid(personId: String)
}
