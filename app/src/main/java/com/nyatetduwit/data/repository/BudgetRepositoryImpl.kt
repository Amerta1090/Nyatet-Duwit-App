package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.dao.BudgetDao
import com.nyatetduwit.data.local.dao.TransactionDao
import com.nyatetduwit.data.local.entity.BudgetEntity
import com.nyatetduwit.domain.model.Budget
import com.nyatetduwit.domain.model.BudgetPeriod
import com.nyatetduwit.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao,
    private val transactionDao: TransactionDao,
) : BudgetRepository {

    override fun getAllActiveBudgets(): Flow<List<Budget>> {
        return budgetDao.getAllActiveBudgets().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getAllBudgets(): Flow<List<Budget>> {
        return budgetDao.getAllBudgets().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getCurrentBudgets(date: Long): Flow<List<Budget>> {
        return budgetDao.getCurrentBudgets(date).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getBudgetById(id: String): Budget? {
        return budgetDao.getBudgetById(id)?.toDomain()
    }

    override suspend fun getBudgetByCategoryId(categoryId: String): Budget? {
        return budgetDao.getBudgetByCategoryId(categoryId)?.toDomain()
    }

    override suspend fun getTotalBudget(): Budget? {
        return budgetDao.getTotalBudget()?.toDomain()
    }

    override suspend fun addBudget(budget: Budget) {
        budgetDao.insert(budget.toEntity())
    }

    override suspend fun updateBudget(budget: Budget) {
        budgetDao.update(budget.toEntity())
    }

    override suspend fun deleteBudget(budget: Budget) {
        budgetDao.delete(budget.toEntity())
    }

    override suspend fun deactivateBudget(id: String) {
        budgetDao.deactivate(id)
    }

    override suspend fun getBudgetProgress(
        categoryId: String?,
        startDate: Long,
        endDate: Long
    ): Pair<Long, Long> {
        val spent = if (categoryId != null) {
            transactionDao.getSumByCategoryAndDateRange(categoryId, startDate, endDate)
        } else {
            transactionDao.getSumByTypeAndDateRangeSync("expense", startDate, endDate)
        }
        return spent to (endDate - startDate)
    }

    private fun BudgetEntity.toDomain(): Budget {
        return Budget(
            id = id,
            categoryId = categoryId,
            amount = amount,
            period = BudgetPeriod.fromValue(period),
            startDate = startDate,
            endDate = endDate,
            isActive = isActive,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    private fun Budget.toEntity(): BudgetEntity {
        return BudgetEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            categoryId = categoryId,
            amount = amount,
            period = period.value,
            startDate = startDate,
            endDate = endDate,
            isActive = isActive,
            createdAt = if (id.isEmpty()) System.currentTimeMillis() else createdAt,
            updatedAt = System.currentTimeMillis(),
        )
    }
}
