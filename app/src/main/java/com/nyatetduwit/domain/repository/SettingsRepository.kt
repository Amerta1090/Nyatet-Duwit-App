package com.nyatetduwit.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val isBalanceVisible: Flow<Boolean>
    val isDarkTheme: Flow<Boolean>
    val isBiometricEnabled: Flow<Boolean>
    val lockTimeoutMinutes: Flow<Int>
    val isReminderEnabled: Flow<Boolean>
    val reminderHour: Flow<Int>

    suspend fun setBalanceVisible(isVisible: Boolean)
    suspend fun setDarkTheme(isDark: Boolean)
    suspend fun setBiometricEnabled(isEnabled: Boolean)
    suspend fun setLockTimeoutMinutes(minutes: Int)
    suspend fun setReminderEnabled(isEnabled: Boolean)
    suspend fun setReminderHour(hour: Int)
}
