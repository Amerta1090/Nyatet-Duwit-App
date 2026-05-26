package com.nyatetduwit.presentation.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.core.util.HapticFeedback
import com.nyatetduwit.core.util.formatRupiah
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.presentation.checkin.AntiBoncosCard
import com.nyatetduwit.presentation.checkin.ProgressFeedbackCard
import com.nyatetduwit.presentation.checkin.WeeklyCheckInCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToAccounts: () -> Unit,
    onNavigateToCategories: () -> Unit,
    onNavigateToTransactions: () -> Unit,
    onNavigateToBudgets: () -> Unit,
    onNavigateToRecurring: () -> Unit,
    onNavigateToTemplates: () -> Unit,
    onNavigateToMonthlySummary: () -> Unit,
    onNavigateToSettings: () -> Unit = {},
    onNavigateToGoals: () -> Unit = {},
    onNavigateToDebts: () -> Unit = {},
    onNavigateToCashflowTrend: () -> Unit = {},
    onNavigateToInvestments: () -> Unit = {},
    onNavigateToSplitBills: () -> Unit = {},
    onNavigateToAddTransaction: () -> Unit,
    onNavigateToTransactionDetail: (String) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()
    val haptic = LocalHapticFeedback.current
    val view = LocalView.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(NyatetDuwitRadius.sm))
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "N",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                        Text(
                            text = "NyatetDuwit",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
                actions = {
                    Row(horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.xxs)) {
                        IconButton(onClick = { viewModel.toggleBalanceVisibility() }) {
                            Icon(
                                imageVector = if (uiState.isBalanceVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                        IconButton(onClick = {
                            HapticFeedback.click(view)
                            onNavigateToSettings()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
        floatingActionButton = {
            FAB(onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onNavigateToAddTransaction()
            })
        },
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { viewModel.refresh() },
            state = pullToRefreshState,
            modifier = Modifier.padding(padding),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = NyatetDuwitSpacing.xl,
                    end = NyatetDuwitSpacing.xl,
                    top = NyatetDuwitSpacing.sm,
                    bottom = 96.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.lg),
            ) {
                item(key = "balance") {
                    BalanceHero(
                        totalBalance = uiState.totalBalance,
                        isBalanceVisible = uiState.isBalanceVisible,
                    )
                }

                item(key = "income_expense") {
                    IncomeExpenseSection(
                        income = uiState.monthlyIncome,
                        expense = uiState.monthlyExpense,
                    )
                }

                if (uiState.dailyAllowance != null) {
                    item(key = "anti_boncos") {
                        val allowance = uiState.dailyAllowance!!
                        AntiBoncosCard(
                            dailyAllowance = allowance.dailyAllowance,
                            remainingBudget = allowance.remainingBudget,
                            remainingDays = allowance.remainingDays,
                            isWarning = allowance.isWarning,
                            isDanger = allowance.isDanger,
                        )
                    }
                }

                if (uiState.showWeeklyCheckIn && uiState.weeklyCheckIn != null) {
                    item(key = "weekly_checkin") {
                        WeeklyCheckInCard(
                            checkInData = uiState.weeklyCheckIn,
                            onDismiss = { viewModel.dismissWeeklyCheckIn() },
                        )
                    }
                }

                if (uiState.recentTransactions.isNotEmpty()) {
                    item(key = "recent_header") {
                        SectionHeader(
                            title = "Transaksi terakhir",
                            action = "Lihat semua",
                            onAction = onNavigateToTransactions,
                        )
                    }
                    items(
                        items = uiState.recentTransactions,
                        key = { "recent_${it.id}" },
                    ) { transaction ->
                        TransactionRow(
                            transaction = transaction,
                            onClick = { onNavigateToTransactionDetail(transaction.id) },
                        )
                    }
                }

                if (uiState.topCategories.isNotEmpty()) {
                    item(key = "top_categories") {
                        Column(verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm)) {
                            SectionHeader(title = "Pengeluaran terbesar", action = null, onAction = {})
                            TopCategoryBars(items = uiState.topCategories)
                        }
                    }
                }

                if (uiState.streakData.currentStreak > 0) {
                    item(key = "progress_feedback") {
                        ProgressFeedbackCard(
                            totalTransactions = uiState.recentTransactions.size,
                            currentMonthExpense = uiState.monthlyExpense,
                            daysActiveThisMonth = 0,
                            streakDays = uiState.streakData.currentStreak,
                        )
                    }
                }

                item(key = "quick_menu") {
                    QuickMenuRow(
                        onAccounts = onNavigateToAccounts,
                        onBudgets = onNavigateToBudgets,
                        onMonthlySummary = onNavigateToMonthlySummary,
                        onTemplates = onNavigateToTemplates,
                        onRecurring = onNavigateToRecurring,
                        onCategories = onNavigateToCategories,
                        onGoals = onNavigateToGoals,
                        onDebts = onNavigateToDebts,
                        onCashflowTrend = onNavigateToCashflowTrend,
                        onInvestments = onNavigateToInvestments,
                        onSplitBills = onNavigateToSplitBills,
                    )
                }

                if (uiState.recentTransactions.isEmpty() && uiState.monthlyIncome == 0L && uiState.monthlyExpense == 0L) {
                    item(key = "empty") {
                        EmptyDashboardState(onAddTransaction = onNavigateToAddTransaction)
                    }
                }
            }
        }
    }
}

