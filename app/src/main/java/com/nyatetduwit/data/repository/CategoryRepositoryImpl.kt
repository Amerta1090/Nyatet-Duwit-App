package com.nyatetduwit.data.repository

import com.nyatetduwit.data.local.CategorySeedData
import com.nyatetduwit.data.local.dao.CategoryDao
import com.nyatetduwit.data.local.entity.CategoryEntity
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.CategoryType
import com.nyatetduwit.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getCategoriesByType(type: CategoryType): Flow<List<Category>> {
        return categoryDao.getCategoriesByType(type.value).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getCategoryById(id: String): Flow<Category?> {
        return categoryDao.getAllCategories().map { entities ->
            entities.find { it.id == id }?.toDomain()
        }
    }

    override suspend fun addCategory(category: Category) {
        categoryDao.insert(category.toEntity())
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.update(category.toEntity())
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.delete(category.toEntity())
    }

    override suspend fun seedDefaultCategories() {
        val count = categoryDao.getCategoryCount()
        if (count == 0) {
            categoryDao.insertAll(CategorySeedData.getDefaultCategories())
        }
    }

    private fun CategoryEntity.toDomain(): Category {
        return Category(
            id = id,
            name = name,
            type = CategoryType.fromValue(type),
            icon = icon,
            color = color,
            parentId = parentId,
            isDefault = isDefault,
            orderIndex = orderIndex,
            createdAt = createdAt,
        )
    }

    private fun Category.toEntity(): CategoryEntity {
        return CategoryEntity(
            id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
            name = name,
            type = type.value,
            icon = icon,
            color = color,
            parentId = parentId,
            isDefault = isDefault,
            orderIndex = orderIndex,
            createdAt = if (id.isEmpty()) System.currentTimeMillis() else createdAt,
        )
    }
}
