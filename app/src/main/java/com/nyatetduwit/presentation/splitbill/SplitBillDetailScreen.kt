package com.nyatetduwit.presentation.splitbill

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.util.formatRupiah
import com.nyatetduwit.domain.model.SplitBill
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitBillDetailScreen(
    billId: String,
    onNavigateBack: () -> Unit,
    onEditBill: (String) -> Unit,
    viewModel: SplitBillViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val bill = uiState.bills.find { it.id == billId }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Split Bill") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                },
                actions = {
                    if (bill != null) {
                        IconButton(onClick = { onEditBill(bill.id) }) {
                            Icon(Icons.Default.Edit, "Edit")
                        }
                    }
                },
            )
        },
    ) { padding ->
        if (bill == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(bill.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = formatRupiah(bill.totalAmount),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Dibayar oleh ${bill.paidBy}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                text = SimpleDateFormat("d MMM yyyy", Locale("id")).format(Date(bill.date)),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            if (!bill.notes.isNullOrBlank()) {
                                Spacer(Modifier.height(8.dp))
                                Text(bill.notes, style = MaterialTheme.typography.bodyMedium)
                            }
                            if (!bill.isSettled) {
                                Spacer(Modifier.height(12.dp))
                                Button(
                                    onClick = { viewModel.markSettled(bill.id) },
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Icon(Icons.Default.CheckCircle, null)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Tandai Lunas Semua")
                                }
                            } else {
                                Spacer(Modifier.height(8.dp))
                                Surface(shape = RoundedCornerShape(8.dp), color = NyatetDuwitColor.teal100) {
                                    Text(
                                        text = "Semua sudah lunas",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = NyatetDuwitColor.teal500,
                                        modifier = Modifier.padding(12.dp),
                                    )
                                }
                            }
                        }
                    }
                }

                if (bill.persons.isNotEmpty()) {
                    item {
                        Text("Pembagian", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    items(bill.persons) { person ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(person.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                                    Text(
                                        text = formatRupiah(person.amount),
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                                if (person.isPaid) {
                                    Icon(Icons.Default.CheckCircle, "Lunas", tint = NyatetDuwitColor.teal500)
                                } else if (!bill.isSettled) {
                                    FilledTonalButton(
                                        onClick = { viewModel.markPersonPaid(person.id) },
                                    ) {
                                        Text("Bayar")
                                    }
                                }
                            }
                        }
                    }
                }

                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }
}
