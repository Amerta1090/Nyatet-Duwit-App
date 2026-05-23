package com.nyatetduwit.domain.usecase.transaction

import com.nyatetduwit.domain.model.TransactionTag
import com.nyatetduwit.domain.repository.TransactionTagRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTagsByTransactionUseCase @Inject constructor(
    private val repository: TransactionTagRepository
) {
    operator fun invoke(transactionId: String): Flow<List<TransactionTag>> {
        return repository.getTagsByTransaction(transactionId)
    }
}

class GetAllTagNamesUseCase @Inject constructor(
    private val repository: TransactionTagRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return repository.getAllTagNames()
    }
}

class SaveTagsUseCase @Inject constructor(
    private val repository: TransactionTagRepository
) {
    suspend operator fun invoke(transactionId: String, tags: List<TransactionTag>) {
        repository.saveTags(transactionId, tags)
    }
}
