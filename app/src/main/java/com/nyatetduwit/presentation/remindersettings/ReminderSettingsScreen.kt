package com.nyatetduwit.presentation.remindersettings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderSettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: ReminderSettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var showTimePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan Reminder") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(
                                    text = "Aktifkan Reminder",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                Text(
                                    text = "Dapatkan pengingat untuk mencatat transaksi",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            Switch(
                                checked = uiState.isEnabled,
                                onCheckedChange = { viewModel.toggleReminder(it) },
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            text = "Waktu Reminder",
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(
                                    text = "Jam Reminder",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                                Text(
                                    text = "${String.format("%02d", uiState.reminderHour)}:00",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                            FilledTonalButton(
                                onClick = { showTimePicker = true },
                                enabled = uiState.isEnabled,
                            ) {
                                Icon(Icons.Default.AccessTime, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Ubah")
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            text = "Frekuensi Adaptif",
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Text(
                            text = viewModel.getFrequencyLabel(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )

                        Text(
                            text = "Terakhir aktif: ${viewModel.getLastActivityLabel()}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )

                        HorizontalDivider()

                        Text(
                            text = "Reminder akan menyesuaikan berdasarkan aktivitasmu:",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = "• Rajin nyatet (5+ hari) → 1x per hari",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = "• Tidak nyatet 3+ hari → 2x per hari",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            currentHour = uiState.reminderHour,
            onDismiss = { showTimePicker = false },
            onConfirm = { hour ->
                viewModel.setReminderHour(hour)
                showTimePicker = false
            },
        )
    }
}

@Composable
private fun TimePickerDialog(
    currentHour: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
) {
    var selectedHour by remember { mutableIntStateOf(currentHour) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Pilih Jam Reminder") },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${String.format("%02d", selectedHour)}:00",
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    for (hour in listOf(6, 8, 10, 12, 15, 18, 20, 21)) {
                        FilterChip(
                            selected = selectedHour == hour,
                            onClick = { selectedHour = hour },
                            label = { Text("${String.format("%02d", hour)}:00") },
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(selectedHour) }) {
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
