package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.dao.RecurringTransactionDao
import com.nyatetduwit.data.local.entity.RecurringTransactionEntity
import com.nyatetduwit.domain.model.RecurringFrequency
import com.nyatetduwit.domain.model.RecurringTransaction
import com.nyatetduwit.domain.repository.RecurringTransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class RecurringTransactionRepositoryImpl @Inject constructor(
    private val recurringDao: RecurringTransactionDao,
) : RecurringTransactionRepository {

    override fun getAllActiveRecurring(): Flow<List<RecurringTransaction>> {
        return recurringDao.getAllActiveRecurring().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getAllRecurring(): Flow<List<RecurringTransaction>> {
        return recurringDao.getAllRecurring().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getRecurringById(id: String): RecurringTransaction? {
        return recurringDao.getRecurringById(id)?.toDomain()
    }

    override suspend fun getDueRecurring(now: Long): List<RecurringTransaction> {
        return recurringDao.getDueRecurring(now).map { it.toDomain() }
    }

    override suspend fun addRecurring(recurring: RecurringTransaction) {
        recurringDao.insert(recurring.toEntity())
    }

    override suspend fun updateRecurring(recurring: RecurringTransaction) {
        recurringDao.update(recurring.toEntity())
    }

    override suspend fun deleteRecurring(recurring: RecurringTransaction) {
        recurringDao.delete(recurring.toEntity())
    }

    override suspend fun deactivateRecurring(id: String) {
        recurringDao.deactivate(id)
    }

    override suspend fun skipInstance(id: String, skipDate: Long) {
        val recurring = recurringDao.getRecurringById(id) ?: return
        val currentSkipped = if (recurring.skippedDates.isEmpty()) {
            emptyList()
        } else {
            recurring.skippedDates.split(",").mapNotNull { it.toLongOrNull() }
        }
        if (skipDate !in currentSkipped) {
            val newSkipped = currentSkipped + skipDate
            recurringDao.updateSkippedDates(id, newSkipped.joinToString(","))
        }
    }

    private fun RecurringTransactionEntity.toDomain(): RecurringTransaction {
        val skippedList = if (skippedDates.isEmpty()) {
            emptyList()
        } else {
            skippedDates.split(",").mapNotNull { it.toLongOrNull() }
        }
        return RecurringTransaction(
            id = id,
            templateTransactionId = templateTransactionId,
            frequency = RecurringFrequency.fromValue(frequency),
            startDate = startDate,
            endDate = endDate,
            nextDue = nextDue,
            isActive = isActive,
            lastProcessed = lastProcessed,
            skippedDates = skippedList,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    private fun RecurringTransaction.toEntity(): RecurringTransactionEntity {
        val skippedStr = skippedDates.joinToString(",")
        return RecurringTransactionEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            templateTransactionId = templateTransactionId,
            frequency = frequency.value,
            startDate = startDate,
            endDate = endDate,
            nextDue = nextDue,
            isActive = isActive,
            lastProcessed = lastProcessed,
            skippedDates = skippedStr,
            createdAt = if (id.isEmpty()) System.currentTimeMillis() else createdAt,
            updatedAt = System.currentTimeMillis(),
        )
    }
}
