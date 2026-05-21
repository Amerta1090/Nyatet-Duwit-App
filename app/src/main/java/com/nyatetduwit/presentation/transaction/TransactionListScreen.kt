package com.nyatetduwit.presentation.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.presentation.account.formatRupiah
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddTransaction: () -> Unit,
    onTransactionClick: (String) -> Unit,
    onTransactionEdit: (String) -> Unit,
    viewModel: TransactionListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val showUndoSnackbar by viewModel.showUndoSnackbar.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val haptic = LocalHapticFeedback.current
    var showFilterSheet by remember { mutableStateOf(false) }
    var showSaveTemplateDialog by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(showUndoSnackbar) {
        if (showUndoSnackbar) {
            snackbarHostState.showSnackbar(
                message = "Transaksi dihapus",
                actionLabel = "Undo",
                duration = SnackbarDuration.Short,
            ).let { result ->
                if (result == SnackbarResult.ActionPerformed) {
                    viewModel.undoDelete()
                } else {
                    viewModel.dismissUndo()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Transaksi") },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { showFilterSheet = true },
                        ) {
                            Icon(
                                Icons.Default.FilterList,
                                contentDescription = "Filter",
                                tint = if (uiState.filterState.hasActiveFilters) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                },
                            )
                        }
                    },
                )

                SearchBar(
                    query = uiState.filterState.query,
                    onQueryChange = { viewModel.updateSearchQuery(it) },
                    onClear = { viewModel.updateSearchQuery("") },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )

                if (uiState.filterState.hasActiveFilters) {
                    ActiveFilterChips(
                        filterState = uiState.filterState,
                        accounts = uiState.accounts,
                        categories = uiState.categories,
                        onClearFilters = { viewModel.clearFilters() },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onNavigateToAddTransaction()
                },
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.transactions.isEmpty() -> {
                EmptyTransactionState(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    hasFilters = uiState.filterState.hasActiveFilters,
                    onClearFilters = { viewModel.clearFilters() },
                    onAddTransaction = onNavigateToAddTransaction,
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(bottom = 80.dp),
                ) {
                    uiState.transactions.forEach { group ->
                        item {
                            DateHeader(dateLabel = group.dateLabel)
                        }
                        items(group.transactions) { transaction ->
                            TransactionItem(
                                transaction = transaction,
                                onClick = { onTransactionClick(transaction.id) },
                                onEdit = { onTransactionEdit(transaction.id) },
                                onDelete = { viewModel.deleteTransaction(transaction.id) },
                                onSaveAsTemplate = { showSaveTemplateDialog = transaction.id },
                            )
                        }
                    }
                }
            }
        }
    }

    if (showFilterSheet) {
        FilterBottomSheet(
            filterState = uiState.filterState,
            accounts = uiState.accounts,
            categories = uiState.categories,
            onApplyFilter = { type, categoryId, accountId, startDate, endDate ->
                viewModel.applyFilter(type, categoryId, accountId, startDate, endDate)
                showFilterSheet = false
            },
            onClearFilters = {
                viewModel.clearFilters()
                showFilterSheet = false
            },
            onDismiss = { showFilterSheet = false },
        )
    }

    showSaveTemplateDialog?.let { transactionId ->
        SaveTemplateDialog(
            onDismiss = { showSaveTemplateDialog = null },
            onSave = { name ->
                viewModel.saveAsTemplate(transactionId, name)
                showSaveTemplateDialog = null
            },
        )
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Cari transaksi...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
    )
}

