package com.nyatetduwit.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nyatetduwit.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private object Keys {
        val BALANCE_VISIBLE = booleanPreferencesKey("balance_visible")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
        val LOCK_TIMEOUT = intPreferencesKey("lock_timeout")
        val REMINDER_ENABLED = booleanPreferencesKey("reminder_enabled")
        val REMINDER_HOUR = intPreferencesKey("reminder_hour")
        val REMINDER_FREQUENCY = intPreferencesKey("reminder_frequency")
        val LAST_TRANSACTION_DATE = longPreferencesKey("last_transaction_date")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val STREAK = intPreferencesKey("streak_count")
        val LAST_ACTIVE_DATE = longPreferencesKey("last_active_date")
        val AUTO_BACKUP_ENABLED = booleanPreferencesKey("auto_backup_enabled")
        val LAST_BACKUP_DATE = longPreferencesKey("last_backup_date")
        val ACCENT_COLOR = stringPreferencesKey("accent_color")
        val AMOLED_DARK = booleanPreferencesKey("amoled_dark")
    }

    override val isBalanceVisible: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[Keys.BALANCE_VISIBLE] ?: true }

    override val isDarkTheme: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[Keys.DARK_THEME] ?: false }

    override val isDarkThemeExplicit: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences.contains(Keys.DARK_THEME) }

    override val isBiometricEnabled: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[Keys.BIOMETRIC_ENABLED] ?: true }

    override val lockTimeoutMinutes: Flow<Int> = dataStore.data
        .map { preferences -> preferences[Keys.LOCK_TIMEOUT] ?: 5 }

    override val isReminderEnabled: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[Keys.REMINDER_ENABLED] ?: true }

    override val reminderHour: Flow<Int> = dataStore.data
        .map { preferences -> preferences[Keys.REMINDER_HOUR] ?: 20 }

    override val reminderFrequency: Flow<Int> = dataStore.data
        .map { preferences -> preferences[Keys.REMINDER_FREQUENCY] ?: 1 }

    override val lastTransactionDate: Flow<Long> = dataStore.data
        .map { preferences -> preferences[Keys.LAST_TRANSACTION_DATE] ?: 0L }

    override val isOnboardingCompleted: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[Keys.ONBOARDING_COMPLETED] ?: false }

    override suspend fun setBalanceVisible(isVisible: Boolean) {
        dataStore.edit { preferences -> preferences[Keys.BALANCE_VISIBLE] = isVisible }
    }

    override suspend fun setDarkTheme(isDark: Boolean) {
        dataStore.edit { preferences -> preferences[Keys.DARK_THEME] = isDark }
    }

    override suspend fun setBiometricEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences -> preferences[Keys.BIOMETRIC_ENABLED] = isEnabled }
    }

    override suspend fun setLockTimeoutMinutes(minutes: Int) {
        dataStore.edit { preferences -> preferences[Keys.LOCK_TIMEOUT] = minutes }
    }

    override suspend fun setReminderEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences -> preferences[Keys.REMINDER_ENABLED] = isEnabled }
    }

    override suspend fun setReminderHour(hour: Int) {
        dataStore.edit { preferences -> preferences[Keys.REMINDER_HOUR] = hour }
    }

    override suspend fun setReminderFrequency(frequency: Int) {
        dataStore.edit { preferences -> preferences[Keys.REMINDER_FREQUENCY] = frequency }
    }

    override suspend fun setLastTransactionDate(timestamp: Long) {
        dataStore.edit { preferences -> preferences[Keys.LAST_TRANSACTION_DATE] = timestamp }
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences -> preferences[Keys.ONBOARDING_COMPLETED] = completed }
    }

    override fun getStreak(): Flow<Int> = dataStore.data
        .map { preferences -> preferences[Keys.STREAK] ?: 0 }

    override suspend fun setStreak(streak: Int) {
        dataStore.edit { preferences -> preferences[Keys.STREAK] = streak }
    }

    override fun getLastActiveDate(): Flow<Long> = dataStore.data
        .map { preferences -> preferences[Keys.LAST_ACTIVE_DATE] ?: 0L }

    override suspend fun setLastActiveDate(date: Long) {
        dataStore.edit { preferences -> preferences[Keys.LAST_ACTIVE_DATE] = date }
    }

    override fun getAutoBackupEnabled(): Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[Keys.AUTO_BACKUP_ENABLED] ?: false }

    override suspend fun setAutoBackupEnabled(enabled: Boolean) {
        dataStore.edit { preferences -> preferences[Keys.AUTO_BACKUP_ENABLED] = enabled }
    }

    override fun getLastBackupDate(): Flow<Long> = dataStore.data
        .map { preferences -> preferences[Keys.LAST_BACKUP_DATE] ?: 0L }

    override suspend fun setLastBackupDate(date: Long) {
        dataStore.edit { preferences -> preferences[Keys.LAST_BACKUP_DATE] = date }
    }

    override val accentColor: Flow<String> = dataStore.data
        .map { preferences -> preferences[Keys.ACCENT_COLOR] ?: "teal" }

    override suspend fun setAccentColor(color: String) {
        dataStore.edit { preferences -> preferences[Keys.ACCENT_COLOR] = color }
    }

    override val isAmoledDark: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[Keys.AMOLED_DARK] ?: false }

    override suspend fun setAmoledDark(enabled: Boolean) {
        dataStore.edit { preferences -> preferences[Keys.AMOLED_DARK] = enabled }
    }
}
