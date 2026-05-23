package com.nyatetduwit.presentation.recurring

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.core.util.formatRupiah
import com.nyatetduwit.domain.model.*
import com.nyatetduwit.presentation.account.getAccountTypeIcon
import com.nyatetduwit.presentation.account.getAccountTypeLabel
import com.nyatetduwit.presentation.components.CustomNumpad
import com.nyatetduwit.presentation.transaction.getCategoryIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecurringTransactionFormScreen(
    recurringId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: RecurringTransactionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val formState = uiState.formState
    val accounts by viewModel.accounts.collectAsState()
    val categories by viewModel.categories.collectAsState()

    var showCategorySheet by remember { mutableStateOf(false) }
    var showAccountSheet by remember { mutableStateOf(false) }

    LaunchedEffect(recurringId) {
        viewModel.loadRecurring(recurringId)
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (recurringId == null) "Setup Recurring" else "Edit Recurring") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(NyatetDuwitSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.md),
        ) {
            Text("Tipe Transaksi", style = MaterialTheme.typography.labelLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
            ) {
                TransactionType.entries.forEach { type ->
                    FilterChip(
                        selected = type == formState.transactionType,
                        onClick = { viewModel.onTypeChange(type) },
                        label = { Text(getTypeLabel(type)) },
                        leadingIcon = {
                            Icon(
                                imageVector = getTypeIcon(type),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                            )
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Text("Frekuensi", style = MaterialTheme.typography.labelLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
            ) {
                RecurringFrequency.entries.forEach { freq ->
                    FilterChip(
                        selected = freq == formState.frequency,
                        onClick = { viewModel.onFrequencyChange(freq) },
                        label = { Text(getFrequencyLabel(freq)) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Text("Nominal", style = MaterialTheme.typography.labelLarge)
            CustomNumpad(
                onNumberClick = { digits ->
                    val current = formState.amount
                    val newAmount = "${current}$digits".toLongOrNull() ?: 0L
                    viewModel.onAmountChange(newAmount)
                },
                onBackspaceClick = {
                    val current = formState.amount.toString()
                    if (current.length > 1) {
                        viewModel.onAmountChange(current.dropLast(1).toLongOrNull() ?: 0L)
                    } else {
                        viewModel.onAmountChange(0L)
                    }
                },
                onPresetClick = { viewModel.onAmountChange(it) },
                currentAmount = formState.amount,
                modifier = Modifier.padding(horizontal = NyatetDuwitSpacing.sm),
            )

            Text("Akun", style = MaterialTheme.typography.labelLarge)
            OutlinedCard(
                onClick = { showAccountSheet = true },
                modifier = Modifier.fillMaxWidth(),
            ) {
                val selectedAccount = accounts.find { it.id == formState.accountId }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(NyatetDuwitSpacing.md),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val accColor = selectedAccount?.let {
                        runCatching { Color(it.color.substring(1).toLong(16) or 0xFF000000) }
                            .getOrElse { MaterialTheme.colorScheme.primary }
                    } ?: MaterialTheme.colorScheme.onSurfaceVariant
                    Icon(
                        imageVector = if (selectedAccount != null) getAccountTypeIcon(selectedAccount.type) else Icons.Default.AccountBalanceWallet,
                        contentDescription = null,
                        tint = accColor,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(NyatetDuwitSpacing.md))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = selectedAccount?.name ?: "Pilih akun",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        if (selectedAccount != null) {
                            Text(
                                text = getAccountTypeLabel(selectedAccount.type),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            if (formState.transactionType != TransactionType.TRANSFER) {
                Text("Kategori", style = MaterialTheme.typography.labelLarge)
                OutlinedCard(
                    onClick = { showCategorySheet = true },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    val selectedCategory = categories.find { it.id == formState.categoryId }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(NyatetDuwitSpacing.md),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val catColor = selectedCategory?.let {
                            runCatching { Color(it.color.substring(1).toLong(16) or 0xFF000000) }
                                .getOrElse { MaterialTheme.colorScheme.primary }
                        } ?: MaterialTheme.colorScheme.onSurfaceVariant
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = catColor,
                            modifier = Modifier.size(24.dp),
                        )
                        Spacer(modifier = Modifier.width(NyatetDuwitSpacing.md))
                        Text(
                            text = selectedCategory?.name ?: "Pilih kategori",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f),
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            Text("Catatan (opsional)", style = MaterialTheme.typography.labelLarge)
            OutlinedTextField(
                value = formState.notes ?: "",
                onValueChange = viewModel::onNotesChange,
                label = { Text("Catatan") },
                placeholder = { Text("Contoh: Tagihan listrik bulanan") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
            )

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))

            Button(
                onClick = viewModel::saveRecurring,
                modifier = Modifier.fillMaxWidth(),
                enabled = formState.amount > 0 && formState.accountId.isNotEmpty(),
            ) {
                Icon(Icons.Default.Repeat, null)
                Spacer(modifier = Modifier.width(NyatetDuwitSpacing.sm))
                Text(if (recurringId == null) "Simpan Recurring" else "Update Recurring")
            }
        }
    }

    if (showAccountSheet) {
        val sheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = { showAccountSheet = false },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NyatetDuwitSpacing.lg),
            ) {
                Text(
                    text = "Pilih Akun",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.md))
                Column(verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.xs)) {
                    accounts.forEach { account ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(NyatetDuwitRadius.md))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                ) {
                                    viewModel.onAccountChange(account.id)
                                    showAccountSheet = false
                                }
                                .padding(NyatetDuwitSpacing.md),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            val accColor = runCatching {
                                Color(account.color.substring(1).toLong(16) or 0xFF000000)
                            }.getOrElse { MaterialTheme.colorScheme.primary }
                            Icon(
                                imageVector = getAccountTypeIcon(account.type),
                                contentDescription = null,
                                tint = accColor,
                                modifier = Modifier.size(24.dp),
                            )
                            Spacer(modifier = Modifier.width(NyatetDuwitSpacing.md))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(account.name, style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    text = getAccountTypeLabel(account.type),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            if (account.id == formState.accountId) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.lg))
            }
        }
    }

    if (showCategorySheet) {
        val sheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = { showCategorySheet = false },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(NyatetDuwitSpacing.lg),
            ) {
                Text(
                    text = "Pilih Kategori",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.md))
                Column(verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.xs)) {
                    categories.forEach { category ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(NyatetDuwitRadius.md))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                ) {
                                    viewModel.onCategoryChange(category.id)
                                    showCategorySheet = false
                                }
                                .padding(NyatetDuwitSpacing.md),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            val catColor = runCatching {
                                Color(category.color.substring(1).toLong(16) or 0xFF000000)
                            }.getOrElse { MaterialTheme.colorScheme.primary }
                            Surface(
                                modifier = Modifier.size(36.dp),
                                shape = MaterialTheme.shapes.small,
                                color = catColor.copy(alpha = 0.15f),
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = getCategoryIcon(category.icon),
                                        contentDescription = null,
                                        tint = catColor,
                                        modifier = Modifier.size(20.dp),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(NyatetDuwitSpacing.md))
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f),
                            )
                            if (category.id == formState.categoryId) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.lg))
            }
        }
    }

    uiState.error?.let { error ->
        AlertDialog(
            onDismissRequest = viewModel::clearError,
            title = { Text("Error") },
            text = { Text(error) },
            confirmButton = {
                TextButton(onClick = viewModel::clearError) {
                    Text("OK")
                }
            },
        )
    }
}

private fun getTypeLabel(type: TransactionType): String {
    return when (type) {
        TransactionType.INCOME -> "Pemasukan"
        TransactionType.EXPENSE -> "Pengeluaran"
        TransactionType.TRANSFER -> "Transfer"
    }
}

private fun getTypeIcon(type: TransactionType): androidx.compose.ui.graphics.vector.ImageVector {
    return when (type) {
        TransactionType.INCOME -> Icons.Default.ArrowDownward
        TransactionType.EXPENSE -> Icons.Default.ArrowUpward
        TransactionType.TRANSFER -> Icons.Default.SwapHoriz
    }
}

private fun getFrequencyLabel(frequency: RecurringFrequency): String {
    return when (frequency) {
        RecurringFrequency.DAILY -> "Harian"
        RecurringFrequency.WEEKLY -> "Mingguan"
        RecurringFrequency.MONTHLY -> "Bulanan"
        RecurringFrequency.YEARLY -> "Tahunan"
    }
}
