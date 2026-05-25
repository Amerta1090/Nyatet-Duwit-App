package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.InvestmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvestmentDao {

    @Query("SELECT * FROM investments WHERE is_active = 1 ORDER BY created_at DESC")
    fun getAllActiveInvestments(): Flow<List<InvestmentEntity>>

    @Query("SELECT * FROM investments ORDER BY created_at DESC")
    fun getAllInvestments(): Flow<List<InvestmentEntity>>

    @Query("SELECT * FROM investments WHERE id = :id")
    suspend fun getInvestmentById(id: String): InvestmentEntity?

    @Query("SELECT COALESCE(SUM(current_value), 0) FROM investments WHERE is_active = 1")
    fun getTotalInvestmentValue(): Flow<Long>

    @Query("SELECT COALESCE(SUM(cost_basis), 0) FROM investments WHERE is_active = 1")
    fun getTotalCostBasis(): Flow<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(investment: InvestmentEntity)

    @Update
    suspend fun update(investment: InvestmentEntity)

    @Delete
    suspend fun delete(investment: InvestmentEntity)

    @Query("UPDATE investments SET is_active = 0, updated_at = :updatedAt WHERE id = :id")
    suspend fun deactivate(id: String, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE investments SET current_value = :value, updated_at = :updatedAt WHERE id = :id")
    suspend fun updateValue(id: String, value: Long, updatedAt: Long = System.currentTimeMillis())

    @Query("SELECT * FROM investments ORDER BY created_at DESC")
    suspend fun getAllInvestmentsSync(): List<InvestmentEntity>

    @Query("DELETE FROM investments")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(investments: List<InvestmentEntity>)
}
