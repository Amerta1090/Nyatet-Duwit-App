package com.nyatetduwit.presentation.recurring

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.*
import com.nyatetduwit.presentation.components.CustomNumpad
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecurringTransactionFormScreen(
    recurringId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: RecurringTransactionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val formState = uiState.formState

    LaunchedEffect(recurringId) {
        viewModel.loadRecurring(recurringId)
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateBack()
        }
    }

    val nf = NumberFormat.getNumberInstance(Locale("id", "ID"))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (recurringId == null) "Setup Recurring" else "Edit Recurring") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Tipe Transaksi", style = MaterialTheme.typography.labelLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
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
            OutlinedTextField(
                value = if (formState.amount == 0L) "" else nf.format(formState.amount),
                onValueChange = { value ->
                    val cleanValue = value.replace(".", "").replace(",", "")
                    val amount = cleanValue.toLongOrNull() ?: 0L
                    viewModel.onAmountChange(amount)
                },
                label = { Text("Jumlah") },
                placeholder = { Text("0") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Payments, null) },
            )

            Text("Catatan (opsional)", style = MaterialTheme.typography.labelLarge)
            OutlinedTextField(
                value = formState.notes ?: "",
                onValueChange = viewModel::onNotesChange,
                label = { Text("Catatan") },
                placeholder = { Text("Contoh: Tagihan listrik bulanan") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = viewModel::saveRecurring,
                modifier = Modifier.fillMaxWidth(),
                enabled = formState.amount > 0 && formState.accountId.isNotEmpty(),
            ) {
                Icon(Icons.Default.Repeat, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (recurringId == null) "Simpan Recurring" else "Update Recurring")
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
