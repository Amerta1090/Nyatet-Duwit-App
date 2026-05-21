package com.nyatetduwit.presentation.security

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.nyatetduwit.NyatetDuwitApp
import com.nyatetduwit.core.util.HapticFeedback

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecuritySettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPinSetup: () -> Unit,
) {
    val context = LocalContext.current
    val view = LocalView.current
    val app = context.applicationContext as NyatetDuwitApp
    val securityManager = app.securityManager
    val appLockManager = app.appLockManager

    var isBiometricEnabled by remember { mutableStateOf(securityManager.isBiometricAvailable()) }
    var isPinSet by remember { mutableStateOf(securityManager.isPinSet()) }
    var lockTimeout by remember { mutableIntStateOf(appLockManager.getLockTimeoutMinutes()) }

    val timeoutOptions = listOf(0, 1, 5, 15)
    var showTimeoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Keamanan") },
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Autentikasi",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text("Biometrik", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                text = if (securityManager.isBiometricAvailable()) "Sidik jari / Face unlock" else "Tidak tersedia",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Switch(
                            checked = isBiometricEnabled && securityManager.isBiometricAvailable(),
                            onCheckedChange = {
                                HapticFeedback.click(view)
                                isBiometricEnabled = it
                            },
                            enabled = securityManager.isBiometricAvailable(),
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text("PIN", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                text = if (isPinSet) "Sudah diatur" else "Belum diatur",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Button(
                            onClick = {
                                HapticFeedback.click(view)
                                onNavigateToPinSetup()
                            },
                            enabled = !isPinSet,
                        ) {
                            Text(if (isPinSet) "Diatur" else "Buat PIN")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Kunci Aplikasi",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text("Kunci saat background", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                text = getTimeoutLabel(lockTimeout),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        TextButton(onClick = { showTimeoutDialog = true }) {
                            Text("Ubah")
                        }
                    }
                }
            }
        }
    }

    if (showTimeoutDialog) {
        AlertDialog(
            onDismissRequest = { showTimeoutDialog = false },
            title = { Text("Timeout Kunci") },
            text = {
                Column {
                    timeoutOptions.forEach { minutes ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(getTimeoutLabel(minutes))
                            RadioButton(
                                selected = lockTimeout == minutes,
                                onClick = {
                                    lockTimeout = minutes
                                    appLockManager.setLockTimeoutMinutes(minutes)
                                    showTimeoutDialog = false
                                },
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showTimeoutDialog = false }) {
                    Text("Tutup")
                }
            },
        )
    }
}

private fun getTimeoutLabel(minutes: Int) = when (minutes) {
    0 -> "Instan"
    1 -> "1 menit"
    5 -> "5 menit"
    15 -> "15 menit"
    else -> "$minutes menit"
}