@Composable
private fun BalanceHero(
    totalBalance: Long,
    isBalanceVisible: Boolean,
) {
    val primary = MaterialTheme.colorScheme.primary
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(NyatetDuwitRadius.lg))
            .background(
                Brush.verticalGradient(
                    colors = listOf(primary, primaryContainer),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY,
                )
            )
            .padding(NyatetDuwitSpacing.xxl),
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val path = Path().apply {
                moveTo(w * 0.7f, 0f)
                cubicTo(w * 0.85f, h * 0.3f, w * 0.95f, h * 0.1f, w + 40f, h * 0.4f)
                lineTo(w + 40f, h + 40f)
                lineTo(-40f, h + 40f)
                lineTo(-40f, 0f)
                close()
            }
            drawPath(
                path = path,
                color = Color.White.copy(alpha = 0.06f),
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Total Saldo",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
            )
            Text(
                text = if (isBalanceVisible) formatRupiah(totalBalance) else "Rp •••••••",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun IncomeExpenseSection(
    income: Long,
    expense: Long,
) {
    val total = income + expense
    val expenseRatio = if (total > 0) expense.toFloat() / total else 0.5f
    val animatedRatio by animateFloatAsState(
        targetValue = expenseRatio,
        animationSpec = spring(stiffness = 200f),
        label = "ratio",
    )
    val netSavings = income - expense

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(NyatetDuwitRadius.md))
            .background(MaterialTheme.colorScheme.surface)
            .padding(NyatetDuwitSpacing.lg),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LabeledAmount(
                label = "Pemasukan",
                amount = income,
                color = NyatetDuwitColor.teal500,
            )
            LabeledAmount(
                label = "Pengeluaran",
                amount = expense,
                color = NyatetDuwitColor.red500,
                alignEnd = true,
            )
        }

        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.md))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .clip(RoundedCornerShape(NyatetDuwitRadius.full))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.CenterStart,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedRatio)
                    .height(32.dp)
                    .clip(RoundedCornerShape(NyatetDuwitRadius.full))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                NyatetDuwitColor.teal400,
                                NyatetDuwitColor.teal500,
                                NyatetDuwitColor.coral500,
                                NyatetDuwitColor.red500,
                            ),
                        )
                    ),
            )

            Text(
                text = "${(expenseRatio * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = NyatetDuwitSpacing.md),
            )
        }

        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))

        Text(
            text = if (netSavings >= 0) {
                "Sisa ${formatRupiah(netSavings)} dari total ${formatRupiah(total)}"
            } else {
                "Defisit ${formatRupiah(-netSavings)} dari total ${formatRupiah(total)}"
            },
            style = MaterialTheme.typography.labelMedium,
            color = if (netSavings >= 0) NyatetDuwitColor.teal500 else NyatetDuwitColor.red500,
        )
    }
}

@Composable
private fun LabeledAmount(
    label: String,
    amount: Long,
    color: Color,
    alignEnd: Boolean = false,
) {
    val horizontalAlignment = if (alignEnd) Alignment.End else Alignment.Start
    Column(horizontalAlignment = horizontalAlignment) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = formatRupiah(amount),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = color,
        )
    }
}

