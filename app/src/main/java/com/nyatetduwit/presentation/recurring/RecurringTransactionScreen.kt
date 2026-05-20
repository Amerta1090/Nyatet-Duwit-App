package com.nyatetduwit.presentation.recurring

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.RecurringFrequency
import com.nyatetduwit.domain.model.RecurringTransaction
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecurringTransactionScreen(
    onNavigateBack: () -> Unit,
    onAddRecurring: () -> Unit,
    viewModel: RecurringTransactionViewModel = hiltViewModel(),
) {
    val recurringTransactions by viewModel.recurringTransactions.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val nf = NumberFormat.getNumberInstance(Locale("id", "ID"))
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaksi Berulang") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onAddRecurring) {
                        Icon(Icons.Default.Add, "Add Recurring")
                    }
                },
            )
        },
    ) { paddingValues ->
        if (recurringTransactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                EmptyRecurringState(onAddRecurring = onAddRecurring)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(recurringTransactions, key = { it.id }) { recurring ->
                    RecurringTransactionCard(
                        recurring = recurring,
                        dateFormat = dateFormat,
                        nf = nf,
                        onSkip = { viewModel.skipInstance(recurring.id) },
                        onPause = { viewModel.deactivateRecurring(recurring.id, onNavigateBack) },
                    )
                }
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

@Composable
private fun RecurringTransactionCard(
    recurring: RecurringTransaction,
    dateFormat: SimpleDateFormat,
    nf: NumberFormat,
    onSkip: () -> Unit,
    onPause: () -> Unit,
) {
    var showActions by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = getFrequencyLabel(recurring.frequency),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "Rp ${nf.format(recurring.id.hashCode().toLong() % 10000000)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                IconButton(onClick = { showActions = !showActions }) {
                    Icon(Icons.Default.MoreVert, "Options")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Jatuh tempo:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = dateFormat.format(recurring.nextDue),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedButton(
                    onClick = onSkip,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    Icon(Icons.Default.SkipNext, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Skip")
                }
                OutlinedButton(
                    onClick = onPause,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    Icon(Icons.Default.Pause, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Pause")
                }
            }
        }
    }

    if (showActions) {
        AlertDialog(
            onDismissRequest = { showActions = false },
            title = { Text("Opsi Recurring") },
            text = { Text("Pilih aksi untuk transaksi berulang ini.") },
            confirmButton = {
                TextButton(onClick = { showActions = false }) {
                    Text("Tutup")
                }
            },
        )
    }
}

@Composable
private fun EmptyRecurringState(onAddRecurring: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            Icons.Default.Repeat,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = "Belum ada transaksi berulang",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "Setup transaksi berulang untuk tagihan bulanan seperti listrik, internet, atau kos.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Button(onClick = onAddRecurring) {
            Icon(Icons.Default.Add, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Setup Recurring")
        }
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
