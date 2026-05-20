package com.nyatetduwit.domain.usecase.recurring

import com.nyatetduwit.domain.model.RecurringTransaction
import com.nyatetduwit.domain.repository.RecurringTransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecurringTransactionsUseCase @Inject constructor(
    private val repository: RecurringTransactionRepository
) {
    operator fun invoke(): Flow<List<RecurringTransaction>> {
        return repository.getAllActiveRecurring()
    }
}

class GetAllRecurringTransactionsUseCase @Inject constructor(
    private val repository: RecurringTransactionRepository
) {
    operator fun invoke(): Flow<List<RecurringTransaction>> {
        return repository.getAllRecurring()
    }
}

class GetRecurringByIdUseCase @Inject constructor(
    private val repository: RecurringTransactionRepository
) {
    suspend operator fun invoke(id: String): RecurringTransaction? {
        return repository.getRecurringById(id)
    }
}

class AddRecurringTransactionUseCase @Inject constructor(
    private val repository: RecurringTransactionRepository
) {
    suspend operator fun invoke(recurring: RecurringTransaction) {
        repository.addRecurring(recurring)
    }
}

class UpdateRecurringTransactionUseCase @Inject constructor(
    private val repository: RecurringTransactionRepository
) {
    suspend operator fun invoke(recurring: RecurringTransaction) {
        repository.updateRecurring(recurring)
    }
}

class DeleteRecurringTransactionUseCase @Inject constructor(
    private val repository: RecurringTransactionRepository
) {
    suspend operator fun invoke(recurring: RecurringTransaction) {
        repository.deleteRecurring(recurring)
    }
}

class DeactivateRecurringTransactionUseCase @Inject constructor(
    private val repository: RecurringTransactionRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deactivateRecurring(id)
    }
}

class SkipRecurringInstanceUseCase @Inject constructor(
    private val repository: RecurringTransactionRepository
) {
    suspend operator fun invoke(id: String, skipDate: Long) {
        repository.skipInstance(id, skipDate)
    }
}
