package com.nyatetduwit.domain.usecase.transaction

import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(query: String): Flow<List<Transaction>> {
        return repository.searchTransactions(query)
    }
}

class FilterTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(
        type: String?,
        categoryId: String?,
        accountId: String?,
        startDate: Long?,
        endDate: Long?
    ): Flow<List<Transaction>> {
        return repository.filterTransactions(type, categoryId, accountId, startDate, endDate)
    }
}

class SearchAndFilterTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(
        query: String?,
        type: String?,
        categoryId: String?,
        accountId: String?,
        startDate: Long?,
        endDate: Long?
    ): Flow<List<Transaction>> {
        return repository.searchAndFilterTransactions(query, type, categoryId, accountId, startDate, endDate)
    }
}
