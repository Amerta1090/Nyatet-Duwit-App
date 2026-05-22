package com.nyatetduwit.presentation.monthlysummary

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
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
                title = {
                    Text(
                        text = "Ringkasan Bulanan",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToReminderSettings) {
                        Icon(Icons.Default.Notifications, contentDescription = "Pengaturan Reminder")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(NyatetDuwitSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.lg),
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
        shape = RoundedCornerShape(NyatetDuwitRadius.md),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NyatetDuwitSpacing.lg),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Bulan sebelumnya",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Text(
                text = getMonthYearLabel(year, month),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )

            val now = Calendar.getInstance()
            val isCurrentMonth = year == now.get(Calendar.YEAR) && month == now.get(Calendar.MONTH)
            IconButton(
                onClick = onNextMonth,
                enabled = !isCurrentMonth,
            ) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Bulan berikutnya",
                    tint = if (isCurrentMonth) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                )
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
        shape = RoundedCornerShape(NyatetDuwitRadius.md),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NyatetDuwitSpacing.lg),
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
                        imageVector = Icons.Default.TrendingDown,
                        contentDescription = null,
                        tint = NyatetDuwitColor.green500,
                        modifier = Modifier.size(22.dp),
                    )
                    Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
                    Text(
                        text = "Pemasukan",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = formatRupiah(totalIncome),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NyatetDuwitColor.green500,
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
                        .height(64.dp)
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = NyatetDuwitColor.red500,
                        modifier = Modifier.size(22.dp),
                    )
                    Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
                    Text(
                        text = "Pengeluaran",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = formatRupiah(totalExpense),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NyatetDuwitColor.red500,
                    )
                    if (comparison != null) {
                        ComparisonBadge(
                            percentage = comparison.expenseChangePercent,
                            isPositive = false,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.md))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
            )
            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Selisih",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = formatRupiah(netSavings),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (netSavings >= 0) NyatetDuwitColor.green500 else NyatetDuwitColor.red500,
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
    val label = if (percentage > 0) "+${absPercent.toInt()}%" else if (percentage < 0) "-${absPercent.toInt()}%" else "="
    val color = if (isPositive) {
        if (percentage > 0) NyatetDuwitColor.green500 else NyatetDuwitColor.red500
    } else {
        if (percentage > 0) NyatetDuwitColor.red500 else NyatetDuwitColor.green500
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(NyatetDuwitRadius.xs))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = NyatetDuwitSpacing.sm, vertical = NyatetDuwitSpacing.xxs)
            .padding(top = NyatetDuwitSpacing.xxs),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = color,
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
        horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.md),
    ) {
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(NyatetDuwitRadius.md),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NyatetDuwitSpacing.md),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "$activeDays",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xxs))
                Text(
                    text = "Hari Aktif",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(NyatetDuwitRadius.md),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NyatetDuwitSpacing.md),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "$transactionCount",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xxs))
                Text(
                    text = "Transaksi",
                    style = MaterialTheme.typography.labelMedium,
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
        shape = RoundedCornerShape(NyatetDuwitRadius.md),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NyatetDuwitSpacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = item.categoryIcon,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.size(36.dp),
            )
            Spacer(modifier = Modifier.width(NyatetDuwitSpacing.md))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.categoryName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
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
        shape = RoundedCornerShape(NyatetDuwitRadius.md),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NyatetDuwitSpacing.lg),
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
        shape = RoundedCornerShape(NyatetDuwitRadius.md),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NyatetDuwitSpacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.notes ?: "Tanpa catatan",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xxs))
                Text(
                    text = formatDateTime(transaction.dateTime),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                text = "-${formatRupiah(transaction.amount)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = NyatetDuwitColor.red500,
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
