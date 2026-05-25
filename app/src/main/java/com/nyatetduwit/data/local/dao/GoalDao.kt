package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {

    @Query("SELECT * FROM goals WHERE is_active = 1 ORDER BY created_at DESC")
    fun getAllActiveGoals(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals ORDER BY created_at DESC")
    fun getAllGoals(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goals WHERE id = :id")
    suspend fun getGoalById(id: String): GoalEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: GoalEntity)

    @Update
    suspend fun update(goal: GoalEntity)

    @Delete
    suspend fun delete(goal: GoalEntity)

    @Query("UPDATE goals SET is_active = 0, updated_at = :updatedAt WHERE id = :id")
    suspend fun deactivate(id: String, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE goals SET current_amount = current_amount + :amount, updated_at = :updatedAt WHERE id = :id")
    suspend fun addProgress(id: String, amount: Long, updatedAt: Long = System.currentTimeMillis())

    @Query("SELECT * FROM goals ORDER BY created_at DESC")
    suspend fun getAllGoalsSync(): List<GoalEntity>

    @Query("DELETE FROM goals")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(goals: List<GoalEntity>)
}
