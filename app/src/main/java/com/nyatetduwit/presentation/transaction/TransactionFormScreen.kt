package com.nyatetduwit.presentation.transaction

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.presentation.components.CustomNumpad
import com.nyatetduwit.presentation.account.formatRupiah

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionFormScreen(
    transactionId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: TransactionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val formState = uiState.formState

    LaunchedEffect(transactionId) {
        transactionId?.let { viewModel.loadTransactionForEdit(it) }
    }

    LaunchedEffect(formState.isSuccess) {
        if (formState.isSuccess) {
            onNavigateBack()
            viewModel.clearSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (formState.type) {
                            TransactionType.INCOME -> "Pemasukan"
                            TransactionType.EXPENSE -> "Pengeluaran"
                            TransactionType.TRANSFER -> "Transfer"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("←")
                    }
                },
                actions = {
                    if (formState.amount > 0) {
                        TextButton(
                            onClick = { viewModel.saveTransaction(transactionId) },
                            enabled = !formState.isLoading,
                        ) {
                            Text("Simpan")
                        }
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        ) {
            TransactionTypeSelector(
                selectedType = formState.type,
                onTypeSelected = { viewModel.setType(it) },
            )

            Spacer(modifier = Modifier.height(16.dp))

            AmountDisplay(
                amount = formState.amount,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomNumpad(
                onNumberClick = { viewModel.appendToAmount(it) },
                onBackspaceClick = { viewModel.backspaceAmount() },
                onPresetClick = { viewModel.setAmount(it) },
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (formState.type != TransactionType.TRANSFER) {
                CategoryPicker(
                    categories = uiState.categories,
                    selectedCategoryId = formState.categoryId,
                    onCategorySelected = { viewModel.setCategoryId(it) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            AccountPickerSection(
                accounts = uiState.accounts,
                selectedAccountId = formState.accountId,
                selectedToAccountId = formState.toAccountId,
                transactionType = formState.type,
                onAccountSelected = { viewModel.setAccountId(it) },
                onToAccountSelected = { viewModel.setToAccountId(it) },
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            NotesField(
                notes = formState.notes,
                onNotesChanged = { viewModel.setNotes(it) },
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    if (formState.isError) {
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Error") },
            text = { Text(formState.errorMessage ?: "Terjadi kesalahan") },
            confirmButton = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text("OK")
                }
            },
        )
    }
}

@Composable
fun TransactionTypeSelector(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current

    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val types = listOf(
            Triple(TransactionType.EXPENSE, "Pengeluaran", Icons.Default.ArrowDownward),
            Triple(TransactionType.INCOME, "Pemasukan", Icons.Default.ArrowUpward),
            Triple(TransactionType.TRANSFER, "Transfer", Icons.Default.SwapHoriz),
        )
        types.forEach { (type, label, icon) ->
            val isSelected = type == selectedType
            Card(
                modifier = Modifier.weight(1f).clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onTypeSelected(type)
                },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                ),
                border = if (isSelected) {
                    BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                } else {
                    BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                },
            ) {
                Column(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    )
                }
            }
        }
    }
}

@Composable
fun AmountDisplay(
    amount: Long,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Box(
            modifier = Modifier.padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = formatRupiah(amount),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Composable
fun CategoryPicker(
    categories: List<Category>,
    selectedCategoryId: String?,
    onCategorySelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedCategory = categories.find { it.id == selectedCategoryId }

    OutlinedCard(
        modifier = modifier.fillMaxWidth().clickable { expanded = true },
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = null,
                )
                Text(
                    text = selectedCategory?.name ?: "Pilih Kategori",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Text("▼")
        }
    }

    if (expanded) {
        AlertDialog(
            onDismissRequest = { expanded = false },
            title = { Text("Pilih Kategori") },
            text = {
                LazyColumn {
                    items(categories.size) { index ->
                        val category = categories[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onCategorySelected(category.id)
                                    expanded = false
                                }
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(category.icon)
                            Text(category.name)
                        }
                    }
                }
            },
            confirmButton = {},
        )
    }
}

@Composable
fun AccountPickerSection(
    accounts: List<Account>,
    selectedAccountId: String,
    selectedToAccountId: String,
    transactionType: TransactionType,
    onAccountSelected: (String) -> Unit,
    onToAccountSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        AccountPicker(
            label = if (transactionType == TransactionType.TRANSFER) "Dari Akun" else "Akun",
            accounts = accounts,
            selectedAccountId = selectedAccountId,
            onAccountSelected = onAccountSelected,
        )

        if (transactionType == TransactionType.TRANSFER) {
            Spacer(modifier = Modifier.height(12.dp))

            AccountPicker(
                label = "Ke Akun",
                accounts = accounts,
                selectedAccountId = selectedToAccountId,
                onAccountSelected = onToAccountSelected,
            )
        }
    }
}

@Composable
fun AccountPicker(
    label: String,
    accounts: List<Account>,
    selectedAccountId: String,
    onAccountSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedAccount = accounts.find { it.id == selectedAccountId }

    OutlinedCard(
        modifier = Modifier.fillMaxWidth().clickable { expanded = true },
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = selectedAccount?.name ?: "Pilih Akun",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Text("▼")
        }
    }

    if (expanded) {
        AlertDialog(
            onDismissRequest = { expanded = false },
            title = { Text("Pilih Akun") },
            text = {
                LazyColumn {
                    items(accounts.size) { index ->
                        val account = accounts[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onAccountSelected(account.id)
                                    expanded = false
                                }
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(account.name)
                            Text(formatRupiah(account.balance))
                        }
                    }
                }
            },
            confirmButton = {},
        )
    }
}

@Composable
fun NotesField(
    notes: String?,
    onNotesChanged: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = notes ?: "",
        onValueChange = { onNotesChanged(it) },
        label = { Text("Catatan (opsional)") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Description, contentDescription = null)
        },
        modifier = modifier.fillMaxWidth(),
        maxLines = 3,
    )
}
