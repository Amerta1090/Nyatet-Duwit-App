package com.nyatetduwit.presentation.security

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.nyatetduwit.core.security.SecurityManager
import com.nyatetduwit.core.util.HapticFeedback

@Composable
fun LockScreen(
    onUnlock: () -> Unit,
    securityManager: SecurityManager,
) {
    val context = LocalContext.current
    val view = LocalView.current
    var pin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var showPin by remember { mutableStateOf(!securityManager.isBiometricAvailable()) }

    LaunchedEffect(Unit) {
        if (securityManager.isBiometricAvailable() && !showPin) {
            val activity = context as? FragmentActivity
            if (activity != null) {
                securityManager.authenticateWithBiometric(
                    activity = activity,
                    title = "Buka NyatetDuwit",
                    subtitle = "Sentuh sensor sidik jari atau gunakan PIN",
                    onSuccess = {
                        onUnlock()
                    },
                    onError = {
                        showPin = true
                    },
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = com.nyatetduwit.R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Aplikasi Terkunci",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (showPin) "Masukkan PIN untuk melanjutkan" else "Verifikasi identitas kamu",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (showPin) {
            PinDots(pin = pin)

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
                    HapticFeedback.click(view)
                    if (pin.length < 6) {
                        pin += key
                        if (pin.length == 6) {
                            if (securityManager.verifyPin(pin)) {
                                HapticFeedback.success(view)
                                onUnlock()
                            } else {
                                HapticFeedback.error(view)
                                error = "PIN salah. Coba lagi."
                                pin = ""
                            }
                        }
                    }
                },
                onDelete = {
                    error = null
                    if (pin.isNotEmpty()) {
                        pin = pin.dropLast(1)
                        HapticFeedback.click(view)
                    }
                },
                leftButton = if (securityManager.isBiometricAvailable()) {
                    {
                        IconButton(
                            onClick = {
                                val activity = context as? FragmentActivity
                                if (activity != null) {
                                    securityManager.authenticateWithBiometric(
                                        activity = activity,
                                        title = "Buka NyatetDuwit",
                                        subtitle = "Sentuh sensor sidik jari atau gunakan PIN",
                                        onSuccess = {
                                            onUnlock()
                                        },
                                        onError = {
                                            // Handle error
                                        }
                                    )
                                }
                            },
                            modifier = Modifier.size(80.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Fingerprint,
                                contentDescription = "Sidik Jari",
                                modifier = Modifier.size(28.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                } else null
            )
        } else {
            IconButton(
                onClick = {
                    HapticFeedback.click(view)
                    val activity = context as? FragmentActivity
                    if (activity != null) {
                        securityManager.authenticateWithBiometric(
                            activity = activity,
                            title = "Buka NyatetDuwit",
                            subtitle = "Sentuh sensor sidik jari atau gunakan PIN",
                            onSuccess = {
                                onUnlock()
                            },
                            onError = {
                                showPin = true
                            }
                        )
                    }
                },
                modifier = Modifier
                    .size(100.dp)
                    .padding(12.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = "Sentuh Sidik Jari",
                    modifier = Modifier.fillMaxSize(),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            FilledTonalButton(
                onClick = {
                    HapticFeedback.click(view)
                    showPin = true
                },
            ) {
                Icon(Icons.Default.Lock, null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Gunakan PIN")
            }
        }
    }
}
