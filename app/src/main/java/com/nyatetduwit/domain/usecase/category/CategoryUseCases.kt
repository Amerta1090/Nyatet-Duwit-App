package com.nyatetduwit.domain.usecase.category

import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.CategoryType
import com.nyatetduwit.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        return repository.getAllCategories()
    }
}

class GetCategoriesByTypeUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(type: CategoryType): Flow<List<Category>> {
        return repository.getCategoriesByType(type)
    }
}

class AddCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        repository.addCategory(category)
    }
}

class UpdateCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        repository.updateCategory(category)
    }
}

class DeleteCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        repository.deleteCategory(category)
    }
}

class SeedDefaultCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke() {
        repository.seedDefaultCategories()
    }
}
