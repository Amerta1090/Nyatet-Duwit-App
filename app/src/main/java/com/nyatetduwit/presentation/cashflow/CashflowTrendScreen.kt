package com.nyatetduwit.presentation.cashflow

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.core.util.formatRupiah
import com.nyatetduwit.domain.usecase.transaction.AnomalyItem
import com.nyatetduwit.domain.usecase.transaction.AnomalySeverity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashflowTrendScreen(
    onNavigateBack: () -> Unit,
    viewModel: CashflowTrendViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tren & Analisis") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.loadReport() }) {
                        Icon(Icons.Default.Refresh, "Refresh")
                    }
                },
            )
        },
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(NyatetDuwitSpacing.lg)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.lg),
            ) {
                val report = uiState.report

                if (report != null && report.monthlyComparison != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(NyatetDuwitRadius.md),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    ) {
                        Column(Modifier.padding(NyatetDuwitSpacing.lg)) {
                            Text("Perbandingan Bulanan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(NyatetDuwitSpacing.md))
                            val comparison = report.monthlyComparison
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Bulan Lalu", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(formatRupiah(comparison.previousMonthTotal), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Bulan Ini", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(formatRupiah(comparison.currentMonthTotal), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Perubahan", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    val pct = comparison.percentageChange
                                    Text(
                                        "${if (pct > 0) "+" else ""}${pct.toInt()}%",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (pct > 0) NyatetDuwitColor.red500 else NyatetDuwitColor.green500,
                                    )
                                }
                            }
                        }
                    }
                }

                if (report != null && report.weeklyTrend.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(NyatetDuwitRadius.md),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    ) {
                        Column(Modifier.padding(NyatetDuwitSpacing.lg)) {
                            Text("Tren Harian", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(NyatetDuwitSpacing.sm))
                            SpendingTrendChart(dailyTrend = report.weeklyTrend)
                        }
                    }
                }

                if (report != null && report.anomalies.isNotEmpty()) {
                    Text("Insight", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                    report.anomalies.forEach { anomaly ->
                        AnomalyCard(anomaly = anomaly)
                    }
                }

                if (report != null && report.anomalies.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(NyatetDuwitRadius.md),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    ) {
                        Column(
                            Modifier.fillMaxWidth().padding(NyatetDuwitSpacing.xxl),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text("📊", style = MaterialTheme.typography.displayMedium)
                            Spacer(Modifier.height(NyatetDuwitSpacing.lg))
                            Text("Tidak ada anomali", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(NyatetDuwitSpacing.sm))
                            Text(
                                "Pola pengeluaran kamu terlihat normal",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnomalyCard(anomaly: AnomalyItem) {
    val bgColor = when (anomaly.severity) {
        AnomalySeverity.HIGH -> NyatetDuwitColor.red100
        AnomalySeverity.MEDIUM -> NyatetDuwitColor.amber100
        AnomalySeverity.LOW -> MaterialTheme.colorScheme.surface
    }
    val iconColor = when (anomaly.severity) {
        AnomalySeverity.HIGH -> NyatetDuwitColor.red500
        AnomalySeverity.MEDIUM -> NyatetDuwitColor.amber500
        AnomalySeverity.LOW -> MaterialTheme.colorScheme.primary
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(NyatetDuwitRadius.md),
        colors = CardDefaults.cardColors(containerColor = bgColor),
    ) {
        Row(
            Modifier.fillMaxWidth().padding(NyatetDuwitSpacing.lg),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(28.dp),
            )
            Spacer(Modifier.width(NyatetDuwitSpacing.md))
            Column(modifier = Modifier.weight(1f)) {
                Text(anomaly.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(NyatetDuwitSpacing.xxs))
                Text(anomaly.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun SpendingTrendChart(dailyTrend: List<com.nyatetduwit.domain.usecase.transaction.DailyExpenseItem>) {
    if (dailyTrend.isEmpty()) return

    val maxAmount = dailyTrend.maxOfOrNull { it.amount } ?: 0L
    val primaryColor = MaterialTheme.colorScheme.primary

    Canvas(
        modifier = Modifier.fillMaxWidth().height(180.dp),
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
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        drawPath(path, color = primaryColor, style = Stroke(width = 3f))

        dailyTrend.forEachIndexed { index, item ->
            val x = padding + index * stepX
            val y = if (maxAmount > 0) {
                padding + chartHeight - (item.amount.toFloat() / maxAmount.toFloat()) * chartHeight
            } else {
                padding + chartHeight
            }
            drawCircle(color = primaryColor, radius = 4f, center = Offset(x, y))
        }
    }
}
