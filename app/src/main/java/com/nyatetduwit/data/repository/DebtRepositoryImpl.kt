package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.dao.DebtDao
import com.nyatetduwit.data.local.entity.DebtEntity
import com.nyatetduwit.data.local.entity.DebtPaymentEntity
import com.nyatetduwit.domain.model.Debt
import com.nyatetduwit.domain.model.DebtPayment
import com.nyatetduwit.domain.model.DebtType
import com.nyatetduwit.domain.repository.DebtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class DebtRepositoryImpl @Inject constructor(
    private val debtDao: DebtDao,
) : DebtRepository {

    override fun getAllActiveDebts(): Flow<List<Debt>> {
        return debtDao.getAllActiveDebts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getAllDebts(): Flow<List<Debt>> {
        return debtDao.getAllDebts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getDebtById(id: String): Debt? {
        return debtDao.getDebtById(id)?.toDomain()
    }

    override suspend fun addDebt(debt: Debt) {
        debtDao.insertDebt(debt.toEntity())
    }

    override suspend fun updateDebt(debt: Debt) {
        debtDao.updateDebt(debt.toEntity())
    }

    override suspend fun deleteDebt(debt: Debt) {
        debtDao.deleteDebt(debt.toEntity())
    }

    override suspend fun deactivateDebt(id: String) {
        debtDao.deactivateDebt(id)
    }

    override fun getPaymentsByDebt(debtId: String): Flow<List<DebtPayment>> {
        return debtDao.getPaymentsByDebt(debtId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addPayment(payment: DebtPayment) {
        debtDao.insertPayment(payment.toEntity())
        debtDao.deductRemaining(payment.debtId, payment.amount)
    }

    override suspend fun deletePayment(payment: DebtPayment) {
        debtDao.deletePayment(payment.toEntity())
        debtDao.addRemaining(payment.debtId, payment.amount)
    }

    override suspend fun getTotalPaidByDebt(debtId: String): Long {
        return debtDao.getTotalPaidByDebt(debtId)
    }

    private fun DebtEntity.toDomain(): Debt {
        return Debt(
            id = id,
            type = DebtType.fromValue(type),
            personName = personName,
            amount = amount,
            remainingAmount = remainingAmount,
            dueDate = dueDate,
            notes = notes,
            isActive = isActive,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    private fun Debt.toEntity(): DebtEntity {
        return DebtEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            type = type.value,
            personName = personName,
            amount = amount,
            remainingAmount = remainingAmount,
            dueDate = dueDate,
            notes = notes,
            isActive = isActive,
            createdAt = if (id.isEmpty()) System.currentTimeMillis() else createdAt,
            updatedAt = System.currentTimeMillis(),
        )
    }

    private fun DebtPaymentEntity.toDomain(): DebtPayment {
        return DebtPayment(
            id = id,
            debtId = debtId,
            amount = amount,
            date = date,
            notes = notes,
        )
    }

    private fun DebtPayment.toEntity(): DebtPaymentEntity {
        return DebtPaymentEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            debtId = debtId,
            amount = amount,
            date = date,
            notes = notes,
        )
    }
}
