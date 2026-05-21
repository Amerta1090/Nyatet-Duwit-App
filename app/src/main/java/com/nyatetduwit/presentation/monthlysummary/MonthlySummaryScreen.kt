package com.nyatetduwit.presentation.monthlysummary

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlySummaryScreen(
    onNavigateBack: () -> Unit,
    onNavigateToReminderSettings: () -> Unit,
    viewModel: MonthlySummaryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ringkasan Bulanan") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToReminderSettings) {
                        Icon(Icons.Default.Notifications, contentDescription = "Pengaturan Reminder")
                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                MonthSelector(
                    year = uiState.selectedYear,
                    month = uiState.selectedMonth,
                    onPreviousMonth = { viewModel.goToPreviousMonth() },
                    onNextMonth = { viewModel.goToNextMonth() },
                )
            }

            item {
                SummaryCards(
                    totalIncome = uiState.totalIncome,
                    totalExpense = uiState.totalExpense,
                    netSavings = uiState.netSavings,
                    comparison = uiState.comparison,
                )
            }

            item {
                StatsRow(
                    activeDays = uiState.activeDays,
                    transactionCount = uiState.transactionCount,
                )
            }

            if (uiState.topCategories.isNotEmpty()) {
                item {
                    Text(
                        text = "Kategori Pengeluaran Teratas",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                items(uiState.topCategories) { category ->
                    TopCategoryRow(item = category)
                }
            }

            if (uiState.dailyTrend.isNotEmpty()) {
                item {
                    Text(
                        text = "Tren Pengeluaran Harian",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                item {
                    SpendingTrendChart(dailyTrend = uiState.dailyTrend)
                }
            }

            if (uiState.biggestExpense != null) {
                item {
                    Text(
                        text = "Transaksi Terbesar Bulan Ini",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                item {
                    BiggestExpenseCard(transaction = uiState.biggestExpense!!)
                }
            }

            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun MonthSelector(
    year: Int,
    month: Int,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(Icons.Default.ChevronLeft, contentDescription = "Bulan sebelumnya")
            }

            Text(
                text = getMonthYearLabel(year, month),
                style = MaterialTheme.typography.titleLarge,
            )

            val now = Calendar.getInstance()
            val isCurrentMonth = year == now.get(Calendar.YEAR) && month == now.get(Calendar.MONTH)
            IconButton(
                onClick = onNextMonth,
                enabled = !isCurrentMonth,
            ) {
                Icon(Icons.Default.ChevronRight, contentDescription = "Bulan berikutnya")
            }
        }
    }
}

@Composable
private fun SummaryCards(
    totalIncome: Long,
    totalExpense: Long,
    netSavings: Long,
    comparison: com.nyatetduwit.domain.usecase.transaction.MonthComparison?,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Pemasukan",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Text(
                        text = formatRupiah(totalIncome),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    if (comparison != null) {
                        ComparisonBadge(
                            percentage = comparison.incomeChangePercent,
                            isPositive = true,
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .height(60.dp)
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Pengeluaran",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Text(
                        text = formatRupiah(totalExpense),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                    if (comparison != null) {
                        ComparisonBadge(
                            percentage = comparison.expenseChangePercent,
                            isPositive = false,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Selisih",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    text = formatRupiah(netSavings),
                    style = MaterialTheme.typography.titleLarge,
                    color = if (netSavings >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Composable
private fun ComparisonBadge(
    percentage: Double,
    isPositive: Boolean,
) {
    val absPercent = abs(percentage)
    val label = if (percentage > 0) "Naik ${absPercent.toInt()}%" else if (percentage < 0) "Turun ${absPercent.toInt()}%" else "Sama"
    val color = if (isPositive) {
        if (percentage > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    } else {
        if (percentage > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(top = 4.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
        )
    }
}

@Composable
private fun StatsRow(
    activeDays: Int,
    transactionCount: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Card(
            modifier = Modifier.weight(1f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "$activeDays",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Hari Aktif",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Card(
            modifier = Modifier.weight(1f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "$transactionCount",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Transaksi",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun TopCategoryRow(item: TopCategoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = item.categoryIcon,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.size(32.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.categoryName,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = formatRupiah(item.total),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun SpendingTrendChart(
    dailyTrend: List<DailyTrendItem>,
) {
    if (dailyTrend.isEmpty()) return

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            val maxAmount = dailyTrend.maxOfOrNull { it.amount } ?: 0L
            val primaryColor = MaterialTheme.colorScheme.primary

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            ) {
                val width = size.width
                val height = size.height
                val padding = 40f
                val chartWidth = width - padding * 2
                val chartHeight = height - padding * 2

                if (dailyTrend.size < 2) return@Canvas

                val stepX = chartWidth / (dailyTrend.size - 1)

                val path = Path()
                dailyTrend.forEachIndexed { index, item ->
                    val x = padding + index * stepX
                    val y = if (maxAmount > 0) {
                        padding + chartHeight - (item.amount.toFloat() / maxAmount.toFloat()) * chartHeight
                    } else {
                        padding + chartHeight
                    }

                    if (index == 0) {
                        path.moveTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                }

                drawPath(
                    path = path,
                    color = primaryColor,
                    style = Stroke(width = 3f),
                )

                dailyTrend.forEachIndexed { index, item ->
                    val x = padding + index * stepX
                    val y = if (maxAmount > 0) {
                        padding + chartHeight - (item.amount.toFloat() / maxAmount.toFloat()) * chartHeight
                    } else {
                        padding + chartHeight
                    }

                    drawCircle(
                        color = primaryColor,
                        radius = 4f,
                        center = Offset(x, y),
                    )
                }
            }
        }
    }
}

@Composable
private fun BiggestExpenseCard(transaction: Transaction) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.notes ?: "Tanpa catatan",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = formatDateTime(transaction.dateTime),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                text = "-${formatRupiah(transaction.amount)}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}

private fun getMonthYearLabel(year: Int, month: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    val sdf = SimpleDateFormat("MMMM yyyy", Locale("id"))
    return sdf.format(calendar.time).replaceFirstChar { it.uppercase() }
}

private fun formatRupiah(amount: Long): String {
    val prefix = if (amount < 0) "-" else ""
    val absAmount = abs(amount)
    return "${prefix}Rp ${String.format("%,d", absAmount).replace(',', '.')}"
}

private fun formatDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("d MMM yyyy, HH:mm", Locale("id"))
    return sdf.format(Date(timestamp))
}
