package com.nyatetduwit.core.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

fun getAccentColors(accentName: String): AccentColors {
    return when (accentName) {
        "gold" -> AccentColors(
            primary = NyatetDuwitColor.gold700,
            primaryLight = NyatetDuwitColor.gold100,
            primaryDark = NyatetDuwitColor.gold300,
            onPrimary = NyatetDuwitColor.pureWhite,
        )
        "coral" -> AccentColors(
            primary = NyatetDuwitColor.coral700,
            primaryLight = NyatetDuwitColor.coral100,
            primaryDark = NyatetDuwitColor.coral500,
            onPrimary = NyatetDuwitColor.pureWhite,
        )
        "green" -> AccentColors(
            primary = NyatetDuwitColor.teal500,
            primaryLight = NyatetDuwitColor.teal100,
            primaryDark = NyatetDuwitColor.teal600,
            onPrimary = NyatetDuwitColor.pureWhite,
        )
        "teal" -> AccentColors(
            primary = NyatetDuwitColor.teal700,
            primaryLight = NyatetDuwitColor.teal100,
            primaryDark = NyatetDuwitColor.teal200,
            onPrimary = NyatetDuwitColor.pureWhite,
        )
        else -> AccentColors(
            primary = NyatetDuwitColor.teal700,
            primaryLight = NyatetDuwitColor.teal100,
            primaryDark = NyatetDuwitColor.teal200,
            onPrimary = NyatetDuwitColor.pureWhite,
        )
    }
}

data class AccentColors(
    val primary: Color,
    val primaryLight: Color,
    val primaryDark: Color,
    val onPrimary: Color,
)

@Composable
fun NyatetDuwitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accentName: String = "teal",
    amoledDark: Boolean = false,
    content: @Composable () -> Unit,
) {
    val accents = getAccentColors(accentName)
    val colorScheme = if (darkTheme) {
        val bg = if (amoledDark) NyatetDuwitColor.amoledBlack else NyatetDuwitColor.gray950
        val surface = if (amoledDark) NyatetDuwitColor.amoledBlack else NyatetDuwitColor.gray900
        val surfaceContainer = if (amoledDark) NyatetDuwitColor.gray950 else NyatetDuwitColor.gray850
        val surfaceContainerHigh = if (amoledDark) NyatetDuwitColor.gray900 else NyatetDuwitColor.gray800

        darkColorScheme(
            primary = accents.primary,
            onPrimary = NyatetDuwitColor.pureWhite,
            primaryContainer = accents.primary.copy(alpha = 0.2f),
            onPrimaryContainer = accents.primaryLight,
            secondary = NyatetDuwitColor.gray300,
            onSecondary = NyatetDuwitColor.gray900,
            secondaryContainer = NyatetDuwitColor.gray600,
            onSecondaryContainer = NyatetDuwitColor.gray100,
            tertiary = NyatetDuwitColor.amber300,
            onTertiary = NyatetDuwitColor.amber900,
            tertiaryContainer = NyatetDuwitColor.amber900,
            onTertiaryContainer = NyatetDuwitColor.amber100,
            error = NyatetDuwitColor.red300,
            onError = NyatetDuwitColor.red900,
            errorContainer = NyatetDuwitColor.red900,
            onErrorContainer = NyatetDuwitColor.red100,
            background = bg,
            onBackground = NyatetDuwitColor.gray100,
            surface = surface,
            onSurface = NyatetDuwitColor.gray100,
            surfaceVariant = surfaceContainerHigh,
            onSurfaceVariant = NyatetDuwitColor.gray300,
            outline = NyatetDuwitColor.gray500,
            outlineVariant = NyatetDuwitColor.gray700,
            inverseSurface = NyatetDuwitColor.gray100,
            inverseOnSurface = NyatetDuwitColor.gray900,
            inversePrimary = accents.primaryLight,
            surfaceTint = accents.primary,
        )
    } else {
        lightColorScheme(
            primary = accents.primary,
            onPrimary = NyatetDuwitColor.pureWhite,
            primaryContainer = accents.primaryLight,
            onPrimaryContainer = accents.primary,
            secondary = NyatetDuwitColor.gray600,
            onSecondary = NyatetDuwitColor.pureWhite,
            secondaryContainer = NyatetDuwitColor.gray100,
            onSecondaryContainer = NyatetDuwitColor.gray800,
            tertiary = NyatetDuwitColor.amber700,
            onTertiary = NyatetDuwitColor.pureWhite,
            tertiaryContainer = NyatetDuwitColor.amber100,
            onTertiaryContainer = NyatetDuwitColor.amber900,
            error = NyatetDuwitColor.red500,
            onError = NyatetDuwitColor.pureWhite,
            errorContainer = NyatetDuwitColor.red50,
            onErrorContainer = NyatetDuwitColor.red900,
            background = NyatetDuwitColor.gray25,
            onBackground = NyatetDuwitColor.gray950,
            surface = NyatetDuwitColor.pureWhite,
            onSurface = NyatetDuwitColor.gray950,
            surfaceVariant = NyatetDuwitColor.gray50,
            onSurfaceVariant = NyatetDuwitColor.gray500,
            outline = NyatetDuwitColor.gray200,
            outlineVariant = NyatetDuwitColor.gray100,
            inverseSurface = NyatetDuwitColor.gray900,
            inverseOnSurface = NyatetDuwitColor.gray100,
            inversePrimary = accents.primaryLight,
            surfaceTint = accents.primary,
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = NyatetDuwitTypography,
        shapes = NyatetDuwitShapes,
        content = content,
    )
}
