package com.nyatetduwit.presentation.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.nyatetduwit.domain.model.BudgetPeriod
import com.nyatetduwit.domain.model.CategoryType
import com.nyatetduwit.presentation.components.CustomNumpad
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetFormScreen(
    budgetId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: BudgetViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val formState = uiState.formState
    val categories by viewModel.categories.collectAsState()

    val expenseCategories = categories.filter { it.type == CategoryType.EXPENSE }

    var numpadInput by remember { mutableStateOf("") }

    LaunchedEffect(budgetId) {
        viewModel.loadBudget(budgetId)
        if (budgetId == null) {
            numpadInput = ""
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateBack()
        }
    }

    LaunchedEffect(formState.amount) {
        numpadInput = if (formState.amount > 0) formState.amount.toString() else ""
    }

    val nf = NumberFormat.getNumberInstance(Locale("id", "ID"))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (budgetId == null) "Set Budget" else "Edit Budget") },
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
            Text("Tipe Budget", style = MaterialTheme.typography.labelLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilterChip(
                    selected = formState.isTotalBudget,
                    onClick = { viewModel.onToggleBudgetType(true) },
                    label = { Text("Total") },
                    leadingIcon = {
                        Icon(Icons.Default.AccountBalanceWallet, null, modifier = Modifier.size(18.dp))
                    },
                    modifier = Modifier.weight(1f),
                )
                FilterChip(
                    selected = !formState.isTotalBudget,
                    onClick = { viewModel.onToggleBudgetType(false) },
                    label = { Text("Per Kategori") },
                    leadingIcon = {
                        Icon(Icons.Default.Category, null, modifier = Modifier.size(18.dp))
                    },
                    modifier = Modifier.weight(1f),
                )
            }

            if (!formState.isTotalBudget) {
                Text("Pilih Kategori", style = MaterialTheme.typography.labelLarge)
                CategorySelector(
                    categories = expenseCategories,
                    selectedCategoryId = formState.categoryId,
                    onCategorySelected = viewModel::onCategoryChange,
                )
            }

            Text("Nominal Budget", style = MaterialTheme.typography.labelLarge)
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Rp ${nf.format(formState.amount)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            CustomNumpad(
                onNumberClick = { digit ->
                    val newInput = numpadInput + digit
                    val amount = newInput.toLongOrNull() ?: 0L
                    if (amount <= 999_999_999_999L) {
                        numpadInput = newInput
                        viewModel.onAmountChange(amount)
                    }
                },
                onBackspaceClick = {
                    if (numpadInput.isNotEmpty()) {
                        numpadInput = numpadInput.dropLast(1)
                        val amount = numpadInput.toLongOrNull() ?: 0L
                        viewModel.onAmountChange(amount)
                    }
                },
                onPresetClick = { amount ->
                    numpadInput = amount.toString()
                    viewModel.onAmountChange(amount)
                },
            )

            Text("Periode", style = MaterialTheme.typography.labelLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BudgetPeriod.entries.forEach { period ->
                    FilterChip(
                        selected = period == formState.period,
                        onClick = { viewModel.onPeriodChange(period) },
                        label = { Text(getPeriodLabel(period)) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = viewModel::saveBudget,
                modifier = Modifier.fillMaxWidth(),
                enabled = formState.amount > 0,
            ) {
                Icon(Icons.Default.Save, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (budgetId == null) "Simpan Budget" else "Update Budget")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategorySelector(
    categories: List<com.nyatetduwit.domain.model.Category>,
    selectedCategoryId: String?,
    onCategorySelected: (String?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedCategory = categories.find { it.id == selectedCategoryId }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            value = selectedCategory?.name ?: "Pilih kategori",
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onCategorySelected(category.id)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = getMaterialIconByName(category.icon),
                            contentDescription = null,
                        )
                    },
                )
            }
        }
    }
}

private fun getPeriodLabel(period: BudgetPeriod): String {
    return when (period) {
        BudgetPeriod.WEEKLY -> "Mingguan"
        BudgetPeriod.MONTHLY -> "Bulanan"
        BudgetPeriod.YEARLY -> "Tahunan"
    }
}

@Composable
private fun getMaterialIconByName(name: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when (name) {
        "restaurant" -> Icons.Default.Restaurant
        "directions_car" -> Icons.Default.DirectionsCar
        "shopping_cart" -> Icons.Default.ShoppingCart
        "receipt" -> Icons.Default.Receipt
        "home" -> Icons.Default.Home
        "movie" -> Icons.Default.Movie
        "favorite" -> Icons.Default.Favorite
        "school" -> Icons.Default.School
        "checkroom" -> Icons.Default.Checkroom
        "redeem" -> Icons.Default.Redeem
        "more_horiz" -> Icons.Default.MoreHoriz
        "account_balance_wallet" -> Icons.Default.AccountBalanceWallet
        else -> Icons.Default.Category
    }
}
