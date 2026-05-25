package com.nyatetduwit.domain.usecase.transaction

import com.nyatetduwit.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.first
import java.util.Calendar
import javax.inject.Inject

data class AnomalyReport(
    val anomalies: List<AnomalyItem> = emptyList(),
    val weeklyTrend: List<DailyExpenseItem> = emptyList(),
    val monthlyComparison: MonthlyComparisonData? = null,
)

data class AnomalyItem(
    val type: AnomalyType,
    val title: String,
    val description: String,
    val severity: AnomalySeverity,
)

enum class AnomalyType {
    SPENDING_SPIKE,
    CATEGORY_SPIKE,
    FREQUENCY_DROP,
    UNUSUAL_TRANSACTION,
}

enum class AnomalySeverity { LOW, MEDIUM, HIGH }

data class DailyExpenseItem(
    val day: String,
    val dayNumber: Int,
    val amount: Long,
)

data class MonthlyComparisonData(
    val currentMonthTotal: Long,
    val previousMonthTotal: Long,
    val percentageChange: Float,
)

class AnomalyDetectionUseCase @Inject constructor(
    private val repository: TransactionRepository,
    private val getDailyExpenseTrendUseCase: GetDailyExpenseTrendUseCase,
    private val getTopCategoriesByExpenseUseCase: GetTopCategoriesByExpenseUseCase,
) {
    suspend operator fun invoke(): AnomalyReport {
        val anomalies = mutableListOf<AnomalyItem>()
        val now = Calendar.getInstance()

        val currentMonthStart = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val currentMonthEnd = Calendar.getInstance().apply {
            timeInMillis = currentMonthStart
            add(Calendar.MONTH, 1)
            add(Calendar.MILLISECOND, -1)
        }.timeInMillis

        val previousMonthStart = Calendar.getInstance().apply {
            timeInMillis = currentMonthStart
            add(Calendar.MONTH, -1)
        }.timeInMillis

        val previousMonthEnd = currentMonthStart - 1

        val currentWeekStart = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val lastWeekStart = currentWeekStart - 7 * 24 * 60 * 60 * 1000L
        val lastWeekEnd = currentWeekStart - 1

        val thisMonthExpense = repository.getSumByTypeAndDateRange("expense", currentMonthStart, currentMonthEnd).first()
        val lastMonthExpense = repository.getSumByTypeAndDateRange("expense", previousMonthStart, previousMonthEnd).first()

        val thisWeekExpense = repository.getSumByTypeAndDateRange("expense", currentWeekStart, currentMonthEnd).first()
        val lastWeekExpense = repository.getSumByTypeAndDateRange("expense", lastWeekStart, lastWeekEnd).first()

        if (lastWeekExpense > 0 && thisWeekExpense > lastWeekExpense * 1.4) {
            val spike = ((thisWeekExpense - lastWeekExpense) * 100 / lastWeekExpense).toInt()
            anomalies.add(
                AnomalyItem(
                    type = AnomalyType.SPENDING_SPIKE,
                    title = "Pengeluaran minggu ini naik $spike%",
                    description = "Minggu ini kamu spend lebih banyak dibanding minggu lalu. Cek yuk ada pengeluaran tak terduga apa.",
                    severity = if (spike > 80) AnomalySeverity.HIGH else AnomalySeverity.MEDIUM,
                )
            )
        }

        if (lastMonthExpense > 0) {
            val change = ((thisMonthExpense - lastMonthExpense) * 100 / lastMonthExpense).toInt()
            if (change > 30) {
                anomalies.add(
                    AnomalyItem(
                        type = AnomalyType.CATEGORY_SPIKE,
                        title = "Pengeluaran bulan ini naik $change%",
                        description = "Dibanding bulan lalu, pengeluaran kamu naik signifikan. Cek detailnya di ringkasan bulanan.",
                        severity = if (change > 60) AnomalySeverity.HIGH else AnomalySeverity.MEDIUM,
                    )
                )
            }
        }

        val dailyTrend = getDailyExpenseTrendUseCase(currentWeekStart, currentMonthEnd).first()
        val dailyItems = dailyTrend.map { (day, amount) ->
            val dayNumber = day.split("-").lastOrNull()?.toIntOrNull() ?: 0
            DailyExpenseItem(day = day, dayNumber = dayNumber, amount = amount)
        }

        val maxDaily = dailyItems.maxOfOrNull { it.amount } ?: 0L
        val avgDaily = if (dailyItems.isNotEmpty()) dailyItems.sumOf { it.amount } / dailyItems.size else 0L
        if (maxDaily > 0 && avgDaily > 0 && maxDaily > avgDaily * 3) {
            anomalies.add(
                AnomalyItem(
                    type = AnomalyType.UNUSUAL_TRANSACTION,
                    title = "Ada pengeluaran besar minggu ini",
                    description = "Ada transaksi ${if (avgDaily > 0) (maxDaily / avgDaily).toInt() else 3}x dari rata-rata harian kamu.",
                    severity = AnomalySeverity.MEDIUM,
                )
            )
        }

        val monthComparison = MonthlyComparisonData(
            currentMonthTotal = thisMonthExpense,
            previousMonthTotal = lastMonthExpense,
            percentageChange = if (lastMonthExpense > 0)
                ((thisMonthExpense - lastMonthExpense).toFloat() / lastMonthExpense) * 100f else 0f,
        )

        return AnomalyReport(
            anomalies = anomalies,
            weeklyTrend = dailyItems,
            monthlyComparison = monthComparison,
        )
    }
}
