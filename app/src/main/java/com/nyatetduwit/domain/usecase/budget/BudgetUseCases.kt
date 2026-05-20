package com.nyatetduwit.domain.usecase.budget

import com.nyatetduwit.domain.model.Budget
import com.nyatetduwit.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBudgetsUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    operator fun invoke(): Flow<List<Budget>> {
        return repository.getAllActiveBudgets()
    }
}

class GetBudgetByIdUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(id: String): Budget? {
        return repository.getBudgetById(id)
    }
}

class GetTotalBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(): Budget? {
        return repository.getTotalBudget()
    }
}

class AddBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget) {
        repository.addBudget(budget)
    }
}

class UpdateBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget) {
        repository.updateBudget(budget)
    }
}

class DeleteBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget) {
        repository.deleteBudget(budget)
    }
}

class DeactivateBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deactivateBudget(id)
    }
}
