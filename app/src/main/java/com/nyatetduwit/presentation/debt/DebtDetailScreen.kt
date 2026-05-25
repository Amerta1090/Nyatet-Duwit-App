package com.nyatetduwit.presentation.debt

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.core.util.formatRupiah
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtDetailScreen(
    debtId: String,
    onNavigateBack: () -> Unit,
    viewModel: DebtViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var paymentAmount by remember { mutableStateOf("") }
    var showPaymentDialog by remember { mutableStateOf(false) }

    LaunchedEffect(debtId) { viewModel.loadDebtDetail(debtId) }

    val debt = uiState.selectedDebt

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(debt?.personName ?: "Detail") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                },
            )
        },
    ) { padding ->
        if (debt == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(NyatetDuwitSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.md),
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(NyatetDuwitRadius.md),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    ) {
                        Column(Modifier.padding(NyatetDuwitSpacing.lg)) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Total", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(formatRupiah(debt.amount), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }
                            Spacer(Modifier.height(NyatetDuwitSpacing.sm))
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Terbayar", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(formatRupiah(debt.paidAmount), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NyatetDuwitColor.green500)
                            }
                            Spacer(Modifier.height(NyatetDuwitSpacing.sm))
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Sisa", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(formatRupiah(debt.remainingAmount), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NyatetDuwitColor.red500)
                            }
                            if (debt.notes != null) {
                                Spacer(Modifier.height(NyatetDuwitSpacing.sm))
                                Text("Catatan: ${debt.notes}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }

                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("Riwayat Pembayaran", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        FilledTonalButton(onClick = { showPaymentDialog = true }) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(NyatetDuwitSpacing.xxs))
                            Text("Tambah")
                        }
                    }
                }

                if (uiState.payments.isEmpty()) {
                    item {
                        Text("Belum ada pembayaran", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                } else {
                    items(uiState.payments, key = { it.id }) { payment ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(NyatetDuwitRadius.md),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        ) {
                            Row(
                                Modifier.fillMaxWidth().padding(NyatetDuwitSpacing.md),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column {
                                    val sdf = SimpleDateFormat("d MMM yyyy", Locale("id"))
                                    Text(sdf.format(Date(payment.date)), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    if (payment.notes != null) {
                                        Text(payment.notes, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(formatRupiah(payment.amount), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = NyatetDuwitColor.green500)
                                    IconButton(onClick = { viewModel.deletePayment(payment, debtId) }) {
                                        Icon(Icons.Default.Delete, "Hapus", modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showPaymentDialog) {
        AlertDialog(
            onDismissRequest = { showPaymentDialog = false },
            title = { Text("Tambah Pembayaran") },
            text = {
                OutlinedTextField(
                    value = paymentAmount,
                    onValueChange = { paymentAmount = it },
                    label = { Text("Nominal") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val amount = paymentAmount.toLongOrNull()
                    if (amount != null && amount > 0) {
                        viewModel.addPayment(debtId, amount)
                        paymentAmount = ""
                        showPaymentDialog = false
                    }
                }) { Text("Tambah") }
            },
            dismissButton = {
                TextButton(onClick = { showPaymentDialog = false }) { Text("Batal") }
            },
        )
    }
}
