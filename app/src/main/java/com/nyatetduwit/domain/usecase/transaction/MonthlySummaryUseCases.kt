package com.nyatetduwit.domain.usecase.transaction

import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

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

class GetActiveDaysCountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(startDate: Long, endDate: Long): Int {
        return repository.getActiveDaysCount(startDate, endDate)
    }
}

class GetTransactionCountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(startDate: Long, endDate: Long): Int {
        return repository.getTransactionCount(startDate, endDate)
    }
}

class GetMonthComparisonUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(
        currentStart: Long,
        currentEnd: Long,
        previousStart: Long,
        previousEnd: Long
    ): Flow<MonthComparison> {
        val currentIncome = repository.getSumByTypeAndDateRange(TransactionType.INCOME.value, currentStart, currentEnd)
        val currentExpense = repository.getSumByTypeAndDateRange(TransactionType.EXPENSE.value, currentStart, currentEnd)
        val previousIncome = repository.getSumByTypeAndDateRange(TransactionType.INCOME.value, previousStart, previousEnd)
        val previousExpense = repository.getSumByTypeAndDateRange(TransactionType.EXPENSE.value, previousStart, previousEnd)

        return kotlinx.coroutines.flow.combine(
            currentIncome, currentExpense, previousIncome, previousExpense
        ) { ci, ce, pi, pe ->
            val incomeChange = calculatePercentageChange(pi, ci)
            val expenseChange = calculatePercentageChange(pe, ce)
            MonthComparison(
                currentIncome = ci,
                currentExpense = ce,
                previousIncome = pi,
                previousExpense = pe,
                incomeChangePercent = incomeChange,
                expenseChangePercent = expenseChange,
            )
        }
    }

    private fun calculatePercentageChange(previous: Long, current: Long): Double {
        return if (previous == 0L) {
            if (current == 0L) 0.0 else 100.0
        } else {
            ((current - previous).toDouble() / previous.toDouble()) * 100.0
        }
    }
}

data class MonthComparison(
    val currentIncome: Long,
    val currentExpense: Long,
    val previousIncome: Long,
    val previousExpense: Long,
    val incomeChangePercent: Double,
    val expenseChangePercent: Double,
)
