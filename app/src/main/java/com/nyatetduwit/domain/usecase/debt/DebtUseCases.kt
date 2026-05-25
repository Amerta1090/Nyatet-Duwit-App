package com.nyatetduwit.domain.usecase.debt

import com.nyatetduwit.domain.model.Debt
import com.nyatetduwit.domain.model.DebtPayment
import com.nyatetduwit.domain.repository.DebtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveDebtsUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    operator fun invoke(): Flow<List<Debt>> = repository.getAllActiveDebts()
}

class GetAllDebtsUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    operator fun invoke(): Flow<List<Debt>> = repository.getAllDebts()
}

class GetDebtByIdUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(id: String): Debt? = repository.getDebtById(id)
}

class AddDebtUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(debt: Debt) = repository.addDebt(debt)
}

class UpdateDebtUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(debt: Debt) = repository.updateDebt(debt)
}

class DeleteDebtUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(debt: Debt) = repository.deleteDebt(debt)
}

class DeactivateDebtUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(id: String) = repository.deactivateDebt(id)
}

class GetDebtPaymentsUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    operator fun invoke(debtId: String): Flow<List<DebtPayment>> = repository.getPaymentsByDebt(debtId)
}

class AddDebtPaymentUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(payment: DebtPayment) = repository.addPayment(payment)
}

class DeleteDebtPaymentUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(payment: DebtPayment) = repository.deletePayment(payment)
}

class GetTotalPaidByDebtUseCase @Inject constructor(
    private val repository: DebtRepository
) {
    suspend operator fun invoke(debtId: String): Long = repository.getTotalPaidByDebt(debtId)
}
