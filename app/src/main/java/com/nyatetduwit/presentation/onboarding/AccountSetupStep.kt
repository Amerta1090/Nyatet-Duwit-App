package com.nyatetduwit.presentation.onboarding

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.AccountType
import com.nyatetduwit.presentation.account.AccountViewModel
import com.nyatetduwit.presentation.account.getAccountTypeIcon
import com.nyatetduwit.presentation.account.getAccountTypeLabel
import com.nyatetduwit.presentation.category.CategoryViewModel

private fun getDefaultIcon(type: AccountType) = when (type) {
    AccountType.CASH -> "payments"
    AccountType.BANK -> "account_balance"
    AccountType.E_WALLET -> "phone_android"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSetupStep(
    onComplete: () -> Unit,
    onBack: () -> Unit,
    accountViewModel: AccountViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    var name by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(AccountType.CASH) }
    var balance by remember { mutableStateOf("0") }

    LaunchedEffect(Unit) {
        categoryViewModel.seedDefaultCategories()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Setup Akun Pertama") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Default.AccountBalanceWallet,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tambah akun pertama kamu",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ini akan jadi akun utama untuk tracking keuangan sehari-hari",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama Akun") },
                placeholder = { Text("Contoh: Dompet, BCA, OVO") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Tipe Akun", style = MaterialTheme.typography.labelLarge, modifier = Modifier.align(Alignment.Start))

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AccountType.entries.forEach { type ->
                    FilterChip(
                        selected = type == selectedType,
                        onClick = { selectedType = type },
                        label = { Text(getAccountTypeLabel(type)) },
                        leadingIcon = {
                            Icon(
                                imageVector = getAccountTypeIcon(type),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                            )
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = balance,
                onValueChange = { value ->
                    if (value.isEmpty() || value.toLongOrNull() != null) {
                        balance = value
                    }
                },
                label = { Text("Saldo Awal (opsional)") },
                placeholder = { Text("0") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Payments, null) },
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        accountViewModel.onNameChange(name.trim())
                        accountViewModel.onTypeChange(selectedType)
                        accountViewModel.onBalanceChange(balance.toLongOrNull() ?: 0L)
                        accountViewModel.onIconChange(getDefaultIcon(selectedType))
                        accountViewModel.onColorChange("#4F6B4E")
                        accountViewModel.saveAccount()
                        onComplete()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank(),
            ) {
                Icon(Icons.Default.Check, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mulai Nyatet!")
            }
        }
    }
}
