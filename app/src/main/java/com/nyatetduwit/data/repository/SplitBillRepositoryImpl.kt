package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.dao.SplitBillDao
import com.nyatetduwit.data.local.entity.SplitBillEntity
import com.nyatetduwit.data.local.entity.SplitBillPersonEntity
import com.nyatetduwit.domain.model.SplitBill
import com.nyatetduwit.domain.model.SplitBillPerson
import com.nyatetduwit.domain.repository.SplitBillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class SplitBillRepositoryImpl @Inject constructor(
    private val splitBillDao: SplitBillDao,
) : SplitBillRepository {

    override fun getAllBills(): Flow<List<SplitBill>> {
        return splitBillDao.getAllBills().map { entities ->
            entities.map { entity ->
                val persons = splitBillDao.getPersonsByBillSync(entity.id)
                entity.toDomain(persons.map { it.toDomain() })
            }
        }
    }

    override suspend fun getBillById(id: String): SplitBill? {
        val entity = splitBillDao.getBillById(id) ?: return null
        val persons = splitBillDao.getPersonsByBillSync(id)
        return entity.toDomain(persons.map { it.toDomain() })
    }

    override suspend fun addBill(bill: SplitBill) {
        val billId = if (bill.id.isEmpty()) UUID.randomUUID().toString() else bill.id
        splitBillDao.insertBill(bill.toEntity(billId))
        splitBillDao.insertPersons(bill.persons.map { it.toEntity(billId) })
    }

    override suspend fun updateBill(bill: SplitBill) {
        val billId = bill.id
        splitBillDao.updateBill(bill.toEntity(billId))
        splitBillDao.deletePersonsByBill(billId)
        splitBillDao.insertPersons(bill.persons.map { it.toEntity(billId) })
    }

    override suspend fun deleteBill(bill: SplitBill) {
        splitBillDao.deleteBill(bill.toEntity(bill.id))
    }

    override suspend fun markSettled(id: String) {
        splitBillDao.markSettled(id)
    }

    override suspend fun markPersonPaid(personId: String) {
        splitBillDao.markPersonPaid(personId)
    }

    private fun SplitBillEntity.toDomain(persons: List<SplitBillPerson>): SplitBill {
        return SplitBill(
            id = id,
            title = title,
            totalAmount = totalAmount,
            paidBy = paidBy,
            date = date,
            notes = notes,
            isSettled = isSettled,
            persons = persons,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    private fun SplitBillPersonEntity.toDomain(): SplitBillPerson {
        return SplitBillPerson(
            id = id,
            splitBillId = splitBillId,
            name = name,
            amount = amount,
            isPaid = isPaid,
        )
    }

    private fun SplitBill.toEntity(billId: String): SplitBillEntity {
        return SplitBillEntity(
            id = billId,
            title = title,
            totalAmount = totalAmount,
            paidBy = paidBy,
            date = date,
            notes = notes,
            isSettled = isSettled,
            createdAt = if (billId.isEmpty()) System.currentTimeMillis() else createdAt,
            updatedAt = System.currentTimeMillis(),
        )
    }

    private fun SplitBillPerson.toEntity(billId: String): SplitBillPersonEntity {
        return SplitBillPersonEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            splitBillId = billId,
            name = name,
            amount = amount,
            isPaid = isPaid,
        )
    }
}
