package com.nyatetduwit.presentation.security

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinSetupScreen(
    onNavigateBack: () -> Unit,
    onPinSet: (String) -> Unit,
) {
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var step by remember { mutableIntStateOf(1) }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (step == 1) "Buat PIN" else "Konfirmasi PIN") },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = if (step == 1) Icons.Default.Shield else Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (step == 1) "Buat PIN 6 digit" else "Masukkan ulang PIN",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (step == 1) "PIN ini akan digunakan untuk mengamankan aplikasi kamu" else "Pastikan PIN yang kamu masukkan sama",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(32.dp))

            PinDots(pin = if (step == 1) pin else confirmPin)

            error?.let { err ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = err,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            PinKeypad(
                onKeyTap = { key ->
                    error = null
                    if (step == 1) {
                        if (pin.length < 6) {
                            pin += key
                            if (pin.length == 6) {
                                step = 2
                            }
                        }
                    } else {
                        if (confirmPin.length < 6) {
                            confirmPin += key
                            if (confirmPin.length == 6) {
                                if (confirmPin == pin) {
                                    onPinSet(pin)
                                } else {
                                    error = "PIN tidak cocok. Coba lagi."
                                    confirmPin = ""
                                    pin = ""
                                    step = 1
                                }
                            }
                        }
                    }
                },
                onDelete = {
                    error = null
                    if (step == 1) {
                        if (pin.isNotEmpty()) pin = pin.dropLast(1)
                    } else {
                        if (confirmPin.isNotEmpty()) confirmPin = confirmPin.dropLast(1)
                    }
                },
            )
        }
    }
}

@Composable
internal fun PinDots(pin: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        repeat(6) { index ->
            val active = index < pin.length
            Surface(
                modifier = Modifier.size(16.dp),
                shape = androidx.compose.foundation.shape.CircleShape,
                color = if (active) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                }
            ) {}
        }
    }
}

@Composable
internal fun PinKeypad(
    onKeyTap: (String) -> Unit,
    onDelete: () -> Unit,
    leftButton: @Composable (() -> Unit)? = null,
) {
    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("", "0", "delete"),
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
            ) {
                row.forEach { key ->
                    when (key) {
                        "" -> {
                            if (leftButton != null) {
                                Box(
                                    modifier = Modifier.size(80.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    leftButton()
                                }
                            } else {
                                Box(modifier = Modifier.size(80.dp))
                            }
                        }
                        "delete" -> {
                            IconButton(
                                onClick = onDelete,
                                modifier = Modifier.size(80.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Backspace,
                                    contentDescription = "Hapus",
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        else -> {
                            FilledTonalButton(
                                onClick = { onKeyTap(key) },
                                modifier = Modifier.size(80.dp),
                                shape = androidx.compose.foundation.shape.CircleShape,
                                colors = ButtonDefaults.filledTonalButtonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                contentPadding = PaddingValues(0.dp),
                            ) {
                                Text(
                                    text = key,
                                    fontSize = 28.sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

