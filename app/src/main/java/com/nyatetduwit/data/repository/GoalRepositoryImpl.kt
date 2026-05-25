package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.dao.GoalDao
import com.nyatetduwit.data.local.entity.GoalEntity
import com.nyatetduwit.domain.model.Goal
import com.nyatetduwit.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val goalDao: GoalDao,
) : GoalRepository {

    override fun getAllActiveGoals(): Flow<List<Goal>> {
        return goalDao.getAllActiveGoals().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getAllGoals(): Flow<List<Goal>> {
        return goalDao.getAllGoals().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getGoalById(id: String): Goal? {
        return goalDao.getGoalById(id)?.toDomain()
    }

    override suspend fun addGoal(goal: Goal) {
        goalDao.insert(goal.toEntity())
    }

    override suspend fun updateGoal(goal: Goal) {
        goalDao.update(goal.toEntity())
    }

    override suspend fun deleteGoal(goal: Goal) {
        goalDao.delete(goal.toEntity())
    }

    override suspend fun deactivateGoal(id: String) {
        goalDao.deactivate(id)
    }

    override suspend fun addProgress(id: String, amount: Long) {
        goalDao.addProgress(id, amount)
    }

    private fun GoalEntity.toDomain(): Goal {
        return Goal(
            id = id,
            name = name,
            targetAmount = targetAmount,
            currentAmount = currentAmount,
            deadline = deadline,
            icon = icon,
            color = color,
            isActive = isActive,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    private fun Goal.toEntity(): GoalEntity {
        return GoalEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            name = name,
            targetAmount = targetAmount,
            currentAmount = currentAmount,
            deadline = deadline,
            icon = icon,
            color = color,
            isActive = isActive,
            createdAt = if (id.isEmpty()) System.currentTimeMillis() else createdAt,
            updatedAt = System.currentTimeMillis(),
        )
    }
}
