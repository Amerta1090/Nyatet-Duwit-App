package com.nyatetduwit.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.data.local.ExportManager
import com.nyatetduwit.data.local.RestorePreview
import com.nyatetduwit.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val isDarkTheme: Boolean = false,
    val isBalanceVisible: Boolean = true,
    val isLoading: Boolean = false,
    val statusMessage: String? = null,
    val restorePreview: RestorePreview? = null,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    val exportManager: ExportManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.isDarkTheme.collect { isDark ->
                _uiState.update { it.copy(isDarkTheme = isDark) }
            }
        }
        viewModelScope.launch {
            settingsRepository.isBalanceVisible.collect { isVisible ->
                _uiState.update { it.copy(isBalanceVisible = isVisible) }
            }
        }
    }

    fun toggleDarkTheme() {
        viewModelScope.launch {
            val newValue = !_uiState.value.isDarkTheme
            settingsRepository.setDarkTheme(newValue)
        }
    }

    fun toggleBalanceVisibility() {
        viewModelScope.launch {
            val newValue = !_uiState.value.isBalanceVisible
            settingsRepository.setBalanceVisible(newValue)
        }
    }

    fun clearStatus() {
        _uiState.update { it.copy(statusMessage = null, restorePreview = null) }
    }
}
