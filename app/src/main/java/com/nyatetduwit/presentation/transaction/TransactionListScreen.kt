package com.nyatetduwit.presentation.transaction

import androidx.compose.foundation.background
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.presentation.account.formatRupiah
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddTransaction: () -> Unit,
    onTransactionClick: (String) -> Unit,
    onTransactionEdit: (String) -> Unit,
    viewModel: TransactionListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val showUndoSnackbar by viewModel.showUndoSnackbar.collectAsState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(showUndoSnackbar) {
        if (showUndoSnackbar) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Transaksi dihapus",
                actionLabel = "Undo",
                duration = SnackbarDuration.Short,
            ).let { result ->
                if (result == SnackbarResult.ActionPerformed) {
                    viewModel.undoDelete()
                } else {
                    viewModel.dismissUndo()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaksi") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    LocalHapticFeedback.current.performHapticFeedback(HapticFeedbackType.LongPress)
                    onNavigateToAddTransaction()
                },
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        },
        snackbarHost = { SnackbarHost(scaffoldState.snackbarHostState) },
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.transactions.isEmpty() -> {
                EmptyTransactionState(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    onAddTransaction = onNavigateToAddTransaction,
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(bottom = 80.dp),
                ) {
                    uiState.transactions.forEach { group ->
                        item {
                            DateHeader(dateLabel = group.dateLabel)
                        }
                        items(group.transactions) { transaction ->
                            TransactionItem(
                                transaction = transaction,
                                onClick = { onTransactionClick(transaction.id) },
                                onEdit = { onTransactionEdit(transaction.id) },
                                onDelete = { viewModel.deleteTransaction(transaction.id) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DateHeader(
    dateLabel: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
    ) {
        Text(
            text = dateLabel,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = when (transaction.type) {
                        TransactionType.INCOME -> "📈"
                        TransactionType.EXPENSE -> "📉"
                        TransactionType.TRANSFER -> "🔄"
                    },
                    style = MaterialTheme.typography.titleMedium,
                )

                Column {
                    Text(
                        text = transaction.notes ?: getCategoryName(transaction),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = formatTime(transaction.dateTime),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Text(
                text = formatTransactionAmount(transaction),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = when (transaction.type) {
                    TransactionType.INCOME -> MaterialTheme.colorScheme.primary
                    TransactionType.EXPENSE -> MaterialTheme.colorScheme.error
                    TransactionType.TRANSFER -> MaterialTheme.colorScheme.onSurface
                },
            )

            IconButton(
                onClick = { showMenu = true },
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "Options")
            }
        }
    }

    if (showMenu) {
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = {
                    showMenu = false
                    onEdit()
                },
                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
            )
            DropdownMenuItem(
                text = { Text("Hapus") },
                onClick = {
                    showMenu = false
                    onDelete()
                },
                leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null) },
            )
        }
    }
}

private fun getCategoryName(transaction: Transaction): String {
    return when (transaction.type) {
        TransactionType.INCOME -> "Pemasukan"
        TransactionType.EXPENSE -> "Pengeluaran"
        TransactionType.TRANSFER -> "Transfer"
    }
}

private fun formatTime(dateTime: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale("id"))
    return sdf.format(Date(dateTime))
}

private fun formatTransactionAmount(transaction: Transaction): String {
    return when (transaction.type) {
        TransactionType.INCOME -> "+${formatRupiah(transaction.amount)}"
        TransactionType.EXPENSE -> "-${formatRupiah(transaction.amount)}"
        TransactionType.TRANSFER -> formatRupiah(transaction.amount)
    }
}

@Composable
fun EmptyTransactionState(
    modifier: Modifier = Modifier,
    onAddTransaction: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "💸",
            style = MaterialTheme.typography.displayLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Belum ada transaksi",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Yuk mulai nyatet! Tap tombol + di bawah untuk transaksi pertamamu 🚀",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onAddTransaction) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Tambah Transaksi")
        }
    }
}
