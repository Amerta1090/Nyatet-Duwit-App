package com.nyatetduwit.presentation.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.core.worker.ReminderScheduler
import com.nyatetduwit.data.local.ExportManager
import com.nyatetduwit.data.local.PdfExportManager
import com.nyatetduwit.data.local.RestorePreview
import com.nyatetduwit.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    val isAutoBackupEnabled: Boolean = false,
    val accentColor: String = "teal",
    val isAmoledDark: Boolean = false,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository,
    val exportManager: ExportManager,
    val pdfExportManager: PdfExportManager,
    private val reminderScheduler: ReminderScheduler,
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
        viewModelScope.launch {
            settingsRepository.getAutoBackupEnabled().collect { enabled ->
                _uiState.update { it.copy(isAutoBackupEnabled = enabled) }
            }
        }
        viewModelScope.launch {
            settingsRepository.accentColor.collect { color ->
                _uiState.update { it.copy(accentColor = color) }
            }
        }
        viewModelScope.launch {
            settingsRepository.isAmoledDark.collect { amoled ->
                _uiState.update { it.copy(isAmoledDark = amoled) }
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

    fun toggleAutoBackup() {
        viewModelScope.launch {
            val newValue = !_uiState.value.isAutoBackupEnabled
            settingsRepository.setAutoBackupEnabled(newValue)
            if (newValue) {
                reminderScheduler.scheduleAutoBackup(context)
            } else {
                reminderScheduler.cancelAutoBackup(context)
            }
        }
    }

    fun setAccentColor(color: String) {
        viewModelScope.launch {
            settingsRepository.setAccentColor(color)
        }
    }

    fun toggleAmoledDark() {
        viewModelScope.launch {
            settingsRepository.setAmoledDark(!_uiState.value.isAmoledDark)
        }
    }

    fun clearStatus() {
        _uiState.update { it.copy(statusMessage = null, restorePreview = null) }
    }
}
