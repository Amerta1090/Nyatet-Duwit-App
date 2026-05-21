package com.nyatetduwit.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.nyatetduwit.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

import androidx.datastore.preferences.core.longPreferencesKey

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
    }

    override val isBalanceVisible: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[Keys.BALANCE_VISIBLE] ?: true }

    override val isDarkTheme: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[Keys.DARK_THEME] ?: false }

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
}
