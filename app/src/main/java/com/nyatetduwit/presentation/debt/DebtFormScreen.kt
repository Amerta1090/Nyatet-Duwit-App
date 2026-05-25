package com.nyatetduwit.presentation.debt

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.domain.model.DebtType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtFormScreen(
    debtId: String?,
    onNavigateBack: () -> Unit,
    viewModel: DebtViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(debtId) { viewModel.loadDebt(debtId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (debtId != null && debtId != "null") "Edit" else "Catat Baru") },
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
                .padding(NyatetDuwitSpacing.lg)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.lg),
        ) {
            Text("Tipe", style = MaterialTheme.typography.titleSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm)) {
                FilterChip(
                    selected = uiState.formState.type == DebtType.OWE,
                    onClick = { viewModel.onTypeChange(DebtType.OWE) },
                    label = { Text("Utang (saya berutang)") },
                )
                FilterChip(
                    selected = uiState.formState.type == DebtType.LENT,
                    onClick = { viewModel.onTypeChange(DebtType.LENT) },
                    label = { Text("Piutang (saya memberi utang)") },
                )
            }

            OutlinedTextField(
                value = uiState.formState.personName,
                onValueChange = viewModel::onPersonNameChange,
                label = { Text("Nama Orang") },
                placeholder = { Text("Contoh: Budi") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            OutlinedTextField(
                value = uiState.formState.amount,
                onValueChange = viewModel::onAmountChange,
                label = { Text("Nominal") },
                placeholder = { Text("Rp") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )

            OutlinedTextField(
                value = uiState.formState.notes,
                onValueChange = viewModel::onNotesChange,
                label = { Text("Catatan (opsional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(Modifier.height(NyatetDuwitSpacing.sm))

            Button(
                onClick = { viewModel.saveDebt(onSuccess = onNavigateBack) },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.formState.personName.isNotBlank() && uiState.formState.amount.toLongOrNull() != null,
            ) {
                Text("Simpan")
            }
        }
    }

    uiState.error?.let { error ->
        AlertDialog(
            onDismissRequest = viewModel::clearError,
            title = { Text("Error") },
            text = { Text(error) },
            confirmButton = { TextButton(onClick = viewModel::clearError) { Text("OK") } },
        )
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) { viewModel.clearSuccess(); onNavigateBack() }
    }
}
