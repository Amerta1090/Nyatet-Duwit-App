package com.nyatetduwit.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val isBalanceVisible: Flow<Boolean>
    val isDarkTheme: Flow<Boolean>
    val isDarkThemeExplicit: Flow<Boolean>
    val isBiometricEnabled: Flow<Boolean>
    val lockTimeoutMinutes: Flow<Int>
    val isReminderEnabled: Flow<Boolean>
    val reminderHour: Flow<Int>
    val reminderFrequency: Flow<Int>
    val lastTransactionDate: Flow<Long>
    val isOnboardingCompleted: Flow<Boolean>

    suspend fun setBalanceVisible(isVisible: Boolean)
    suspend fun setDarkTheme(isDark: Boolean)
    suspend fun setBiometricEnabled(isEnabled: Boolean)
    suspend fun setLockTimeoutMinutes(minutes: Int)
    suspend fun setReminderEnabled(isEnabled: Boolean)
    suspend fun setReminderHour(hour: Int)
    suspend fun setReminderFrequency(frequency: Int)
    suspend fun setLastTransactionDate(timestamp: Long)
    suspend fun setOnboardingCompleted(completed: Boolean)
    fun getStreak(): Flow<Int>
    suspend fun setStreak(streak: Int)
    fun getLastActiveDate(): Flow<Long>
    suspend fun setLastActiveDate(date: Long)
    fun getAutoBackupEnabled(): Flow<Boolean>
    suspend fun setAutoBackupEnabled(enabled: Boolean)
    fun getLastBackupDate(): Flow<Long>
    suspend fun setLastBackupDate(date: Long)
    val accentColor: Flow<String>
    suspend fun setAccentColor(color: String)
    val isAmoledDark: Flow<Boolean>
    suspend fun setAmoledDark(enabled: Boolean)
    val baseCurrency: Flow<String>
    suspend fun setBaseCurrency(currency: String)
    val syncEnabled: Flow<Boolean>
    suspend fun setSyncEnabled(enabled: Boolean)
    val lastSyncTimestamp: Flow<Long>
    suspend fun setLastSyncTimestamp(timestamp: Long)
}
