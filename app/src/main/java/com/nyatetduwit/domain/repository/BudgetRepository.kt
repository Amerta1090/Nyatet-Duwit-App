package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.Budget
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getAllActiveBudgets(): Flow<List<Budget>>
    fun getAllBudgets(): Flow<List<Budget>>
    fun getCurrentBudgets(date: Long): Flow<List<Budget>>
    suspend fun getBudgetById(id: String): Budget?
    suspend fun getBudgetByCategoryId(categoryId: String): Budget?
    suspend fun getTotalBudget(): Budget?
    suspend fun addBudget(budget: Budget)
    suspend fun updateBudget(budget: Budget)
    suspend fun deleteBudget(budget: Budget)
    suspend fun deactivateBudget(id: String)
    suspend fun getBudgetProgress(categoryId: String?, startDate: Long, endDate: Long): Pair<Long, Long>
}
