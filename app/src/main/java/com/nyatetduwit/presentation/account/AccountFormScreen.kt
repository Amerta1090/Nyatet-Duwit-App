package com.nyatetduwit.presentation.account

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.AccountType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountFormScreen(
    accountId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: AccountViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val formState = uiState.formState

    LaunchedEffect(accountId) {
        viewModel.loadAccount(accountId)
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (accountId == null) "Tambah Akun" else "Edit Akun") },
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
            OutlinedTextField(
                value = formState.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nama Akun") },
                placeholder = { Text("Contoh: BCA, OVO, Dompet") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Text("Tipe Akun", style = MaterialTheme.typography.labelLarge)
            AccountTypeSelector(
                selectedType = formState.type,
                onTypeSelected = viewModel::onTypeChange,
            )

            OutlinedTextField(
                value = if (formState.balance == 0L) "" else formState.balance.toString(),
                onValueChange = { value ->
                    val balance = value.toLongOrNull() ?: 0L
                    viewModel.onBalanceChange(balance)
                },
                label = { Text("Saldo Awal") },
                placeholder = { Text("0") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Payments, null) },
            )

            Text("Icon", style = MaterialTheme.typography.labelLarge)
            IconSelector(
                selectedIcon = formState.icon,
                onIconSelected = viewModel::onIconChange,
            )

            Text("Warna", style = MaterialTheme.typography.labelLarge)
            ColorSelector(
                selectedColor = formState.color,
                onColorSelected = viewModel::onColorChange,
            )

            Text("Mata Uang", style = MaterialTheme.typography.labelLarge)
            CurrencySelector(
                selectedCurrency = formState.currencyCode,
                onCurrencySelected = viewModel::onCurrencyChange,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Sembunyikan akun", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = formState.isHidden,
                    onCheckedChange = viewModel::onHiddenChange,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = viewModel::saveAccount,
                modifier = Modifier.fillMaxWidth(),
                enabled = formState.name.isNotBlank(),
            ) {
                Icon(Icons.Default.Save, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (accountId == null) "Simpan" else "Update")
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
private fun CurrencySelector(
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
) {
    val currencies = listOf(
        "IDR" to "Rp",
        "USD" to "$",
        "SGD" to "S$",
        "MYR" to "RM",
        "JPY" to "¥",
        "EUR" to "€",
        "GBP" to "£",
        "CNY" to "¥",
        "KRW" to "₩",
        "THB" to "฿",
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        currencies.forEach { (code, symbol) ->
            FilterChip(
                selected = code == selectedCurrency,
                onClick = { onCurrencySelected(code) },
                label = { Text("$code ($symbol)") },
            )
        }
    }
}

@Composable
private fun AccountTypeSelector(
    selectedType: AccountType,
    onTypeSelected: (AccountType) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AccountType.entries.forEach { type ->
            FilterChip(
                selected = type == selectedType,
                onClick = { onTypeSelected(type) },
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
}

@Composable
private fun IconSelector(
    selectedIcon: String,
    onIconSelected: (String) -> Unit,
) {
    val icons = listOf(
        "account_balance_wallet" to Icons.Default.AccountBalanceWallet,
        "account_balance" to Icons.Default.AccountBalance,
        "payments" to Icons.Default.Payments,
        "credit_card" to Icons.Default.CreditCard,
        "savings" to Icons.Default.Savings,
        "phone_android" to Icons.Default.PhoneAndroid,
        "lock" to Icons.Default.Lock,
        "store" to Icons.Default.Store,
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        icons.forEach { (name, icon) ->
            IconButton(
                onClick = { onIconSelected(name) },
                modifier = Modifier
                    .size(48.dp)
                    .wrapContentSize(),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = name,
                    tint = if (name == selectedIcon)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun ColorSelector(
    selectedColor: String,
    onColorSelected: (String) -> Unit,
) {
    val colors = listOf(
        "#4F6B4E", "#42A5F5", "#FF7043", "#AB47BC",
        "#66BB6A", "#FFA726", "#EC407A", "#78909C",
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        colors.forEach { color ->
            val colorObj = Color(color.substring(1).toLong(16) or 0xFF000000L)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .then(
                        if (color == selectedColor) {
                            Modifier
                        } else {
                            Modifier
                        }
                    ),
            ) {
                Surface(
                    modifier = Modifier.size(36.dp),
                    color = colorObj,
                    shape = MaterialTheme.shapes.small,
                    tonalElevation = if (color == selectedColor) 4.dp else 0.dp,
                ) {}
                if (color == selectedColor) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .padding(4.dp),
                        tint = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}
