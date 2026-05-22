package com.nyatetduwit.presentation.transaction

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.core.util.formatRupiah
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.Transaction
import com.nyatetduwit.domain.model.TransactionType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
                    title = {
                        Text(
                            text = "Transaksi",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Kembali",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { showFilterSheet = true }) {
                            Icon(
                                Icons.Default.FilterList,
                                contentDescription = "Filter",
                                tint = if (uiState.filterState.hasActiveFilters) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                )

                SearchField(
                    query = uiState.filterState.query,
                    onQueryChange = { viewModel.updateSearchQuery(it) },
                    onClear = { viewModel.updateSearchQuery("") },
                    modifier = Modifier.padding(horizontal = NyatetDuwitSpacing.lg, vertical = NyatetDuwitSpacing.sm),
                )

                if (uiState.filterState.hasActiveFilters) {
                    ActiveFilterChips(
                        filterState = uiState.filterState,
                        accounts = uiState.accounts,
                        categories = uiState.categories,
                        onClearFilters = { viewModel.clearFilters() },
                        modifier = Modifier.padding(horizontal = NyatetDuwitSpacing.lg, vertical = NyatetDuwitSpacing.xxs),
                    )
                }
            }
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(NyatetDuwitRadius.lg))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                    ) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onNavigateToAddTransaction()
                    }
                    .padding(NyatetDuwitSpacing.lg),
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Tambah transaksi",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Memuat...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            uiState.transactions.isEmpty() -> {
                EmptyTransactionState(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    hasFilters = uiState.filterState.hasActiveFilters,
                    onClearFilters = { viewModel.clearFilters() },
                    onAddTransaction = onNavigateToAddTransaction,
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(bottom = 88.dp),
                ) {
                    uiState.transactions.forEach { group ->
                        item(key = "header_${group.dateLabel}") {
                            DateSectionHeader(dateLabel = group.dateLabel)
                        }
                        items(
                            items = group.transactions,
                            key = { it.id },
                        ) { transaction ->
                            val dismissState = rememberSwipeToDismissBoxState(
                                confirmValueChange = { value ->
                                    if (value == SwipeToDismissBoxValue.EndToStart) {
                                        viewModel.deleteTransaction(transaction.id)
                                        true
                                    } else false
                                },
                            )

                            SwipeToDismissBox(
                                state = dismissState,
                                backgroundContent = {
                                    val color by animateColorAsState(
                                        targetValue = NyatetDuwitColor.red500,
                                        animationSpec = spring(),
                                        label = "swipeBg",
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(NyatetDuwitRadius.md))
                                            .background(color)
                                            .padding(end = NyatetDuwitSpacing.xl),
                                        contentAlignment = Alignment.CenterEnd,
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Hapus",
                                            tint = Color.White,
                                        )
                                    }
                                },
                                enableDismissFromStartToEnd = false,
                            ) {
                                TransactionCard(
                                    transaction = transaction,
                                    onClick = { onTransactionClick(transaction.id) },
                                    onEdit = { onTransactionEdit(transaction.id) },
                                    onSaveAsTemplate = { showSaveTemplateDialog = transaction.id },
                                )
                            }

                            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
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
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(NyatetDuwitRadius.md))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(horizontal = NyatetDuwitSpacing.md),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(NyatetDuwitSpacing.sm))
            androidx.compose.foundation.text.BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (query.isEmpty()) {
                            Text(
                                text = "Cari transaksi...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        innerTextField()
                    }
                },
            )
            if (query.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Hapus",
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }
    }
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
        horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
    ) {
        if (filterState.query.isNotBlank()) {
            item {
                ActiveChip(
                    label = "\"${filterState.query}\"",
                    icon = "🔍",
                )
            }
        }
        if (filterState.type != null) {
            item {
                val label = when (filterState.type) {
                    TransactionType.INCOME -> "Pemasukan"
                    TransactionType.EXPENSE -> "Pengeluaran"
                    TransactionType.TRANSFER -> "Transfer"
                }
                ActiveChip(label = label, icon = "📊")
            }
        }
        if (filterState.categoryId != null) {
            val category = categories.find { it.id == filterState.categoryId }
            item {
                ActiveChip(
                    label = category?.name ?: "Kategori",
                    icon = category?.icon ?: "📁",
                )
            }
        }
        if (filterState.accountId != null) {
            val account = accounts.find { it.id == filterState.accountId }
            item {
                ActiveChip(
                    label = account?.name ?: "Akun",
                    icon = "💳",
                )
            }
        }
        item {
            TextButton(
                onClick = onClearFilters,
                contentPadding = PaddingValues(horizontal = NyatetDuwitSpacing.sm),
            ) {
                Text(
                    text = "Hapus",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
fun ActiveChip(
    label: String,
    icon: String,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(NyatetDuwitRadius.full))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = NyatetDuwitSpacing.md, vertical = NyatetDuwitSpacing.sm),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.xxs),
        ) {
            Text(text = icon, style = MaterialTheme.typography.labelSmall)
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
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
        shape = RoundedCornerShape(NyatetDuwitRadius.xl),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = NyatetDuwitSpacing.lg)
                .padding(bottom = NyatetDuwitSpacing.xxxl),
        ) {
            Text(
                text = "Filter Transaksi",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = NyatetDuwitSpacing.xl),
            )

            Text(
                text = "Tipe",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = NyatetDuwitSpacing.sm),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
            ) {
                listOf(
                    TransactionType.EXPENSE to "Pengeluaran",
                    TransactionType.INCOME to "Pemasukan",
                    TransactionType.TRANSFER to "Transfer",
                ).forEach { (type, label) ->
                    val isSelected = type == selectedType
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(NyatetDuwitRadius.md))
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(),
                            ) { selectedType = if (isSelected) null else type }
                            .padding(vertical = NyatetDuwitSpacing.md),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xl))

            Text(
                text = "Kategori",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = NyatetDuwitSpacing.sm),
            )
            FilterDropdownField(
                selectedValue = selectedCategoryId,
                items = categories.map { it.id to it.name },
                onItemSelected = { selectedCategoryId = it },
                placeholder = "Semua Kategori",
                getItemLabel = { id -> categories.find { it.id == id }?.name ?: "" },
            )

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.lg))

            Text(
                text = "Akun",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = NyatetDuwitSpacing.sm),
            )
            FilterDropdownField(
                selectedValue = selectedAccountId,
                items = accounts.map { it.id to it.name },
                onItemSelected = { selectedAccountId = it },
                placeholder = "Semua Akun",
                getItemLabel = { id -> accounts.find { it.id == id }?.name ?: "" },
            )

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xxl))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
            ) {
                OutlinedButton(
                    onClick = onClearFilters,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(NyatetDuwitRadius.md),
                ) {
                    Text("Reset")
                }
                Button(
                    onClick = {
                        onApplyFilter(selectedType, selectedCategoryId, selectedAccountId, null, null)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(NyatetDuwitRadius.md),
                ) {
                    Text("Terapkan")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> FilterDropdownField(
    selectedValue: T?,
    items: List<Pair<T, String>>,
    onItemSelected: (T?) -> Unit,
    placeholder: String,
    getItemLabel: (T) -> String,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(NyatetDuwitRadius.md))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .clickable { expanded = true }
                .padding(NyatetDuwitSpacing.md),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = selectedValue?.let { getItemLabel(it) } ?: placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (selectedValue != null) MaterialTheme.colorScheme.onSurface
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "▼",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        if (expanded) {
            AlertDialog(
                onDismissRequest = { expanded = false },
                title = {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                text = {
                    LazyColumn {
                        item {
                            TextButton(
                                onClick = {
                                    onItemSelected(null)
                                    expanded = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = placeholder,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                        items(items.size) { index ->
                            val (id, name) = items[index]
                            TextButton(
                                onClick = {
                                    onItemSelected(id)
                                    expanded = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (id == selectedValue) FontWeight.Bold else FontWeight.Normal,
                                )
                            }
                        }
                    }
                },
                confirmButton = {},
            )
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
                    modifier = Modifier.padding(bottom = NyatetDuwitSpacing.sm),
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
fun DateSectionHeader(
    dateLabel: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = NyatetDuwitSpacing.sm, horizontal = NyatetDuwitSpacing.xxs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(RoundedCornerShape(NyatetDuwitRadius.full))
                .background(MaterialTheme.colorScheme.primary),
        )
        Spacer(modifier = Modifier.width(NyatetDuwitSpacing.sm))
        Text(
            text = dateLabel,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun TransactionCard(
    transaction: Transaction,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onSaveAsTemplate: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val (icon, bgColor, amountColor) = when (transaction.type) {
        TransactionType.INCOME -> Triple("↓", NyatetDuwitColor.green100, NyatetDuwitColor.green500)
        TransactionType.EXPENSE -> Triple("↑", NyatetDuwitColor.red100, NyatetDuwitColor.red500)
        TransactionType.TRANSFER -> Triple("⇄", NyatetDuwitColor.gold100, MaterialTheme.colorScheme.onSurfaceVariant)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(NyatetDuwitRadius.md),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NyatetDuwitSpacing.lg),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(NyatetDuwitRadius.md))
                    .background(bgColor),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = amountColor,
                )
            }

            Spacer(modifier = Modifier.width(NyatetDuwitSpacing.md))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.notes ?: getDefaultLabel(transaction.type),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = formatTime(transaction.dateTime),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = formatAmount(transaction),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = amountColor,
                )
                Spacer(modifier = Modifier.width(NyatetDuwitSpacing.sm))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(NyatetDuwitRadius.sm))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(),
                        ) { onEdit() },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    )
                }
            }
        }
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
            text = if (hasFilters) "🔍" else "📝",
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.lg))
        Text(
            text = if (hasFilters) "Tidak ada hasil" else "Belum ada transaksi",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
        Text(
            text = if (hasFilters)
                "Coba ubah filter atau kata kunci pencarian"
            else
                "Yuk mulai! Tap + buat transaksi pertamamu",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = NyatetDuwitSpacing.xxxl),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xxl))
        if (hasFilters) {
            Button(
                onClick = onClearFilters,
                shape = RoundedCornerShape(NyatetDuwitRadius.md),
            ) {
                Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(NyatetDuwitSpacing.sm))
                Text("Hapus Filter")
            }
        } else {
            Button(
                onClick = onAddTransaction,
                shape = RoundedCornerShape(NyatetDuwitRadius.md),
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(NyatetDuwitSpacing.sm))
                Text("Tambah Transaksi")
            }
        }
    }
}

private fun getDefaultLabel(type: TransactionType): String = when (type) {
    TransactionType.INCOME -> "Pemasukan"
    TransactionType.EXPENSE -> "Pengeluaran"
    TransactionType.TRANSFER -> "Transfer"
}

private fun formatTime(dateTime: Long): String {
    val sdf = SimpleDateFormat("d MMM yyyy, HH:mm", Locale("id"))
    return sdf.format(Date(dateTime))
}

private fun formatAmount(transaction: Transaction): String = when (transaction.type) {
    TransactionType.INCOME -> "+${formatRupiah(transaction.amount)}"
    TransactionType.EXPENSE -> "-${formatRupiah(transaction.amount)}"
    TransactionType.TRANSFER -> formatRupiah(transaction.amount)
}
