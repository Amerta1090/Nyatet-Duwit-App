package com.nyatetduwit.presentation.settings

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.NyatetDuwitApp
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.util.HapticFeedback
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSecurity: () -> Unit,
    onNavigateToReminder: () -> Unit,
    onNavigateToAbout: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val view = LocalView.current
    val app = context.applicationContext as NyatetDuwitApp
    val securityManager = app.securityManager
    val scope = rememberCoroutineScope()

    val pdfExportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri: Uri? ->
        uri?.let {
            scope.launch {
                val calendar = Calendar.getInstance()
                val monthFormat = java.text.SimpleDateFormat("MMMM yyyy", Locale("id"))
                val monthLabel = monthFormat.format(calendar.time)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val startDate = calendar.timeInMillis
                calendar.add(Calendar.MONTH, 1)
                calendar.add(Calendar.MILLISECOND, -1)
                val endDate = calendar.timeInMillis

                val result = viewModel.pdfExportManager.exportPdf(context, it, monthLabel, startDate, endDate)
                result.onSuccess {
                    Toast.makeText(context, "PDF berhasil diexport", Toast.LENGTH_SHORT).show()
                }.onFailure { e ->
                    Toast.makeText(context, "Gagal: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val csvExportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("text/csv")
    ) { uri: Uri? ->
        uri?.let {
            scope.launch {
                val result = viewModel.exportManager.exportCsv(context, it)
                result.onSuccess {
                    Toast.makeText(context, "CSV berhasil diexport", Toast.LENGTH_SHORT).show()
                }.onFailure { e ->
                    Toast.makeText(context, "Gagal: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val backupLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/octet-stream")
    ) { uri: Uri? ->
        uri?.let {
            scope.launch {
                val encrypt: (suspend (String) -> String)? =
                    if (securityManager.isPinSet()) {
                        { data -> securityManager.encryptData(data) }
                    } else null

                val result = viewModel.exportManager.createBackup(context, it, encrypt)
                result.onSuccess {
                    Toast.makeText(context, "Backup berhasil dibuat", Toast.LENGTH_SHORT).show()
                }.onFailure { e ->
                    Toast.makeText(context, "Gagal: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val restoreLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            scope.launch {
                val decrypt: (suspend (String) -> String)? =
                    if (securityManager.isPinSet()) {
                        { data -> securityManager.decryptData(data) }
                    } else null

                val result = viewModel.exportManager.restoreBackup(context, it, decrypt)
                result.onSuccess {
                    Toast.makeText(
                        context,
                        "Data berhasil dipulihkan",
                        Toast.LENGTH_SHORT
                    ).show()
                }.onFailure { e ->
                    Toast.makeText(
                        context,
                        "Gagal: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SectionHeader("Tampilan")

            SettingsCard {
                ThemeToggle(
                    isDarkTheme = uiState.isDarkTheme,
                    onToggle = { viewModel.toggleDarkTheme() },
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                BalanceToggle(
                    isVisible = uiState.isBalanceVisible,
                    onToggle = { viewModel.toggleBalanceVisibility() },
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                AmoledToggle(
                    isEnabled = uiState.isAmoledDark,
                    onToggle = { viewModel.toggleAmoledDark() },
                )
            }

            SectionHeader("Warna Aksen")

            SettingsCard {
                AccentColorPicker(
                    currentColor = uiState.accentColor,
                    onColorSelected = { viewModel.setAccentColor(it) },
                )
            }

            SectionHeader("Keamanan")

            SettingsNavCard(
                icon = Icons.Default.Lock,
                title = "Keamanan Aplikasi",
                description = "PIN, biometrik, kunci aplikasi",
                onClick = {
                    HapticFeedback.click(view)
                    onNavigateToSecurity()
                },
            )

            SectionHeader("Notifikasi")

            SettingsNavCard(
                icon = Icons.Default.Notifications,
                title = "Reminder",
                description = "Pengingat catat transaksi",
                onClick = {
                    HapticFeedback.click(view)
                    onNavigateToReminder()
                },
            )

            SectionHeader("Data")

            SettingsCard {
                SettingsActionRow(
                    icon = Icons.Default.FileDownload,
                    title = "Export CSV",
                    description = "Simpan transaksi ke file CSV",
                    onClick = {
                        HapticFeedback.click(view)
                        csvExportLauncher.launch("nyatetduwit_transactions.csv")
                    },
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            SettingsCard {
                SettingsActionRow(
                    icon = Icons.Default.Download,
                    title = "Export PDF (Bulan Ini)",
                    description = "Laporan keuangan bulan ini",
                    onClick = {
                        HapticFeedback.click(view)
                        pdfExportLauncher.launch("nyatetduwit_report.pdf")
                    },
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            SettingsCard {
                SettingsActionRow(
                    icon = Icons.Default.Backup,
                    title = "Backup Data",
                    description = "Buat file backup terenkripsi",
                    onClick = {
                        HapticFeedback.click(view)
                        backupLauncher.launch("nyatetduwit_backup.nyatetduwit")
                    },
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            SettingsCard {
                SettingsActionRow(
                    icon = Icons.Default.Restore,
                    title = "Pulihkan Data",
                    description = "Kembalikan data dari file backup",
                    onClick = {
                        HapticFeedback.click(view)
                        restoreLauncher.launch(arrayOf("*/*"))
                    },
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            SettingsCard {
                AutoBackupToggle(
                    isEnabled = uiState.isAutoBackupEnabled,
                    onToggle = { viewModel.toggleAutoBackup() },
                )
            }

            SectionHeader("Tentang")

            SettingsNavCard(
                icon = Icons.Default.Info,
                title = "Tentang Aplikasi",
                description = "Versi, kebijakan privasi",
                onClick = {
                    HapticFeedback.click(view)
                    onNavigateToAbout()
                },
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 8.dp),
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) { content() }
    }
}

@Composable
private fun ThemeToggle(isDarkTheme: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text("Tema Gelap", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = if (isDarkTheme) "Aktif" else "Nonaktif",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(checked = isDarkTheme, onCheckedChange = { onToggle() })
    }
}

@Composable
private fun BalanceToggle(isVisible: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text("Tampilkan Saldo", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = if (isVisible) "Saldo terlihat" else "Saldo tersembunyi",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(checked = isVisible, onCheckedChange = { onToggle() })
    }
}

@Composable
private fun SettingsNavCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun AutoBackupToggle(isEnabled: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text("Backup Otomatis", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = if (isEnabled) "Mingguan (setiap 7 hari)" else "Nonaktif",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(checked = isEnabled, onCheckedChange = { onToggle() })
    }
}

@Composable
private fun AmoledToggle(isEnabled: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text("AMOLED Hitam", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = if (isEnabled) "Latar belakang hitam pekat" else "Latar belakang abu-abu",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(checked = isEnabled, onCheckedChange = { onToggle() })
    }
}

data class AccentColorOption(val name: String, val label: String, val color: Color)

@Composable
private fun AccentColorPicker(currentColor: String, onColorSelected: (String) -> Unit) {
    val colors = listOf(
        AccentColorOption("teal", "Teal", NyatetDuwitColor.teal700),
        AccentColorOption("gold", "Gold", NyatetDuwitColor.gold700),
        AccentColorOption("coral", "Coral", NyatetDuwitColor.coral700),
        AccentColorOption("green", "Green", NyatetDuwitColor.green500),
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Pilih warna aksen", style = MaterialTheme.typography.bodyLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            colors.forEach { option ->
                val isSelected = currentColor == option.name
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(option.color)
                            .then(
                                if (isSelected) Modifier.border(3.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                                else Modifier
                            )
                            .clickable { onColorSelected(option.name) },
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(option.label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun SettingsActionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        FilledTonalButton(onClick = onClick) {
            Text("Pilih")
        }
    }
}
