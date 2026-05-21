package com.nyatetduwit.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.CategoryType
import com.nyatetduwit.domain.usecase.category.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val seedDefaultCategoriesUseCase: SeedDefaultCategoriesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    val categories: StateFlow<List<Category>> = getCategoriesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val expenseCategories: StateFlow<List<Category>> = getCategoriesUseCase()
        .map { it.filter { c -> c.type == CategoryType.EXPENSE } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val incomeCategories: StateFlow<List<Category>> = getCategoriesUseCase()
        .map { it.filter { c -> c.type == CategoryType.INCOME } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        seedDefaultCategories()
        loadCategory()
    }

    fun seedDefaultCategories() {
        viewModelScope.launch {
            seedDefaultCategoriesUseCase()
        }
    }

    fun loadCategory(categoryId: String? = null) {
        if (categoryId == null) {
            _uiState.update {
                it.copy(
                    formState = CategoryFormState(),
                    isLoading = false,
                )
            }
            return
        }

        viewModelScope.launch {
            val category = categories.value.find { it.id == categoryId }
            if (category != null) {
                _uiState.update {
                    it.copy(
                        formState = CategoryFormState(
                            id = category.id,
                            name = category.name,
                            type = category.type,
                            icon = category.icon,
                            color = category.color,
                        ),
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update {
            it.copy(formState = it.formState.copy(name = name))
        }
    }

    fun onTypeChange(type: CategoryType) {
        _uiState.update {
            it.copy(formState = it.formState.copy(type = type))
        }
    }

    fun onIconChange(icon: String) {
        _uiState.update {
            it.copy(formState = it.formState.copy(icon = icon))
        }
    }

    fun onColorChange(color: String) {
        _uiState.update {
            it.copy(formState = it.formState.copy(color = color))
        }
    }

    fun saveCategory() {
        val formState = _uiState.value.formState
        if (formState.name.isBlank()) {
            _uiState.update { it.copy(error = "Nama kategori tidak boleh kosong") }
            return
        }

        viewModelScope.launch {
            try {
                val category = Category(
                    id = if (formState.id.isEmpty()) UUID.randomUUID().toString() else formState.id,
                    name = formState.name.trim(),
                    type = formState.type,
                    icon = formState.icon,
                    color = formState.color,
                    parentId = null,
                    isDefault = false,
                    orderIndex = 0,
                    createdAt = if (formState.id.isEmpty()) System.currentTimeMillis() else 0,
                )

                if (formState.id.isEmpty()) {
                    addCategoryUseCase(category)
                } else {
                    updateCategoryUseCase(category)
                }

                _uiState.update {
                    it.copy(
                        formState = CategoryFormState(),
                        isSuccess = true,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = e.message, isLoading = false)
                }
            }
        }
    }

    fun deleteCategory(category: Category, onSuccess: () -> Unit) {
        if (category.isDefault) {
            _uiState.update { it.copy(error = "Kategori default tidak bisa dihapus") }
            return
        }

        viewModelScope.launch {
            try {
                deleteCategoryUseCase(category)
                _uiState.update { it.copy(isSuccess = true) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun clearSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }
}

data class CategoryUiState(
    val formState: CategoryFormState = CategoryFormState(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isSuccess: Boolean = false,
)

data class CategoryFormState(
    val id: String = "",
    val name: String = "",
    val type: CategoryType = CategoryType.EXPENSE,
    val icon: String = "category",
    val color: String = "#4F6B4E",
)
