package com.nyatetduwit.presentation.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionSplit
import com.nyatetduwit.domain.model.TransactionTag
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.presentation.account.formatRupiah
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    transactionId: String,
    onNavigateBack: () -> Unit,
    onEditTransaction: (String) -> Unit,
    viewModel: TransactionDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(transactionId) {
        viewModel.loadTransaction(transactionId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Transaksi") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { onEditTransaction(transactionId) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = {
                        uiState.transaction?.let { viewModel.deleteTransaction(it) }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Hapus")
                    }
                },
            )
        },
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
            uiState.transaction == null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Transaksi tidak ditemukan")
                }
            }
            else -> {
                TransactionDetailContent(
                    transaction = uiState.transaction!!,
                    account = uiState.account,
                    toAccount = uiState.toAccount,
                    category = uiState.category,
                    splits = uiState.splits,
                    tags = uiState.tags,
                    splitCategories = uiState.splitCategories,
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                )
            }
        }
    }

    if (uiState.showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDeleteDialog() },
            title = { Text("Hapus Transaksi?") },
            text = { Text("Transaksi yang dihapus bisa di-undo dalam 3 detik.") },
            confirmButton = {
                TextButton(onClick = { viewModel.confirmDelete() }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissDeleteDialog() }) {
                    Text("Batal")
                }
            },
        )
    }
}

@Composable
fun TransactionDetailContent(
    transaction: Transaction,
    account: Account?,
    toAccount: Account?,
    category: Category?,
    splits: List<TransactionSplit>,
    tags: List<TransactionTag>,
    splitCategories: Map<String, Category?>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(NyatetDuwitSpacing.lg),
        verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.md),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = when (transaction.type) {
                    TransactionType.INCOME -> MaterialTheme.colorScheme.primaryContainer
                    TransactionType.EXPENSE -> MaterialTheme.colorScheme.errorContainer
                    TransactionType.TRANSFER -> MaterialTheme.colorScheme.surfaceVariant
                },
            ),
        ) {
            Column(
                modifier = Modifier.padding(NyatetDuwitSpacing.xxl),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = when (transaction.type) {
                        TransactionType.INCOME -> "Pemasukan"
                        TransactionType.EXPENSE -> "Pengeluaran"
                        TransactionType.TRANSFER -> "Transfer"
                    },
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
                Text(
                    text = formatTransactionAmount(transaction),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        DetailRow(
            icon = Icons.Default.AccountBalanceWallet,
            label = "Akun",
            value = account?.name ?: "Unknown",
        )

        if (transaction.type == TransactionType.TRANSFER) {
            DetailRow(
                icon = Icons.Default.SwapHoriz,
                label = "Ke Akun",
                value = toAccount?.name ?: "Unknown",
            )
        }

        if (transaction.type != TransactionType.TRANSFER && splits.isEmpty()) {
            DetailRow(
                icon = Icons.Default.Category,
                label = "Kategori",
                value = category?.name ?: "Tidak ada kategori",
            )
        }

        if (splits.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                ),
            ) {
                Column(modifier = Modifier.padding(NyatetDuwitSpacing.md)) {
                    Text(
                        text = "Split Transaksi",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
                    splits.forEach { split ->
                        val splitCat = splitCategories[split.categoryId]
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = splitCat?.name ?: split.categoryId,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Text(
                                text = formatRupiah(split.amount),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    }
                }
            }
        }

        if (tags.isNotEmpty()) {
            Column {
                Text(
                    text = "Tags",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xs))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.xs)) {
                    items(tags) { tag ->
                        Surface(
                            shape = RoundedCornerShape(NyatetDuwitRadius.full),
                            color = MaterialTheme.colorScheme.primaryContainer,
                        ) {
                            Text(
                                text = "#${tag.tagName}",
                                modifier = Modifier.padding(
                                    horizontal = NyatetDuwitSpacing.sm,
                                    vertical = NyatetDuwitSpacing.xxs,
                                ),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                    }
                }
            }
        }

        transaction.notes?.let { notes ->
            DetailRow(
                icon = Icons.Default.Description,
                label = "Catatan",
                value = notes,
            )
        }

        transaction.attachmentPath?.let {
            DetailRow(
                icon = Icons.Default.AttachFile,
                label = "Lampiran",
                value = "File terlampir",
            )
        }

        DetailRow(
            icon = Icons.Default.Schedule,
            label = "Tanggal dan Waktu",
            value = formatDateTime(transaction.dateTime),
        )

        DetailRow(
            icon = Icons.Default.Info,
            label = "Dibuat",
            value = formatDateTime(transaction.createdAt),
        )
    }
}

@Composable
fun DetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

private fun formatTransactionAmount(transaction: Transaction): String {
    return when (transaction.type) {
        TransactionType.INCOME -> "+${formatRupiah(transaction.amount)}"
        TransactionType.EXPENSE -> "-${formatRupiah(transaction.amount)}"
        TransactionType.TRANSFER -> formatRupiah(transaction.amount)
    }
}

private fun formatDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("d MMM yyyy, HH:mm", Locale("id"))
    return sdf.format(Date(timestamp))
}
