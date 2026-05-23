package com.nyatetduwit.domain.usecase.transaction

import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

data class MonthComparison(
    val currentTotal: Long,
    val previousTotal: Long,
) {
    val difference: Long get() = currentTotal - previousTotal
    val percentage: Float get() = if (previousTotal > 0) (difference.toFloat() / previousTotal) * 100f else 0f
    val incomeChangePercent: Double get() = percentage.toDouble()
    val expenseChangePercent: Double get() = percentage.toDouble()
}

class GetTransactionCountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(startDate: Long, endDate: Long): Int {
        return repository.getTransactionCount(startDate, endDate)
    }
}

class GetMonthlyExpenseUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(startDate: Long, endDate: Long): Long {
        return repository.getSumByTypeAndDateRange("expense", startDate, endDate).first()
    }
}

class GetMonthlyIncomeUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(startDate: Long, endDate: Long): Long {
        return repository.getSumByTypeAndDateRange("income", startDate, endDate).first()
    }
}

class GetDailyExpenseTrendUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(startDate: Long, endDate: Long): Flow<List<Pair<String, Long>>> {
        return repository.getDailyExpenseTrend(startDate, endDate)
    }
}

class GetBiggestExpenseUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(startDate: Long, endDate: Long): Transaction? {
        return repository.getBiggestExpense(startDate, endDate)
    }
}

class GetMonthComparisonUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(
        currentStart: Long, currentEnd: Long,
        previousStart: Long, previousEnd: Long
    ): Flow<MonthComparison> {
        val currentTotal = repository.getSumByTypeAndDateRange("expense", currentStart, currentEnd).first()
        val previousTotal = repository.getSumByTypeAndDateRange("expense", previousStart, previousEnd).first()
        return flowOf(MonthComparison(currentTotal, previousTotal))
    }
}

class GetActiveDaysCountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(startDate: Long, endDate: Long): Int {
        return repository.getActiveDaysCount(startDate, endDate)
    }
}
