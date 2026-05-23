package com.nyatetduwit.presentation.checkin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.core.util.formatRupiah
import com.nyatetduwit.domain.usecase.habit.WeeklyCheckInData

@Composable
fun WeeklyCheckInCard(
    checkInData: WeeklyCheckInData?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (checkInData == null) return

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
        ),
    ) {
        Column(
            modifier = Modifier.padding(NyatetDuwitSpacing.lg),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Ringkasan Mingguan",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Tutup", modifier = Modifier.size(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.md))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = formatRupiah(checkInData.totalExpense),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = NyatetDuwitColor.red500,
                    )
                    Text(
                        text = "Pengeluaran",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = formatRupiah(checkInData.totalIncome),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = NyatetDuwitColor.green500,
                    )
                    Text(
                        text = "Pemasukan",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${checkInData.transactionCount}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Transaksi",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            if (checkInData.daysActive > 0) {
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
                Text(
                    text = "Kamu aktif ${checkInData.daysActive} hari minggu ini",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }

            if (checkInData.vsPreviousWeekPercent != 0) {
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
                val trendText = if (checkInData.vsPreviousWeekPercent > 0)
                    "Naik ${checkInData.vsPreviousWeekPercent}% dari minggu lalu"
                else
                    "Turun ${-checkInData.vsPreviousWeekPercent}% dari minggu lalu"
                val trendColor = if (checkInData.vsPreviousWeekPercent > 0)
                    NyatetDuwitColor.red500 else NyatetDuwitColor.green500
                Text(
                    text = trendText,
                    style = MaterialTheme.typography.bodySmall,
                    color = trendColor,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun ProgressFeedbackCard(
    totalTransactions: Int,
    currentMonthExpense: Long,
    daysActiveThisMonth: Int,
    streakDays: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(NyatetDuwitSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
        ) {
            Text(
                text = "Progress Kamu",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                StatItem(value = totalTransactions.toString(), label = "Total Transaksi")
                StatItem(value = daysActiveThisMonth.toString(), label = "Hari Aktif")
                StatItem(value = streakDays.toString(), label = "Streak 🔥")
            }

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xs))

            Text(
                text = "Pengeluaran bulan ini: ${formatRupiah(currentMonthExpense)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun AntiBoncosCard(
    dailyAllowance: Long,
    remainingBudget: Long,
    remainingDays: Int,
    isWarning: Boolean,
    isDanger: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isDanger -> NyatetDuwitColor.red100
                isWarning -> NyatetDuwitColor.amber100
                else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            },
        ),
    ) {
        Column(
            modifier = Modifier.padding(NyatetDuwitSpacing.lg),
        ) {
            Text(
                text = "Anti-Boncos 🛡️",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.md))

            if (isDanger) {
                Text(
                    text = "Waduh, budget bulan ini sudah habis!",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = NyatetDuwitColor.red500,
                )
            } else if (isWarning) {
                Text(
                    text = "Hampir habis! Sisa ${formatRupiah(remainingBudget)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = NyatetDuwitColor.amber500,
                )
            }

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = "Jatah Harian",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = formatRupiah(dailyAllowance),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Sisa Budget",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = formatRupiah(remainingBudget),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))

            Text(
                text = "$remainingDays hari lagi di bulan ini",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
