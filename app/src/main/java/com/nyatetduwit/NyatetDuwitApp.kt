package com.nyatetduwit

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nyatetduwit.core.security.AppLockManager
import com.nyatetduwit.core.security.SecurityManager
import com.nyatetduwit.core.worker.RecurringTransactionWorker
import com.nyatetduwit.core.worker.ReminderWorker
import com.nyatetduwit.domain.repository.SettingsRepository
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class NyatetDuwitApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    @Inject
    lateinit var settingsRepository: SettingsRepository

    lateinit var securityManager: SecurityManager
        private set

    lateinit var appLockManager: AppLockManager
        private set

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        securityManager = SecurityManager(this)
        appLockManager = AppLockManager(this)
        setupRecurringWorker()
        setupReminderWorker()
    }

    private fun setupRecurringWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val recurringWork = PeriodicWorkRequestBuilder<RecurringTransactionWorker>(
            repeatInterval = 6,
            TimeUnit.HOURS,
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "recurring_transaction_check",
            ExistingPeriodicWorkPolicy.KEEP,
            recurringWork,
        )
    }

    private fun setupReminderWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val reminderWork = PeriodicWorkRequestBuilder<ReminderWorker>(
            repeatInterval = 3,
            TimeUnit.HOURS,
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            ReminderWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            reminderWork,
        )
    }
}
