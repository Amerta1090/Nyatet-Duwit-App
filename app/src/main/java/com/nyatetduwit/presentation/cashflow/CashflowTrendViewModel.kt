package com.nyatetduwit.presentation.cashflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.usecase.transaction.AnomalyDetectionUseCase
import com.nyatetduwit.domain.usecase.transaction.AnomalyReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CashflowUiState(
    val report: AnomalyReport? = null,
    val isLoading: Boolean = true,
)

@HiltViewModel
class CashflowTrendViewModel @Inject constructor(
    private val anomalyDetectionUseCase: AnomalyDetectionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CashflowUiState())
    val uiState: StateFlow<CashflowUiState> = _uiState.asStateFlow()

    init {
        loadReport()
    }

    fun loadReport() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val report = anomalyDetectionUseCase()
                _uiState.update { it.copy(report = report, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
