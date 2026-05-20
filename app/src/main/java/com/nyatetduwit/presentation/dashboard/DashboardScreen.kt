package com.nyatetduwit.presentation.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.TransactionType
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToAccounts: () -> Unit,
    onNavigateToCategories: () -> Unit,
    onNavigateToTransactions: () -> Unit,
    onNavigateToBudgets: () -> Unit,
    onNavigateToRecurring: () -> Unit,
    onNavigateToAddTransaction: () -> Unit,
    onNavigateToTransactionDetail: (String) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NyatetDuwit") },
                actions = {
                    IconButton(onClick = { viewModel.toggleBalanceVisibility() }) {
                        Icon(
                            imageVector = if (uiState.isBalanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (uiState.isBalanceVisible) "Sembunyikan saldo" else "Tampilkan saldo",
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddTransaction,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Transaksi")
            }
        },
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = { viewModel.refresh() },
            state = pullToRefreshState,
            modifier = Modifier.padding(paddingValues),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    BalanceCard(
                        totalBalance = uiState.totalBalance,
                        isBalanceVisible = uiState.isBalanceVisible,
                        onToggleVisibility = { viewModel.toggleBalanceVisibility() },
                    )
                }

                item {
                    IncomeExpenseCard(
                        monthlyIncome = uiState.monthlyIncome,
                        monthlyExpense = uiState.monthlyExpense,
                    )
                }

                if (uiState.topCategories.isNotEmpty()) {
                    item {
                        Text(
                            text = "Kategori Teratas Bulan Ini",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            uiState.topCategories.forEach { category ->
                                TopCategoryCard(
                                    item = category,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    }
                }

                if (uiState.recentTransactions.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Transaksi Terakhir",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            TextButton(onClick = onNavigateToTransactions) {
                                Text("Lihat Semua")
                            }
                        }
                    }

                    items(uiState.recentTransactions) { transaction ->
                        RecentTransactionItem(
                            transaction = transaction,
                            onClick = { onNavigateToTransactionDetail(transaction.id) },
                        )
                    }
                }

                item {
                    MenuSection(
                        onNavigateToAccounts = onNavigateToAccounts,
                        onNavigateToCategories = onNavigateToCategories,
                        onNavigateToTransactions = onNavigateToTransactions,
                        onNavigateToBudgets = onNavigateToBudgets,
                        onNavigateToRecurring = onNavigateToRecurring,
                    )
                }

                if (uiState.recentTransactions.isEmpty() && uiState.monthlyIncome == 0L && uiState.monthlyExpense == 0L) {
                    item {
                        DashboardEmptyState(
                            onAddTransaction = onNavigateToAddTransaction,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BalanceCard(
    totalBalance: Long,
    isBalanceVisible: Boolean,
    onToggleVisibility: () -> Unit,
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Total Saldo",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                IconButton(onClick = onToggleVisibility) {
                    Icon(
                        imageVector = if (isBalanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isBalanceVisible) "Sembunyikan saldo" else "Tampilkan saldo",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isBalanceVisible) formatRupiah(totalBalance) else "Rp ****",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Composable
private fun IncomeExpenseCard(
    monthlyIncome: Long,
    monthlyExpense: Long,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Bulan Ini",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(12.dp))
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
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = formatRupiah(monthlyIncome),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .height(40.dp)
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
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
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = formatRupiah(monthlyExpense),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
    }
}

@Composable
private fun TopCategoryCard(
    item: TopCategoryItem,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = item.categoryIcon,
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.categoryName,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = formatRupiah(item.total),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun RecentTransactionItem(
    transaction: com.nyatetduwit.domain.model.Transaction,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                    text = transaction.notes ?: getTransactionTypeLabel(transaction.type),
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
                text = formatTransactionAmount(transaction),
                style = MaterialTheme.typography.titleSmall,
                color = when (transaction.type) {
                    TransactionType.INCOME -> MaterialTheme.colorScheme.primary
                    TransactionType.EXPENSE -> MaterialTheme.colorScheme.error
                    TransactionType.TRANSFER -> MaterialTheme.colorScheme.onSurfaceVariant
                },
            )
        }
    }
}

@Composable
private fun MenuSection(
    onNavigateToAccounts: () -> Unit,
    onNavigateToCategories: () -> Unit,
    onNavigateToTransactions: () -> Unit,
    onNavigateToBudgets: () -> Unit,
    onNavigateToRecurring: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Menu",
            style = MaterialTheme.typography.titleMedium,
        )
        MenuCard(
            icon = Icons.Default.SwapHoriz,
            title = "Transaksi",
            description = "Lihat dan kelola semua transaksi",
            onClick = onNavigateToTransactions,
        )
        MenuCard(
            icon = Icons.Default.AccountBalanceWallet,
            title = "Akun",
            description = "Kelola akun cash, bank, dan e-wallet",
            onClick = onNavigateToAccounts,
        )
        MenuCard(
            icon = Icons.Default.Category,
            title = "Kategori",
            description = "Kelola kategori pemasukan & pengeluaran",
            onClick = onNavigateToCategories,
        )
        MenuCard(
            icon = Icons.Default.PieChart,
            title = "Budget",
            description = "Set dan pantau budget bulanan",
            onClick = onNavigateToBudgets,
        )
        MenuCard(
            icon = Icons.Default.Repeat,
            title = "Transaksi Berulang",
            description = "Kelola transaksi otomatis berulang",
            onClick = onNavigateToRecurring,
        )
    }
}

@Composable
private fun MenuCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun DashboardEmptyState(
    onAddTransaction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "👋",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Yuk mulai nyatet!",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tap tombol + di bawah untuk transaksi pertamamu",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(16.dp))
        FilledTonalButton(onClick = onAddTransaction) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Tambah Transaksi")
        }
    }
}

private fun formatRupiah(amount: Long): String {
    val prefix = if (amount < 0) "-" else ""
    val absAmount = kotlin.math.abs(amount)
    return "${prefix}Rp ${String.format("%,d", absAmount).replace(',', '.')}"
}

private fun getTransactionTypeLabel(type: TransactionType): String {
    return when (type) {
        TransactionType.INCOME -> "Pemasukan"
        TransactionType.EXPENSE -> "Pengeluaran"
        TransactionType.TRANSFER -> "Transfer"
    }
}

private fun formatDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("d MMM yyyy, HH:mm", Locale("id"))
    return sdf.format(Date(timestamp))
}

private fun formatTransactionAmount(transaction: com.nyatetduwit.domain.model.Transaction): String {
    return when (transaction.type) {
        TransactionType.INCOME -> "+${formatRupiah(transaction.amount)}"
        TransactionType.EXPENSE -> "-${formatRupiah(transaction.amount)}"
        TransactionType.TRANSFER -> formatRupiah(transaction.amount)
    }
}
