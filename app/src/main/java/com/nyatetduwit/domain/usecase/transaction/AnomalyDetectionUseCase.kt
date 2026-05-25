package com.nyatetduwit.domain.usecase.transaction

import com.nyatetduwit.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.first
import java.util.Calendar
import javax.inject.Inject

data class AnomalyReport(
    val anomalies: List<AnomalyItem> = emptyList(),
    val weeklyTrend: List<DailyExpenseItem> = emptyList(),
    val monthlyComparison: MonthlyComparisonData? = null,
    val insights: List<InsightItem> = emptyList(),
)

data class InsightItem(
    val type: InsightType,
    val title: String,
    val description: String,
    val severity: AnomalySeverity = AnomalySeverity.LOW,
)

enum class InsightType {
    SAVING_OPPORTUNITY,
    INCOME_CHANGE,
    SPENDING_PATTERN,
    CONSISTENCY,
}

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
    private var sensitivityFactor: Float = 1.0f

    fun setSensitivity(factor: Float) {
        sensitivityFactor = factor.coerceIn(0.5f, 2.0f)
    }

    suspend operator fun invoke(): AnomalyReport {
        val anomalies = mutableListOf<AnomalyItem>()
        val insights = mutableListOf<InsightItem>()
        val now = Calendar.getInstance()

        val currentMonthStart = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1); set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val currentMonthEnd = Calendar.getInstance().apply {
            timeInMillis = currentMonthStart
            add(Calendar.MONTH, 1); add(Calendar.MILLISECOND, -1)
        }.timeInMillis

        val previousMonthStart = Calendar.getInstance().apply {
            timeInMillis = currentMonthStart; add(Calendar.MONTH, -1)
        }.timeInMillis
        val previousMonthEnd = currentMonthStart - 1

        val currentWeekStart = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val lastWeekStart = currentWeekStart - 7 * 24 * 60 * 60 * 1000L
        val lastWeekEnd = currentWeekStart - 1

        val thisMonthExpense = repository.getSumByTypeAndDateRange("expense", currentMonthStart, currentMonthEnd).first()
        val lastMonthExpense = repository.getSumByTypeAndDateRange("expense", previousMonthStart, previousMonthEnd).first()
        val thisMonthIncome = repository.getSumByTypeAndDateRange("income", currentMonthStart, currentMonthEnd).first()
        val lastMonthIncome = repository.getSumByTypeAndDateRange("income", previousMonthStart, previousMonthEnd).first()
        val thisWeekExpense = repository.getSumByTypeAndDateRange("expense", currentWeekStart, currentMonthEnd).first()
        val lastWeekExpense = repository.getSumByTypeAndDateRange("expense", lastWeekStart, lastWeekEnd).first()

        val spikeThreshold = (1.3f + (1.0f - sensitivityFactor) * 0.3f)
        val categorySpikeThreshold = (1.25f + (1.0f - sensitivityFactor) * 0.25f)

        if (lastWeekExpense > 0 && thisWeekExpense > lastWeekExpense * spikeThreshold) {
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
            if (change > 30 * sensitivityFactor) {
                anomalies.add(
                    AnomalyItem(
                        type = AnomalyType.CATEGORY_SPIKE,
                        title = "Pengeluaran bulan ini naik $change%",
                        description = "Dibanding bulan lalu, pengeluaran kamu naik signifikan.",
                        severity = if (change > 60) AnomalySeverity.HIGH else AnomalySeverity.MEDIUM,
                    )
                )
            }
        }

        val dailyTrend = getDailyExpenseTrendUseCase(currentWeekStart, currentMonthEnd).first()
        val dailyItems = dailyTrend.map { (day, amount) ->
            DailyExpenseItem(day = day, dayNumber = day.split("-").lastOrNull()?.toIntOrNull() ?: 0, amount = amount)
        }

        val maxDaily = dailyItems.maxOfOrNull { it.amount } ?: 0L
        val avgDaily = if (dailyItems.isNotEmpty()) dailyItems.sumOf { it.amount } / dailyItems.size else 0L
        if (maxDaily > 0 && avgDaily > 0 && maxDaily > avgDaily * 3 * sensitivityFactor) {
            anomalies.add(
                AnomalyItem(
                    type = AnomalyType.UNUSUAL_TRANSACTION,
                    title = "Ada pengeluaran besar minggu ini",
                    description = "Ada transaksi ${(maxDaily / avgDaily).toInt()}x dari rata-rata harian kamu.",
                    severity = AnomalySeverity.MEDIUM,
                )
            )
        }

        if (thisMonthIncome > 0 && lastMonthIncome > 0) {
            val incomeChange = ((thisMonthIncome - lastMonthIncome) * 100 / lastMonthIncome).toInt()
            if (incomeChange > 20) {
                insights.add(
                    InsightItem(
                        type = InsightType.INCOME_CHANGE,
                        title = "Pemasukan naik $incomeChange%",
                        description = "Bulan ini pemasukan kamu lebih besar dari bulan lalu. Mantap!",
                    )
                )
            } else if (incomeChange < -20) {
                insights.add(
                    InsightItem(
                        type = InsightType.INCOME_CHANGE,
                        title = "Pemasukan turun ${-incomeChange}%",
                        description = "Bulan ini pemasukan berkurang. Cek apakah ada yang perlu diatur.",
                    )
                )
            }
        }

        if (thisMonthExpense > 0 && thisMonthIncome > 0) {
            val savingsRate = ((thisMonthIncome - thisMonthExpense) * 100 / thisMonthIncome).toInt()
            if (savingsRate < 10) {
                insights.add(
                    InsightItem(
                        type = InsightType.SAVING_OPPORTUNITY,
                        title = "Kesempatan menabung",
                        description = "Hanya $savingsRate% dari pemasukan yang tersisa. Coba cek pengeluaran yang bisa dikurangi.",
                        severity = AnomalySeverity.MEDIUM,
                    )
                )
            } else if (savingsRate > 30) {
                insights.add(
                    InsightItem(
                        type = InsightType.CONSISTENCY,
                        title = "Kebiasaan menabung bagus!",
                        description = "Kamu berhasil menyisihkan $savingsRate% dari pemasukan. Pertahankan!",
                        severity = AnomalySeverity.LOW,
                    )
                )
            }
        }

        val transactionCount = repository.getTransactionCount(currentMonthStart, currentMonthEnd)
        val daysInMonth = Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH).coerceAtLeast(1)
        val avgPerDay = transactionCount.toFloat() / daysInMonth
        if (avgPerDay < 0.3f && lastMonthExpense > 0) {
            anomalies.add(
                AnomalyItem(
                    type = AnomalyType.FREQUENCY_DROP,
                    title = "Frekuensi mencatat menurun",
                    description = "Kamu hanya mencatat ${"%.0f".format(avgPerDay * daysInMonth)} transaksi bulan ini. Yuk lebih rajin!",
                    severity = AnomalySeverity.LOW,
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
            insights = insights,
        )
    }
}
