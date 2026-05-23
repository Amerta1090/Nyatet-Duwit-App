package com.nyatetduwit.presentation.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.Budget
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    onNavigateBack: () -> Unit,
    onAddBudget: () -> Unit,
    onEditBudget: (String) -> Unit,
    viewModel: BudgetViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val budgets by viewModel.budgets.collectAsState()
    var showDeleteBudget by remember { mutableStateOf<Budget?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget Bulanan") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onAddBudget) {
                        Icon(Icons.Default.Add, "Add Budget")
                    }
                },
            )
        },
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
            uiState.budgetProgress.isEmpty() && budgets.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    EmptyBudgetState(onAddBudget = onAddBudget)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(uiState.budgetProgress, key = { it.budgetId }) { progress ->
                        BudgetProgressCard(
                            progress = progress,
                            onEdit = { onEditBudget(progress.budgetId) },
                            onDelete = {
                                showDeleteBudget = budgets.find { it.id == progress.budgetId }
                            },
                        )
                    }
                }
            }
        }
    }

    showDeleteBudget?.let { budget ->
        AlertDialog(
            onDismissRequest = { showDeleteBudget = null },
            title = { Text("Hapus Budget?") },
            text = { Text("Budget ini akan dihapus. Lanjutkan?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteBudget(budget) { showDeleteBudget = null }
                    },
                ) {
                    Text("Hapus", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteBudget = null }) {
                    Text("Batal")
                }
            },
        )
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
private fun BudgetProgressCard(
    progress: BudgetProgressItem,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    val nf = NumberFormat.getNumberInstance(Locale("id", "ID"))

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = progress.categoryName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, "Edit", modifier = Modifier.size(20.dp))
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, "Delete", modifier = Modifier.size(20.dp))
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Rp ${nf.format(progress.spentAmount)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = when (progress.warningLevel) {
                        BudgetWarningLevel.EXCEEDED -> MaterialTheme.colorScheme.error
                        BudgetWarningLevel.WARNING -> Color(0xFFFFA726)
                        BudgetWarningLevel.NORMAL -> MaterialTheme.colorScheme.onSurface
                    },
                )
                Text(
                    text = "Rp ${nf.format(progress.budgetAmount)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            val progressFraction = progress.percentage.coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { progressFraction },
                modifier = Modifier.fillMaxWidth(),
                color = when (progress.warningLevel) {
                    BudgetWarningLevel.EXCEEDED -> MaterialTheme.colorScheme.error
                    BudgetWarningLevel.WARNING -> Color(0xFFFFA726)
                    BudgetWarningLevel.NORMAL -> {
                        val color = Color(progress.categoryColor.substring(1).toLong(16) or 0xFF000000)
                        color
                    }
                },
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "${(progress.percentage * 100).toInt()}% terpakai",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                if (progress.remaining > 0) {
                    Text(
                        text = "Sisa: Rp ${nf.format(progress.remaining)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                } else {
                    Text(
                        text = "Budget habis!",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            if (progress.warningLevel == BudgetWarningLevel.WARNING) {
                AssistChip(
                    onClick = {},
                    label = { Text("Budget sudah 80%, hati-hati ya!") },
                    leadingIcon = {
                        Icon(Icons.Default.Warning, null, modifier = Modifier.size(16.dp))
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFFFFA726).copy(alpha = 0.12f),
                    ),
                )
            }

            if (progress.warningLevel == BudgetWarningLevel.EXCEEDED) {
                AssistChip(
                    onClick = {},
                    label = { Text("Budget habis! Lebih Rp ${nf.format(-progress.remaining)}") },
                    leadingIcon = {
                        Icon(Icons.Default.Error, null, modifier = Modifier.size(16.dp))
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.12f),
                    ),
                )
            }
        }
    }
}

@Composable
private fun EmptyBudgetState(onAddBudget: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            Icons.Default.AccountBalanceWallet,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = "Belum ada budget",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "Set budget bulanan untuk kontrol pengeluaran kamu. Nanti ada warning kalau udah mendekati limit!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Button(onClick = onAddBudget) {
            Icon(Icons.Default.Add, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Set Budget")
        }
    }
}
