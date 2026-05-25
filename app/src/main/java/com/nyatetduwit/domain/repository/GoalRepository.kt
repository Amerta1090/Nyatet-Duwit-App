package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.Goal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    fun getAllActiveGoals(): Flow<List<Goal>>
    fun getAllGoals(): Flow<List<Goal>>
    suspend fun getGoalById(id: String): Goal?
    suspend fun addGoal(goal: Goal)
    suspend fun updateGoal(goal: Goal)
    suspend fun deleteGoal(goal: Goal)
    suspend fun deactivateGoal(id: String)
    suspend fun addProgress(id: String, amount: Long)
}
