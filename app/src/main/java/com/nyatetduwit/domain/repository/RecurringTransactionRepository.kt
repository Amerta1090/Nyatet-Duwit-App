package com.nyatetduwit.domain.repository

import com.nyatetduwit.domain.model.RecurringTransaction
import kotlinx.coroutines.flow.Flow

interface RecurringTransactionRepository {
    fun getAllActiveRecurring(): Flow<List<RecurringTransaction>>
    fun getAllRecurring(): Flow<List<RecurringTransaction>>
    suspend fun getRecurringById(id: String): RecurringTransaction?
    suspend fun getDueRecurring(now: Long): List<RecurringTransaction>
    suspend fun addRecurring(recurring: RecurringTransaction)
    suspend fun updateRecurring(recurring: RecurringTransaction)
    suspend fun deleteRecurring(recurring: RecurringTransaction)
    suspend fun deactivateRecurring(id: String)
    suspend fun skipInstance(id: String, skipDate: Long)
}
