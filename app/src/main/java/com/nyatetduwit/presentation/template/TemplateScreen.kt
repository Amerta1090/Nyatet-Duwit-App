package com.nyatetduwit.presentation.template

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.Template
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.presentation.account.formatRupiah

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateScreen(
    onNavigateBack: () -> Unit,
    onTemplateClick: (String) -> Unit,
    viewModel: TemplateViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val haptic = LocalHapticFeedback.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Template") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            uiState.templates.isEmpty() -> {
                EmptyTemplateState(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val pinned = uiState.templates.filter { it.isPinned }
                    val unpinned = uiState.templates.filter { !it.isPinned }

                    if (pinned.isNotEmpty()) {
                        item {
                            Text(
                                text = "Disematkan",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(vertical = 8.dp),
                            )
                        }
                        items(pinned) { template ->
                            TemplateItem(
                                template = template,
                                onClick = { onTemplateClick(template.id) },
                                onTogglePin = { viewModel.togglePin(template) },
                                onDelete = { viewModel.deleteTemplate(template) },
                                getTypeLabel = { viewModel.getTransactionTypeLabel(it) },
                            )
                        }
                    }

                    if (unpinned.isNotEmpty()) {
                        if (pinned.isNotEmpty()) {
                            item {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                        item {
                            Text(
                                text = "Lainnya",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(vertical = 8.dp),
                            )
                        }
                        items(unpinned) { template ->
                            TemplateItem(
                                template = template,
                                onClick = { onTemplateClick(template.id) },
                                onTogglePin = { viewModel.togglePin(template) },
                                onDelete = { viewModel.deleteTemplate(template) },
                                getTypeLabel = { viewModel.getTransactionTypeLabel(it) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TemplateItem(
    template: Template,
    onClick: () -> Unit,
    onTogglePin: () -> Unit,
    onDelete: () -> Unit,
    getTypeLabel: (TransactionType) -> String,
    modifier: Modifier = Modifier,
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }

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
                    text = when (template.type) {
                        TransactionType.INCOME -> "📈"
                        TransactionType.EXPENSE -> "📉"
                        TransactionType.TRANSFER -> "🔄"
                    },
                    style = MaterialTheme.typography.titleMedium,
                )

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = template.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        if (template.isPinned) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Pinned",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp),
                            )
                        }
                    }
                    Text(
                        text = "${getTypeLabel(template.type)} • Dipakai ${template.usageCount}x",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Text(
                text = formatRupiah(template.amount),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )

            Row {
                IconButton(onClick = onTogglePin) {
                    Icon(
                        if (template.isPinned) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = if (template.isPinned) "Unpin" else "Pin",
                        tint = if (template.isPinned) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                IconButton(onClick = { showDeleteConfirm = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Hapus Template") },
            text = { Text("Yakin hapus template \"${template.name}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteConfirm = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error,
                    ),
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Batal")
                }
            },
        )
    }
}

@Composable
fun EmptyTemplateState(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "⭐",
            style = MaterialTheme.typography.displayLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Belum ada template",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Long press transaksi di list untuk menyimpan sebagai template",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
