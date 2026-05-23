package com.nyatetduwit.presentation.dashboard

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.core.util.HapticFeedback
import com.nyatetduwit.core.util.formatRupiah
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
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
                                .size(36.dp)
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
                            style = MaterialTheme.typography.titleLarge,
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
                    start = NyatetDuwitSpacing.lg,
                    end = NyatetDuwitSpacing.lg,
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
                    IncomeExpenseBar(
                        income = uiState.monthlyIncome,
                        expense = uiState.monthlyExpense,
                    )
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

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
                            ) {
                                items(uiState.topCategories) { category ->
                                    TopCategoryPill(item = category)
                                }
                            }
                        }
                    }
                }

                item(key = "quick_menu") {
                    QuickMenuGrid(
                        onAccounts = onNavigateToAccounts,
                        onBudgets = onNavigateToBudgets,
                        onMonthlySummary = onNavigateToMonthlySummary,
                        onTemplates = onNavigateToTemplates,
                        onRecurring = onNavigateToRecurring,
                        onCategories = onNavigateToCategories,
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(NyatetDuwitRadius.lg))
            .background(MaterialTheme.colorScheme.primary)
            .padding(NyatetDuwitSpacing.xxl),
    ) {
        Text(
            text = "Total Saldo",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
        Text(
            text = if (isBalanceVisible) formatRupiah(totalBalance) else "Rp •••••••",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.animateContentSize(animationSpec = tween(300)),
        )
    }
}

@Composable
private fun IncomeExpenseBar(
    income: Long,
    expense: Long,
) {
    val total = income + expense
    val expenseRatio = if (total > 0) expense.toFloat() / total else 0f
    val animatedRatio by animateFloatAsState(
        targetValue = expenseRatio,
        animationSpec = spring(stiffness = 200f),
        label = "ratio",
    )

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
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(NyatetDuwitColor.teal500),
                )
                Spacer(modifier = Modifier.width(NyatetDuwitSpacing.sm))
                Column {
                    Text(
                        text = "Pemasukan",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = formatRupiah(income),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = NyatetDuwitColor.teal500,
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(NyatetDuwitColor.coral500),
                )
                Spacer(modifier = Modifier.width(NyatetDuwitSpacing.sm))
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Pengeluaran",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = formatRupiah(expense),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = NyatetDuwitColor.coral500,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.md))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(NyatetDuwitRadius.full))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedRatio)
                    .height(6.dp)
                    .clip(RoundedCornerShape(NyatetDuwitRadius.full))
                    .background(NyatetDuwitColor.coral500),
            )
        }
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xxs))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            val netSavings = income - expense
            val savingsText = if (netSavings >= 0) {
                "Sisa ${formatRupiah(netSavings)}"
            } else {
                "Defisit ${formatRupiah(-netSavings)}"
            }
            Text(
                text = savingsText,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = if (netSavings >= 0) NyatetDuwitColor.teal500 else NyatetDuwitColor.coral500,
            )
            if (total > 0) {
                Text(
                    text = "${(expenseRatio * 100).toInt()}% habis",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun TopCategoryPill(item: TopCategoryItem) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(NyatetDuwitRadius.md))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
            ) { }
            .padding(horizontal = NyatetDuwitSpacing.md, vertical = NyatetDuwitSpacing.sm + 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = item.categoryIcon, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xxs))
        Text(
            text = item.categoryName,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
        )
        Text(
            text = formatRupiah(item.total),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
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
            NyatetDuwitColor.green100,
            NyatetDuwitColor.green500,
        )
        TransactionType.EXPENSE -> Triple(
            "↑",
            NyatetDuwitColor.red100,
            NyatetDuwitColor.red500,
        )
        TransactionType.TRANSFER -> Triple(
            "⇄",
            NyatetDuwitColor.gold100,
            MaterialTheme.colorScheme.onSurfaceVariant,
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
            .padding(NyatetDuwitSpacing.lg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(NyatetDuwitRadius.md))
                .background(bgColor),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.titleMedium,
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

        Text(
            text = formatAmount(transaction),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = amountColor,
        )
    }
}

@Composable
private fun QuickMenuGrid(
    onAccounts: () -> Unit,
    onBudgets: () -> Unit,
    onMonthlySummary: () -> Unit,
    onTemplates: () -> Unit,
    onRecurring: () -> Unit,
    onCategories: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm)) {
        SectionHeader(title = "Menu Cepat", action = null, onAction = {})

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
        ) {
            QuickMenuItem(
                label = "Akun",
                onClick = onAccounts,
                modifier = Modifier.weight(1f),
            )
            QuickMenuItem(
                label = "Budget",
                onClick = onBudgets,
                modifier = Modifier.weight(1f),
            )
            QuickMenuItem(
                label = "Ringkasan",
                onClick = onMonthlySummary,
                modifier = Modifier.weight(1f),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
        ) {
            QuickMenuItem(
                label = "Template",
                onClick = onTemplates,
                modifier = Modifier.weight(1f),
            )
            QuickMenuItem(
                label = "Berulang",
                onClick = onRecurring,
                modifier = Modifier.weight(1f),
            )
            QuickMenuItem(
                label = "Kategori",
                onClick = onCategories,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun QuickMenuItem(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current

    Box(
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
            .padding(vertical = NyatetDuwitSpacing.md + 2.dp),
        contentAlignment = Alignment.Center,
    ) {
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
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
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
        modifier = Modifier.padding(bottom = NyatetDuwitSpacing.lg, end = NyatetDuwitSpacing.sm),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Tambah transaksi",
            modifier = Modifier.size(26.dp),
        )
    }
}

@Composable
private fun EmptyDashboardState(onAddTransaction: () -> Unit) {
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = NyatetDuwitSpacing.xxxl),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "📝",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.lg))
        Text(
            text = "Belum ada catatan",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
        Text(
            text = "Yuk mulai! Tap + buat transaksi pertamamu",
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
                .padding(horizontal = NyatetDuwitSpacing.xxl, vertical = NyatetDuwitSpacing.md),
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
    val sdf = SimpleDateFormat("d MMM HH:mm", Locale("id"))
    return sdf.format(Date(timestamp))
}
