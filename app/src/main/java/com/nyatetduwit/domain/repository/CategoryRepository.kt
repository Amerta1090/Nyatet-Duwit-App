package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.CategoryType
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    fun getCategoriesByType(type: CategoryType): Flow<List<Category>>
    fun getCategoryById(id: String): Flow<Category?>
    suspend fun addCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    suspend fun seedDefaultCategories()
}
