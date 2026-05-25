package com.nyatetduwit.presentation.goal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalFormScreen(
    goalId: String?,
    onNavigateBack: () -> Unit,
    viewModel: GoalViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(goalId) { viewModel.loadGoal(goalId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (goalId != null && goalId != "null") "Edit Target" else "Target Baru") },
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
            OutlinedTextField(
                value = uiState.formState.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nama Target") },
                placeholder = { Text("Contoh: Liburan ke Jepang") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            OutlinedTextField(
                value = uiState.formState.targetAmount,
                onValueChange = viewModel::onTargetAmountChange,
                label = { Text("Nominal Target") },
                placeholder = { Text("Rp") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )

            OutlinedTextField(
                value = uiState.formState.currentAmount,
                onValueChange = viewModel::onCurrentAmountChange,
                label = { Text("Sudah Terkumpul") },
                placeholder = { Text("Rp") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )

            Spacer(Modifier.height(NyatetDuwitSpacing.sm))

            Button(
                onClick = { viewModel.saveGoal(onSuccess = onNavigateBack) },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.formState.name.isNotBlank() && uiState.formState.targetAmount.toLongOrNull() != null,
            ) {
                Text(if (goalId != null && goalId != "null") "Simpan" else "Buat Target")
            }
        }
    }

    uiState.error?.let { error ->
        AlertDialog(
            onDismissRequest = viewModel::clearError,
            title = { Text("Error") },
            text = { Text(error) },
            confirmButton = {
                TextButton(onClick = viewModel::clearError) { Text("OK") }
            },
        )
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) { viewModel.clearSuccess(); onNavigateBack() }
    }
}
