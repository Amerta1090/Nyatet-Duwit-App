package com.nyatetduwit.core.util

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

@Composable
fun animateFloatAsProgress(
    targetValue: Float,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 800),
): Float {
    val animatedValue by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = animationSpec,
        label = "progress",
    )
    return animatedValue
}

fun formatRupiah(amount: Long): String {
    val absAmount = kotlin.math.abs(amount)
    val formatted = String.format("%,d", absAmount).replace(',', '.')
    return if (amount < 0) "-Rp $formatted" else "Rp $formatted"
}
