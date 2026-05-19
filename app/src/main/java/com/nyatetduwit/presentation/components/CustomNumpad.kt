package com.nyatetduwit.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp

@Composable
fun CustomNumpad(
    onNumberClick: (String) -> Unit,
    onBackspaceClick: () -> Unit,
    onPresetClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    presets: List<Long> = listOf(5_000, 10_000, 20_000, 50_000, 100_000),
) {
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        ) {
            presets.forEach { preset ->
                TextButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onPresetClick(preset)
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(formatRupiahShort(preset))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val keys = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("000", "0", ""),
        )

        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                row.forEach { key ->
                    if (key.isEmpty()) {
                        TextButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onBackspaceClick()
                            },
                            modifier = Modifier.weight(1f),
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.Backspace,
                                contentDescription = "Backspace",
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    } else {
                        TextButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onNumberClick(key)
                            },
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(
                                text = key,
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun formatRupiahShort(amount: Long): String {
    return when {
        amount >= 1_000_000 -> "Rp ${amount / 1_000_000}M"
        amount >= 1_000 -> "Rp ${amount / 1_000}K"
        else -> "Rp $amount"
    }
}
