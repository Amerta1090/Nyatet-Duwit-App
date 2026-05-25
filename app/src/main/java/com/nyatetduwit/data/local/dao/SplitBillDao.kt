package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.SplitBillEntity
import com.nyatetduwit.data.local.entity.SplitBillPersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SplitBillDao {

    @Query("SELECT * FROM split_bills ORDER BY date DESC")
    fun getAllBills(): Flow<List<SplitBillEntity>>

    @Query("SELECT * FROM split_bills WHERE id = :id")
    suspend fun getBillById(id: String): SplitBillEntity?

    @Query("SELECT * FROM split_bill_persons WHERE split_bill_id = :billId")
    fun getPersonsByBill(billId: String): Flow<List<SplitBillPersonEntity>>

    @Query("SELECT * FROM split_bill_persons WHERE split_bill_id = :billId")
    suspend fun getPersonsByBillSync(billId: String): List<SplitBillPersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: SplitBillEntity)

    @Update
    suspend fun updateBill(bill: SplitBillEntity)

    @Delete
    suspend fun deleteBill(bill: SplitBillEntity)

    @Query("UPDATE split_bills SET is_settled = 1, updated_at = :updatedAt WHERE id = :id")
    suspend fun markSettled(id: String, updatedAt: Long = System.currentTimeMillis())

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: SplitBillPersonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersons(persons: List<SplitBillPersonEntity>)

    @Update
    suspend fun updatePerson(person: SplitBillPersonEntity)

    @Query("UPDATE split_bill_persons SET is_paid = 1 WHERE id = :personId")
    suspend fun markPersonPaid(personId: String)

    @Query("DELETE FROM split_bill_persons WHERE split_bill_id = :billId")
    suspend fun deletePersonsByBill(billId: String)

    @Query("SELECT * FROM split_bills ORDER BY date DESC")
    suspend fun getAllBillsSync(): List<SplitBillEntity>

    @Query("DELETE FROM split_bills")
    suspend fun deleteAllBills()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBills(bills: List<SplitBillEntity>)
}
