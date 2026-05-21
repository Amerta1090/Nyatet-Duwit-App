package com.nyatetduwit.presentation.remindersettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyatetduwit.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReminderSettingsUiState(
    val isEnabled: Boolean = true,
    val reminderHour: Int = 20,
    val reminderFrequency: Int = 1,
    val lastTransactionDate: Long = 0L,
)

@HiltViewModel
class ReminderSettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReminderSettingsUiState())
    val uiState: StateFlow<ReminderSettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            combine(
                settingsRepository.isReminderEnabled,
                settingsRepository.reminderHour,
                settingsRepository.reminderFrequency,
                settingsRepository.lastTransactionDate,
            ) { isEnabled, hour, frequency, lastDate ->
                ReminderSettingsUiState(
                    isEnabled = isEnabled,
                    reminderHour = hour,
                    reminderFrequency = frequency,
                    lastTransactionDate = lastDate,
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun toggleReminder(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setReminderEnabled(enabled)
        }
    }

    fun setReminderHour(hour: Int) {
        viewModelScope.launch {
            settingsRepository.setReminderHour(hour)
        }
    }

    fun getFrequencyLabel(): String {
        return when (_uiState.value.reminderFrequency) {
            1 -> "1x per hari (adaptif)"
            2 -> "2x per hari (adaptif)"
            else -> "1x per hari"
        }
    }

    fun getLastActivityLabel(): String {
        val lastDate = _uiState.value.lastTransactionDate
        if (lastDate == 0L) return "Belum ada aktivitas"

        val now = System.currentTimeMillis()
        val diffMillis = now - lastDate
        val diffDays = diffMillis / (1000 * 60 * 60 * 24)

        return when {
            diffDays == 0L -> "Hari ini"
            diffDays == 1L -> "Kemarin"
            diffDays < 7 -> "$diffDays hari yang lalu"
            else -> "Lebih dari seminggu yang lalu"
        }
    }
}
