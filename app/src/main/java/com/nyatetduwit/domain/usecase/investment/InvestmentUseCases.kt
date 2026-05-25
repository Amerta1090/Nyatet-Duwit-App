package com.nyatetduwit.domain.usecase.investment

import com.nyatetduwit.domain.model.Investment
import com.nyatetduwit.domain.repository.InvestmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveInvestmentsUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    operator fun invoke(): Flow<List<Investment>> = repository.getAllActiveInvestments()
}

class GetAllInvestmentsUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    operator fun invoke(): Flow<List<Investment>> = repository.getAllInvestments()
}

class GetInvestmentByIdUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    suspend operator fun invoke(id: String): Investment? = repository.getInvestmentById(id)
}

class AddInvestmentUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    suspend operator fun invoke(investment: Investment) = repository.addInvestment(investment)
}

class UpdateInvestmentUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    suspend operator fun invoke(investment: Investment) = repository.updateInvestment(investment)
}

class DeleteInvestmentUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    suspend operator fun invoke(investment: Investment) = repository.deleteInvestment(investment)
}

class DeactivateInvestmentUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    suspend operator fun invoke(id: String) = repository.deactivateInvestment(id)
}

class UpdateInvestmentValueUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    suspend operator fun invoke(id: String, value: Long) = repository.updateValue(id, value)
}

class GetTotalInvestmentValueUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    operator fun invoke(): Flow<Long> = repository.getTotalInvestmentValue()
}

class GetTotalCostBasisUseCase @Inject constructor(
    private val repository: InvestmentRepository
) {
    operator fun invoke(): Flow<Long> = repository.getTotalCostBasis()
}
