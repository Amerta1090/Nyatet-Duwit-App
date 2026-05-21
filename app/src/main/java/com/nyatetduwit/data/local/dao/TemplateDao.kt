package com.nyatetduwit.data.local.dao

import androidx.room.*
import com.nyatetduwit.data.local.entity.TemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDao {

    @Query(
        """
        SELECT * FROM templates 
        ORDER BY is_pinned DESC, usage_count DESC, created_at DESC
        """
    )
    fun getAllTemplates(): Flow<List<TemplateEntity>>

    @Query(
        """
        SELECT * FROM templates 
        WHERE is_pinned = 1 
        ORDER BY usage_count DESC, created_at DESC
        """
    )
    fun getPinnedTemplates(): Flow<List<TemplateEntity>>

    @Query(
        """
        SELECT * FROM templates 
        WHERE id = :id
        """
    )
    suspend fun getTemplateById(id: String): TemplateEntity?

    @Query(
        """
        SELECT * FROM templates 
        ORDER BY usage_count DESC 
        LIMIT :limit
        """
    )
    fun getRecentTemplates(limit: Int = 5): Flow<List<TemplateEntity>>

    @Query(
        """
        SELECT * FROM templates 
        WHERE type = :type 
        ORDER BY is_pinned DESC, usage_count DESC, created_at DESC
        """
    )
    fun getTemplatesByType(type: String): Flow<List<TemplateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(template: TemplateEntity)

    @Update
    suspend fun update(template: TemplateEntity)

    @Delete
    suspend fun delete(template: TemplateEntity)

    @Query(
        """
        UPDATE templates 
        SET usage_count = usage_count + 1, last_used = :now 
        WHERE id = :id
        """
    )
    suspend fun incrementUsageCount(id: String, now: Long = System.currentTimeMillis())

    @Query(
        """
        UPDATE templates 
        SET is_pinned = :isPinned 
        WHERE id = :id
        """
    )
    suspend fun togglePin(id: String, isPinned: Boolean)
}
