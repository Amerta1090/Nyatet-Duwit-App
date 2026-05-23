package com.nyatetduwit.domain.usecase.transaction

import com.nyatetduwit.domain.model.TransactionSplit
import com.nyatetduwit.domain.repository.TransactionSplitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSplitsByTransactionUseCase @Inject constructor(
    private val repository: TransactionSplitRepository
) {
    operator fun invoke(transactionId: String): Flow<List<TransactionSplit>> {
        return repository.getSplitsByTransaction(transactionId)
    }
}

class SaveSplitsUseCase @Inject constructor(
    private val repository: TransactionSplitRepository
) {
    suspend operator fun invoke(transactionId: String, splits: List<TransactionSplit>) {
        repository.saveSplits(transactionId, splits)
    }
}
