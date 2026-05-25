package com.nyatetduwit.domain.usecase.splitbill

import com.nyatetduwit.domain.model.SplitBill
import com.nyatetduwit.domain.repository.SplitBillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBillsUseCase @Inject constructor(
    private val repository: SplitBillRepository
) {
    operator fun invoke(): Flow<List<SplitBill>> = repository.getAllBills()
}

class GetBillByIdUseCase @Inject constructor(
    private val repository: SplitBillRepository
) {
    suspend operator fun invoke(id: String): SplitBill? = repository.getBillById(id)
}

class AddBillUseCase @Inject constructor(
    private val repository: SplitBillRepository
) {
    suspend operator fun invoke(bill: SplitBill) = repository.addBill(bill)
}

class UpdateBillUseCase @Inject constructor(
    private val repository: SplitBillRepository
) {
    suspend operator fun invoke(bill: SplitBill) = repository.updateBill(bill)
}

class DeleteBillUseCase @Inject constructor(
    private val repository: SplitBillRepository
) {
    suspend operator fun invoke(bill: SplitBill) = repository.deleteBill(bill)
}

class MarkSettledUseCase @Inject constructor(
    private val repository: SplitBillRepository
) {
    suspend operator fun invoke(id: String) = repository.markSettled(id)
}

class MarkPersonPaidUseCase @Inject constructor(
    private val repository: SplitBillRepository
) {
    suspend operator fun invoke(personId: String) = repository.markPersonPaid(personId)
}