@Composable
private fun TopCategoryBars(items: List<TopCategoryItem>) {
    val maxAmount = items.maxOfOrNull { it.total } ?: 1L

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(NyatetDuwitRadius.md))
            .background(MaterialTheme.colorScheme.surface)
            .padding(NyatetDuwitSpacing.lg),
        verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.md),
    ) {
        items.forEach { item ->
            val fraction = if (maxAmount > 0) item.total.toFloat() / maxAmount else 0f
            val animatedFraction by animateFloatAsState(
                targetValue = fraction,
                animationSpec = spring(stiffness = 150f),
                label = "catBar",
            )

            Column(verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.xs)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = item.categoryIcon,
                            style = MaterialTheme.typography.titleSmall,
                        )
                        Text(
                            text = item.categoryName,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    Text(
                        text = formatRupiah(item.total),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }

                val catColor = runCatching {
                    Color(item.categoryColor.substring(1).toLong(16) or 0xFF000000L)
                }.getOrElse { MaterialTheme.colorScheme.primary }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(NyatetDuwitRadius.full))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedFraction)
                            .height(6.dp)
                            .clip(RoundedCornerShape(NyatetDuwitRadius.full))
                            .background(catColor),
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionRow(
    transaction: Transaction,
    onClick: () -> Unit,
) {
    val (icon, bgColor, amountColor) = when (transaction.type) {
        TransactionType.INCOME -> Triple(
            "↓",
            NyatetDuwitColor.teal50,
            NyatetDuwitColor.teal500,
        )
        TransactionType.EXPENSE -> Triple(
            "↑",
            NyatetDuwitColor.red50,
            NyatetDuwitColor.red500,
        )
        TransactionType.TRANSFER -> Triple(
            "⇄",
            NyatetDuwitColor.amber50,
            NyatetDuwitColor.amber700,
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(NyatetDuwitRadius.md))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
            ) { onClick() }
            .padding(
                start = NyatetDuwitSpacing.lg,
                end = NyatetDuwitSpacing.md,
                top = NyatetDuwitSpacing.sm + 2.dp,
                bottom = NyatetDuwitSpacing.sm + 2.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(NyatetDuwitRadius.sm))
                .background(bgColor),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = amountColor,
            )
        }

        Spacer(modifier = Modifier.width(NyatetDuwitSpacing.md))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.notes ?: labelForType(transaction.type),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = formatDateShort(transaction.dateTime),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.xs),
        ) {
            Text(
                text = formatAmount(transaction),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = amountColor,
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

@Composable
private fun QuickMenuRow(
    onAccounts: () -> Unit,
    onBudgets: () -> Unit,
    onMonthlySummary: () -> Unit,
    onTemplates: () -> Unit,
    onRecurring: () -> Unit,
    onCategories: () -> Unit,
    onGoals: () -> Unit,
    onDebts: () -> Unit,
    onCashflowTrend: () -> Unit,
    onInvestments: () -> Unit,
    onSplitBills: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm)) {
        SectionHeader(title = "Menu", action = null, onAction = {})

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
        ) {
            QuickMenuItem(label = "Akun", emoji = "💳", onClick = onAccounts, modifier = Modifier.weight(1f))
            QuickMenuItem(label = "Budget", emoji = "📊", onClick = onBudgets, modifier = Modifier.weight(1f))
            QuickMenuItem(label = "Ringkasan", emoji = "📋", onClick = onMonthlySummary, modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
        ) {
            QuickMenuItem(label = "Template", emoji = "⚡", onClick = onTemplates, modifier = Modifier.weight(1f))
            QuickMenuItem(label = "Berulang", emoji = "🔄", onClick = onRecurring, modifier = Modifier.weight(1f))
            QuickMenuItem(label = "Kategori", emoji = "🏷️", onClick = onCategories, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun QuickMenuItem(
    label: String,
    emoji: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(NyatetDuwitRadius.md))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onClick()
            }
            .padding(
                horizontal = NyatetDuwitSpacing.md,
                vertical = NyatetDuwitSpacing.sm + 2.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.xs),
    ) {
        Text(text = emoji, style = MaterialTheme.typography.titleSmall)
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SectionHeader(
    title: String,
    action: String?,
    onAction: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
        ) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(NyatetDuwitRadius.full))
                    .background(MaterialTheme.colorScheme.primary),
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
            )
        }
        if (action != null) {
            Text(
                text = action,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                ) { onAction() },
            )
        }
    }
}

@Composable
private fun FAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(NyatetDuwitRadius.lg),
        modifier = Modifier.padding(bottom = NyatetDuwitSpacing.xl, end = NyatetDuwitSpacing.sm),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Tambah transaksi",
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
private fun EmptyDashboardState(onAddTransaction: () -> Unit) {
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = NyatetDuwitSpacing.xxl),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "📝",
                style = MaterialTheme.typography.headlineLarge,
            )
        }
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.lg))
        Text(
            text = "Belum ada catatan",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xs))
        Text(
            text = "Yuk mulai! Ketuk + buat transaksi pertamamu",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xl))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(NyatetDuwitRadius.md))
                .background(MaterialTheme.colorScheme.primary)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                ) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onAddTransaction()
                }
                .padding(horizontal = NyatetDuwitSpacing.xxl, vertical = NyatetDuwitSpacing.md + 2.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(18.dp),
                )
                Text(
                    text = "Transaksi Baru",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

private fun labelForType(type: TransactionType): String = when (type) {
    TransactionType.INCOME -> "Pemasukan"
    TransactionType.EXPENSE -> "Pengeluaran"
    TransactionType.TRANSFER -> "Transfer"
}

private fun formatAmount(transaction: Transaction): String = when (transaction.type) {
    TransactionType.INCOME -> "+${formatRupiah(transaction.amount)}"
    TransactionType.EXPENSE -> "-${formatRupiah(transaction.amount)}"
    TransactionType.TRANSFER -> formatRupiah(transaction.amount)
}

private fun formatDateShort(timestamp: Long): String {
    val sdf = SimpleDateFormat("d MMM", Locale("id"))
    return sdf.format(Date(timestamp))
}
