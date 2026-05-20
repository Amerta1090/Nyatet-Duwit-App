package com.nyatetduwit.core.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nyatetduwit.domain.model.RecurringFrequency
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.repository.RecurringTransactionRepository
import com.nyatetduwit.domain.repository.TransactionRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar
import java.util.UUID

@HiltWorker
class RecurringTransactionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val recurringTransactionRepository: RecurringTransactionRepository,
    private val transactionRepository: TransactionRepository,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val dueRecurring = recurringTransactionRepository.getDueRecurring(System.currentTimeMillis())

            for (recurring in dueRecurring) {
                if (recurring.endDate != null && recurring.nextDue > recurring.endDate) {
                    continue
                }

                val templateTransaction = transactionRepository.getTransactionById(recurring.templateTransactionId)
                templateTransaction.collect { template ->
                    if (template != null) {
                        val newTransaction = Transaction(
                            id = UUID.randomUUID().toString(),
                            type = template.type,
                            amount = template.amount,
                            accountId = template.accountId,
                            categoryId = template.categoryId,
                            toAccountId = template.toAccountId,
                            notes = template.notes ?: "Recurring: ${getFrequencyLabel(recurring.frequency)}",
                            dateTime = recurring.nextDue,
                            createdAt = System.currentTimeMillis(),
                            updatedAt = System.currentTimeMillis(),
                        )
                        transactionRepository.addTransaction(newTransaction)
                    }
                }

                val nextDue = calculateNextDue(recurring.frequency, recurring.nextDue)
                recurringTransactionRepository.updateRecurring(
                    recurring.copy(
                        nextDue = nextDue,
                        lastProcessed = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis(),
                    )
                )
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

    private fun calculateNextDue(frequency: RecurringFrequency, from: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = from
        when (frequency) {
            RecurringFrequency.DAILY -> calendar.add(Calendar.DAY_OF_MONTH, 1)
            RecurringFrequency.WEEKLY -> calendar.add(Calendar.WEEK_OF_YEAR, 1)
            RecurringFrequency.MONTHLY -> calendar.add(Calendar.MONTH, 1)
            RecurringFrequency.YEARLY -> calendar.add(Calendar.YEAR, 1)
        }
        return calendar.timeInMillis
    }

    private fun getFrequencyLabel(frequency: RecurringFrequency): String {
        return when (frequency) {
            RecurringFrequency.DAILY -> "Harian"
            RecurringFrequency.WEEKLY -> "Mingguan"
            RecurringFrequency.MONTHLY -> "Bulanan"
            RecurringFrequency.YEARLY -> "Tahunan"
        }
    }
}
