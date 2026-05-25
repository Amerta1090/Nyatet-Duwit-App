package com.nyatetduwit.presentation.investment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.InvestmentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentFormScreen(
    investmentId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: InvestmentViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val form = uiState.formState

    LaunchedEffect(investmentId) { viewModel.loadInvestment(investmentId) }
    LaunchedEffect(uiState.isSuccess) { if (uiState.isSuccess) onNavigateBack() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (investmentId == null) "Tambah Investasi" else "Edit Investasi") },
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
                value = form.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nama Investasi") },
                placeholder = { Text("Contoh: Saham BBCA") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Text("Tipe Investasi", style = MaterialTheme.typography.labelLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                InvestmentType.entries.take(4).forEach { type ->
                    FilterChip(
                        selected = type == form.type,
                        onClick = { viewModel.onTypeChange(type) },
                        label = { Text(type.name.replace("_", " ")) },
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                InvestmentType.entries.drop(4).forEach { type ->
                    FilterChip(
                        selected = type == form.type,
                        onClick = { viewModel.onTypeChange(type) },
                        label = { Text(type.name.replace("_", " ")) },
                    )
                }
            }

            OutlinedTextField(
                value = if (form.currentValue == 0L) "" else form.currentValue.toString(),
                onValueChange = { viewModel.onCurrentValueChange(it.toLongOrNull() ?: 0L) },
                label = { Text("Nilai Saat Ini") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.TrendingUp, null) },
            )

            OutlinedTextField(
                value = if (form.costBasis == 0L) "" else form.costBasis.toString(),
                onValueChange = { viewModel.onCostBasisChange(it.toLongOrNull() ?: 0L) },
                label = { Text("Modal Awal") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Money, null) },
            )

            OutlinedTextField(
                value = form.notes ?: "",
                onValueChange = viewModel::onNotesChange,
                label = { Text("Catatan (opsional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
            )

            Button(
                onClick = viewModel::saveInvestment,
                modifier = Modifier.fillMaxWidth(),
                enabled = form.name.isNotBlank(),
            ) {
                Icon(Icons.Default.Save, null)
                Spacer(Modifier.width(8.dp))
                Text(if (investmentId == null) "Simpan" else "Update")
            }
        }
    }
}