@Composable
fun ActiveFilterChips(
    filterState: TransactionFilterState,
    accounts: List<Account>,
    categories: List<Category>,
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (filterState.query.isNotBlank()) {
            item {
                FilterChip(
                    selected = true,
                    label = { Text("\"${filterState.query}\"") },
                    onClick = {},
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, Modifier.size(16.dp)) },
                )
            }
        }
        if (filterState.type != null) {
            item {
                FilterChip(
                    selected = true,
                    label = {
                        Text(
                            when (filterState.type) {
                                TransactionType.INCOME -> "Pemasukan"
                                TransactionType.EXPENSE -> "Pengeluaran"
                                TransactionType.TRANSFER -> "Transfer"
                            }
                        )
                    },
                    onClick = {},
                    leadingIcon = { Icon(Icons.Default.FilterList, contentDescription = null, Modifier.size(16.dp)) },
                )
            }
        }
        if (filterState.categoryId != null) {
            val category = categories.find { it.id == filterState.categoryId }
            item {
                FilterChip(
                    selected = true,
                    label = { Text(category?.name ?: "Kategori") },
                    onClick = {},
                    leadingIcon = { Text(category?.icon ?: "", style = MaterialTheme.typography.labelSmall) },
                )
            }
        }
        if (filterState.accountId != null) {
            val account = accounts.find { it.id == filterState.accountId }
            item {
                FilterChip(
                    selected = true,
                    label = { Text(account?.name ?: "Akun") },
                    onClick = {},
                    leadingIcon = { Icon(Icons.Default.AccountBalance, contentDescription = null, Modifier.size(16.dp)) },
                )
            }
        }
        if (filterState.startDate != null || filterState.endDate != null) {
            item {
                FilterChip(
                    selected = true,
                    label = { Text("Tanggal") },
                    onClick = {},
                    leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, Modifier.size(16.dp)) },
                )
            }
        }
        item {
            TextButton(
                onClick = onClearFilters,
                contentPadding = PaddingValues(horizontal = 8.dp),
            ) {
                Text("Hapus", fontSize = MaterialTheme.typography.labelLarge.fontSize)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    filterState: TransactionFilterState,
    accounts: List<Account>,
    categories: List<Category>,
    onApplyFilter: (
        type: TransactionType?,
        categoryId: String?,
        accountId: String?,
        startDate: Long?,
        endDate: Long?,
    ) -> Unit,
    onClearFilters: () -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedType by remember { mutableStateOf(filterState.type) }
    var selectedCategoryId by remember { mutableStateOf(filterState.categoryId) }
    var selectedAccountId by remember { mutableStateOf(filterState.accountId) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "Filter Transaksi",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            Text(
                text = "Tipe",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                listOf(
                    Triple(TransactionType.EXPENSE, "Pengeluaran", "📉"),
                    Triple(TransactionType.INCOME, "Pemasukan", "📈"),
                    Triple(TransactionType.TRANSFER, "Transfer", "🔄"),
                ).forEach { (type, label, icon) ->
                    val isSelected = type == selectedType
                    FilterTypeChip(
                        label = label,
                        icon = icon,
                        isSelected = isSelected,
                        onClick = { selectedType = if (isSelected) null else type },
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Kategori",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            FilterDropdown(
                selectedValue = selectedCategoryId,
                items = categories.map { it.id to it.name },
                onItemSelected = { selectedCategoryId = it },
                placeholder = "Semua Kategori",
                getItemLabel = { id -> categories.find { it.id == id }?.name ?: "Pilih" },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Akun",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            FilterDropdown(
                selectedValue = selectedAccountId,
                items = accounts.map { it.id to it.name },
                onItemSelected = { selectedAccountId = it },
                placeholder = "Semua Akun",
                getItemLabel = { id -> accounts.find { it.id == id }?.name ?: "Pilih" },
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedButton(
                    onClick = onClearFilters,
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Reset")
                }
                Button(
                    onClick = {
                        onApplyFilter(selectedType, selectedCategoryId, selectedAccountId, null, null)
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Terapkan")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FilterTypeChip(
    label: String,
    icon: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else {
            androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        },
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = icon, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> FilterDropdown(
    selectedValue: T?,
    items: List<Pair<T, String>>,
    onItemSelected: (T?) -> Unit,
    placeholder: String,
    getItemLabel: (T) -> String,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            value = selectedValue?.let { getItemLabel(it) } ?: placeholder,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text(placeholder) },
                onClick = {
                    onItemSelected(null)
                    expanded = false
                },
            )
            items.forEach { (id, name) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        onItemSelected(id)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
fun SaveTemplateDialog(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
) {
    var templateName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Simpan sebagai Template") },
        text = {
            Column {
                Text(
                    text = "Beri nama untuk template ini:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                OutlinedTextField(
                    value = templateName,
                    onValueChange = { templateName = it },
                    placeholder = { Text("Contoh: Kopi Pagi") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { if (templateName.isNotBlank()) onSave(templateName) },
                enabled = templateName.isNotBlank(),
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        },
    )
}

@Composable
fun DateHeader(
    dateLabel: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
    ) {
        Text(
            text = dateLabel,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onSaveAsTemplate: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = when (transaction.type) {
                        TransactionType.INCOME -> "📈"
                        TransactionType.EXPENSE -> "📉"
                        TransactionType.TRANSFER -> "🔄"
                    },
                    style = MaterialTheme.typography.titleMedium,
                )

                Column {
                    Text(
                        text = transaction.notes ?: getCategoryName(transaction),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = formatTime(transaction.dateTime),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Text(
                text = formatTransactionAmount(transaction),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = when (transaction.type) {
                    TransactionType.INCOME -> MaterialTheme.colorScheme.primary
                    TransactionType.EXPENSE -> MaterialTheme.colorScheme.error
                    TransactionType.TRANSFER -> MaterialTheme.colorScheme.onSurface
                },
            )

            IconButton(
                onClick = { showMenu = true },
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "Options")
            }
        }
    }

    if (showMenu) {
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = {
                    showMenu = false
                    onEdit()
                },
                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
            )
            DropdownMenuItem(
                text = { Text("Simpan sebagai Template") },
                onClick = {
                    showMenu = false
                    onSaveAsTemplate()
                },
                leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) },
            )
            DropdownMenuItem(
                text = { Text("Hapus") },
                onClick = {
                    showMenu = false
                    onDelete()
                },
                leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null) },
            )
        }
    }
}

private fun getCategoryName(transaction: Transaction): String {
    return when (transaction.type) {
        TransactionType.INCOME -> "Pemasukan"
        TransactionType.EXPENSE -> "Pengeluaran"
        TransactionType.TRANSFER -> "Transfer"
    }
}

private fun formatTime(dateTime: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale("id"))
    return sdf.format(Date(dateTime))
}

private fun formatTransactionAmount(transaction: Transaction): String {
    return when (transaction.type) {
        TransactionType.INCOME -> "+${formatRupiah(transaction.amount)}"
        TransactionType.EXPENSE -> "-${formatRupiah(transaction.amount)}"
        TransactionType.TRANSFER -> formatRupiah(transaction.amount)
    }
}

@Composable
fun EmptyTransactionState(
    modifier: Modifier = Modifier,
    hasFilters: Boolean = false,
    onClearFilters: () -> Unit = {},
    onAddTransaction: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = if (hasFilters) "🔍" else "💸",
            style = MaterialTheme.typography.displayLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (hasFilters) "Tidak ada hasil" else "Belum ada transaksi",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (hasFilters)
                "Coba ubah filter atau kata kunci pencarian"
            else
                "Yuk mulai nyatet! Tap tombol + di bawah untuk transaksi pertamamu 🚀",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(24.dp))
        if (hasFilters) {
            Button(onClick = onClearFilters) {
                Icon(Icons.Default.Close, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Hapus Filter")
            }
        } else {
            Button(onClick = onAddTransaction) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tambah Transaksi")
            }
        }
    }
}
