package com.nyatetduwit.presentation.debt

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.core.util.formatRupiah
import com.nyatetduwit.domain.model.Debt
import com.nyatetduwit.domain.model.DebtType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtScreen(
    onNavigateBack: () -> Unit,
    onAddDebt: () -> Unit,
    onDebtClick: (String) -> Unit,
    viewModel: DebtViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteConfirm by remember { mutableStateOf<Debt?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Utang & Piutang") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = onAddDebt) {
                        Icon(Icons.Default.Add, "Tambah")
                    }
                },
            )
        },
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.debts.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "💸", style = MaterialTheme.typography.displayMedium)
                    Spacer(Modifier.height(NyatetDuwitSpacing.lg))
                    Text("Belum ada catatan", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(NyatetDuwitSpacing.sm))
                    Text(
                        "Catat utang dan piutang biar gak lupa",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(NyatetDuwitSpacing.xl))
                    Button(onClick = onAddDebt) {
                        Icon(Icons.Default.Add, null)
                        Spacer(Modifier.width(NyatetDuwitSpacing.sm))
                        Text("Catat Baru")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(NyatetDuwitSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
            ) {
                item {
                    SummaryRow(debts = uiState.debts)
                }

                items(uiState.debts, key = { it.id }) { debt ->
                    DebtCard(
                        debt = debt,
                        onClick = { onDebtClick(debt.id) },
                        onDelete = { showDeleteConfirm = debt },
                    )
                }
            }
        }
    }

    showDeleteConfirm?.let { debt ->
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = null },
            title = { Text("Hapus?") },
            text = { Text("Data ini akan dihapus. Lanjutkan?") },
            confirmButton = {
                TextButton(onClick = { viewModel.deleteDebt(debt); showDeleteConfirm = null }) {
                    Text("Hapus", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = null }) { Text("Batal") }
            },
        )
    }
}

@Composable
private fun SummaryRow(debts: List<Debt>) {
    val totalOwe = debts.filter { it.type == DebtType.OWE }.sumOf { it.remainingAmount }
    val totalLent = debts.filter { it.type == DebtType.LENT }.sumOf { it.remainingAmount }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(NyatetDuwitRadius.md),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(NyatetDuwitSpacing.lg),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Utang", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(NyatetDuwitSpacing.xxs))
                Text(formatRupiah(totalOwe), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NyatetDuwitColor.red500)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Piutang", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(NyatetDuwitSpacing.xxs))
                Text(formatRupiah(totalLent), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NyatetDuwitColor.green500)
            }
        }
    }
}

@Composable
private fun DebtCard(debt: Debt, onClick: () -> Unit, onDelete: () -> Unit) {
    val animatedProgress by animateFloatAsState(
        targetValue = debt.progress,
        animationSpec = tween(800),
        label = "progress",
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(NyatetDuwitRadius.md),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(NyatetDuwitSpacing.md),
            verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val icon = if (debt.type == DebtType.OWE) "🔴" else "🟢"
                    Text(icon, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.width(NyatetDuwitSpacing.sm))
                    Column {
                        Text(debt.personName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text(
                            if (debt.type == DebtType.OWE) "Utang" else "Piutang",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (debt.type == DebtType.OWE) NyatetDuwitColor.red500 else NyatetDuwitColor.green500,
                        )
                    }
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Hapus", modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(NyatetDuwitRadius.full)),
                color = if (debt.type == DebtType.OWE) NyatetDuwitColor.coral500 else NyatetDuwitColor.teal500,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Terbayar: ${formatRupiah(debt.paidAmount)}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Sisa: ${formatRupiah(debt.remainingAmount)}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold)
            }

            if (debt.dueDate != null) {
                val sdf = SimpleDateFormat("d MMM yyyy", Locale("id"))
                val isOverdue = debt.dueDate < System.currentTimeMillis()
                Text(
                    "Jatuh tempo: ${sdf.format(Date(debt.dueDate))}${if (isOverdue) " (lewat)" else ""}",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isOverdue) NyatetDuwitColor.red500 else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
