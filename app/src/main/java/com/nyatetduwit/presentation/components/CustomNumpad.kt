package com.nyatetduwit.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.core.util.formatRupiah

private val KeyShape = RoundedCornerShape(14.dp)

@Composable
fun CustomNumpad(
    onNumberClick: (String) -> Unit = {},
    onBackspaceClick: () -> Unit = {},
    onPresetClick: (Long) -> Unit = {},
    onAmountConfirmed: (Long) -> Unit = {},
    currentAmount: Long = 0L,
    confirmLabel: String? = null,
    modifier: Modifier = Modifier,
    presets: List<Long> = listOf(5_000, 10_000, 20_000, 50_000, 100_000),
) {
    val haptic = LocalHapticFeedback.current
    val showPresets = confirmLabel == null
    val isDark = MaterialTheme.colorScheme.surface == NyatetDuwitColor.gray800

    val keypadSurface = if (isDark) {
        NyatetDuwitColor.gray700.copy(alpha = 0.5f)
    } else {
        NyatetDuwitColor.gray100.copy(alpha = 0.5f)
    }

    val keyColor = if (isDark) {
        NyatetDuwitColor.gray700
    } else {
        NyatetDuwitColor.pureWhite
    }

    val keyTextColor = MaterialTheme.colorScheme.onSurface

    val onTap: () -> Unit = {
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(NyatetDuwitRadius.xl))
            .background(keypadSurface)
            .padding(top = NyatetDuwitSpacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (showPresets) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = NyatetDuwitSpacing.lg),
                horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
            ) {
                presets.forEach { preset ->
                    val isActive = currentAmount == preset
                    val chipBg by animateColorAsState(
                        targetValue = if (isActive) {
                            MaterialTheme.colorScheme.primary
                        } else if (isDark) {
                            NyatetDuwitColor.gray700
                        } else {
                            NyatetDuwitColor.pureWhite
                        },
                        animationSpec = spring(),
                        label = "chipBg",
                    )
                    val chipTextColor by animateColorAsState(
                        targetValue = if (isActive) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            keyTextColor
                        },
                        animationSpec = spring(),
                        label = "chipText",
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(NyatetDuwitRadius.md))
                            .background(chipBg)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(),
                            ) {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onPresetClick(preset)
                            }
                            .padding(vertical = NyatetDuwitSpacing.sm + 2.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = formatPreset(preset),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = chipTextColor,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.md))
        }

        val rows = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("000", "0", null),
        )

        rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = NyatetDuwitSpacing.sm),
                horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
            ) {
                row.forEach { key ->
                    when (key) {
                        null -> {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1.3f)
                                    .clip(KeyShape)
                                    .background(keyColor)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = ripple(),
                                    ) {
                                        onTap()
                                        onBackspaceClick()
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Backspace,
                                    contentDescription = "Hapus",
                                    modifier = Modifier.size(22.dp),
                                    tint = keyTextColor.copy(alpha = 0.7f),
                                )
                            }
                        }
                        "confirm" -> {
                            val isEnabled = currentAmount > 0
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1.3f)
                                    .clip(KeyShape)
                                    .background(
                                        if (isEnabled) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surfaceVariant
                                    )
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = ripple(),
                                        enabled = isEnabled,
                                    ) {
                                        onTap()
                                        onAmountConfirmed(currentAmount)
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = confirmLabel ?: "",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isEnabled) {
                                        MaterialTheme.colorScheme.onPrimary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                )
                            }
                        }
                        else -> {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1.3f)
                                    .clip(KeyShape)
                                    .background(keyColor)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = ripple(),
                                    ) {
                                        onTap()
                                        onNumberClick(key)
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = key,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = keyTextColor,
                                    textAlign = TextAlign.Center,
                                    fontSize = if (key == "000") 18.sp else 22.sp,
                                )
                            }
                        }
                    }
                }
            }

            if (row != rows.last()) {
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
            }
        }

        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm + 2.dp))
    }
}

private fun formatPreset(amount: Long): String {
    return when {
        amount >= 1_000_000 -> "${amount / 1_000_000}jt"
        amount >= 1_000 -> "${amount / 1_000}rb"
        else -> formatRupiah(amount).replace("Rp ", "")
    }
}
