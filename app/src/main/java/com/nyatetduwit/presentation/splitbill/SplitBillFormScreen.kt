package com.nyatetduwit.presentation.splitbill

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.util.formatRupiah

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitBillFormScreen(
    billId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: SplitBillViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val form = uiState.formState

    LaunchedEffect(billId) { viewModel.loadBill(billId) }
    LaunchedEffect(uiState.isSuccess) { if (uiState.isSuccess) onNavigateBack() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (billId == null) "Buat Split Bill" else "Edit Split Bill") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                value = form.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text("Judul") },
                placeholder = { Text("Contoh: Makan bersama") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            OutlinedTextField(
                value = if (form.totalAmount == 0L) "" else form.totalAmount.toString(),
                onValueChange = { viewModel.onTotalAmountChange(it.toLongOrNull() ?: 0L) },
                label = { Text("Total") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Payments, null) },
            )

            OutlinedTextField(
                value = form.paidBy,
                onValueChange = viewModel::onPaidByChange,
                label = { Text("Dibayar oleh") },
                placeholder = { Text("Nama kamu") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Person, null) },
            )

            OutlinedTextField(
                value = form.notes ?: "",
                onValueChange = viewModel::onNotesChange,
                label = { Text("Catatan (opsional)") },
                modifier = Modifier.fillMaxWidth(),
            )

            HorizontalDivider()
            Text("Teman yang ikut", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

            form.persons.forEachIndexed { index, person ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    OutlinedTextField(
                        value = person.name,
                        onValueChange = { viewModel.onPersonNameChange(index, it) },
                        label = { Text("Nama") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                    )
                    OutlinedTextField(
                        value = if (person.amount == 0L) "" else person.amount.toString(),
                        onValueChange = { viewModel.onPersonAmountChange(index, it.toLongOrNull() ?: 0L) },
                        label = { Text("Bagian") },
                        modifier = Modifier.weight(0.8f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )
                    if (form.persons.size > 1) {
                        IconButton(onClick = { viewModel.removePerson(index) }) {
                            Icon(Icons.Default.Close, "Hapus", tint = NyatetDuwitColor.coral500)
                        }
                    }
                }
            }

            TextButton(onClick = viewModel::addPerson) {
                Icon(Icons.Default.PersonAdd, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(4.dp))
                Text("Tambah teman")
            }

            if (form.persons.any { it.name.isNotBlank() } && form.totalAmount > 0) {
                val totalPersonAmount = form.persons.filter { it.name.isNotBlank() }.sumOf { it.amount }
                val remainder = form.totalAmount - totalPersonAmount
                Text(
                    text = "Sisa untuk kamu: ${formatRupiah(remainder)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (remainder <= 0) NyatetDuwitColor.teal500 else NyatetDuwitColor.coral500,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Button(
                onClick = viewModel::saveBill,
                modifier = Modifier.fillMaxWidth(),
                enabled = form.title.isNotBlank() && form.totalAmount > 0 && form.paidBy.isNotBlank(),
            ) {
                Icon(Icons.Default.Save, null)
                Spacer(Modifier.width(8.dp))
                Text(if (billId == null) "Simpan" else "Update")
            }
        }
    }
}
