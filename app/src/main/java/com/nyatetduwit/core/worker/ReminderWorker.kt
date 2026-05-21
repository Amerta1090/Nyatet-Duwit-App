package com.nyatetduwit.core.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nyatetduwit.domain.repository.SettingsRepository
import com.nyatetduwit.domain.repository.TransactionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val settingsRepository: SettingsRepository,
    private val transactionRepository: TransactionRepository,
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val WORK_NAME = "reminder_work"
        const val CHANNEL_ID = "reminder_channel"
        const val NOTIFICATION_ID = 1001
    }

    override suspend fun doWork(): Result {
        return try {
            val isEnabled = settingsRepository.isReminderEnabled.first()
            if (!isEnabled) {
                return Result.success()
            }

            val reminderHour = settingsRepository.reminderHour.first()
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

            if (currentHour != reminderHour && currentHour != reminderHour + 3) {
                return Result.success()
            }

            val frequency = calculateAdaptiveFrequency()
            settingsRepository.setReminderFrequency(frequency)

            val shouldRemind = when (frequency) {
                1 -> true
                2 -> currentHour == reminderHour || currentHour == reminderHour + 3
                else -> currentHour == reminderHour
            }

            if (shouldRemind) {
                showReminderNotification()
            }

            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    private suspend fun calculateAdaptiveFrequency(): Int {
        val lastTransactionTimestamp = settingsRepository.lastTransactionDate.first()
        val now = System.currentTimeMillis()
        val daysSinceLastTransaction = if (lastTransactionTimestamp > 0) {
            TimeUnit.MILLISECONDS.toDays(now - lastTransactionTimestamp).toInt()
        } else {
            999
        }

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -7)
        val weekAgo = calendar.timeInMillis
        val activeDays = transactionRepository.getActiveDaysCount(weekAgo, now)

        return when {
            activeDays >= 5 -> 1
            daysSinceLastTransaction >= 3 -> 2
            else -> 1
        }
    }

    private fun showReminderNotification() {
        createNotificationChannel()

        val messages = listOf(
            "Belum nyatet hari ini nih 👀",
            "Yuk catat pengeluaran hari ini!",
            "Jangan lupa nyatet duit kamu ya~",
            "Udah keluarin berapa hari ini? Catat yuk!",
            "Reminder: Nyatet duit biar nggak boncos 💸",
        )

        val message = messages[Random.nextInt(messages.size)]

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("NyatetDuwit")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID + Random.nextInt(100), notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Reminder NyatetDuwit",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                description = "Reminder untuk mencatat transaksi"
            }

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
