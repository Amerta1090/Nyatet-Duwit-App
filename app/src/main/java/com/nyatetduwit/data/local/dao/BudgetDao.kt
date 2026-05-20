package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Query("SELECT * FROM budgets WHERE is_active = 1 ORDER BY created_at DESC")
    fun getAllActiveBudgets(): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets ORDER BY created_at DESC")
    fun getAllBudgets(): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetById(id: String): BudgetEntity?

    @Query("SELECT * FROM budgets WHERE category_id = :categoryId AND is_active = 1")
    suspend fun getBudgetByCategoryId(categoryId: String): BudgetEntity?

    @Query("SELECT * FROM budgets WHERE category_id IS NULL AND is_active = 1 LIMIT 1")
    suspend fun getTotalBudget(): BudgetEntity?

    @Query(
        """
        SELECT * FROM budgets 
        WHERE is_active = 1 
        AND start_date <= :date AND end_date >= :date
        """
    )
    fun getCurrentBudgets(date: Long = System.currentTimeMillis()): Flow<List<BudgetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: BudgetEntity)

    @Update
    suspend fun update(budget: BudgetEntity)

    @Delete
    suspend fun delete(budget: BudgetEntity)

    @Query("UPDATE budgets SET is_active = 0 WHERE id = :id")
    suspend fun deactivate(id: String)
}
