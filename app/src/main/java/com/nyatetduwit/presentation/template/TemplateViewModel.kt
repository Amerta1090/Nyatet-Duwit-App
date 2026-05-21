package com.nyatetduwit.presentation.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.model.Template
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.usecase.template.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TemplateUiState(
    val templates: List<Template> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val getTemplatesUseCase: GetTemplatesUseCase,
    private val deleteTemplateUseCase: DeleteTemplateUseCase,
    private val togglePinUseCase: ToggleTemplatePinUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TemplateUiState())
    val uiState: StateFlow<TemplateUiState> = _uiState.asStateFlow()

    init {
        loadTemplates()
    }

    private fun loadTemplates() {
        viewModelScope.launch {
            getTemplatesUseCase()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = e.message ?: "Terjadi kesalahan"
                        )
                    }
                }
                .collect { templates ->
                    _uiState.update {
                        it.copy(
                            templates = templates,
                            isLoading = false,
                        )
                    }
                }
        }
    }

    fun deleteTemplate(template: Template) {
        viewModelScope.launch {
            deleteTemplateUseCase(template)
        }
    }

    fun togglePin(template: Template) {
        viewModelScope.launch {
            togglePinUseCase(template.id, !template.isPinned)
        }
    }

    fun getTransactionTypeLabel(type: TransactionType): String {
        return when (type) {
            TransactionType.INCOME -> "Pemasukan"
            TransactionType.EXPENSE -> "Pengeluaran"
            TransactionType.TRANSFER -> "Transfer"
        }
    }
}
