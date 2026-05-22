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

private val LightScheme = lightColorScheme(
    primary = NyatetDuwitColor.teal700,
    onPrimary = NyatetDuwitColor.pureWhite,
    primaryContainer = NyatetDuwitColor.teal100,
    onPrimaryContainer = NyatetDuwitColor.teal700,
    secondary = NyatetDuwitColor.gray600,
    onSecondary = NyatetDuwitColor.pureWhite,
    secondaryContainer = NyatetDuwitColor.gray100,
    onSecondaryContainer = NyatetDuwitColor.gray800,
    tertiary = NyatetDuwitColor.gold700,
    onTertiary = NyatetDuwitColor.pureWhite,
    tertiaryContainer = NyatetDuwitColor.gold100,
    onTertiaryContainer = NyatetDuwitColor.gold700,
    error = NyatetDuwitColor.red500,
    onError = NyatetDuwitColor.pureWhite,
    errorContainer = NyatetDuwitColor.red100,
    onErrorContainer = NyatetDuwitColor.red500,
    background = NyatetDuwitColor.gray50,
    onBackground = NyatetDuwitColor.gray900,
    surface = NyatetDuwitColor.pureWhite,
    onSurface = NyatetDuwitColor.gray900,
    surfaceVariant = NyatetDuwitColor.gray100,
    onSurfaceVariant = NyatetDuwitColor.gray500,
    outline = NyatetDuwitColor.gray300,
    outlineVariant = NyatetDuwitColor.gray200,
    inverseSurface = NyatetDuwitColor.gray800,
    inverseOnSurface = NyatetDuwitColor.gray100,
    inversePrimary = NyatetDuwitColor.teal200,
    surfaceTint = NyatetDuwitColor.teal700,
)

private val DarkScheme = darkColorScheme(
    primary = NyatetDuwitColor.teal200,
    onPrimary = NyatetDuwitColor.teal700,
    primaryContainer = NyatetDuwitColor.teal700,
    onPrimaryContainer = NyatetDuwitColor.teal100,
    secondary = NyatetDuwitColor.gray300,
    onSecondary = NyatetDuwitColor.gray800,
    secondaryContainer = NyatetDuwitColor.gray600,
    onSecondaryContainer = NyatetDuwitColor.gray100,
    tertiary = NyatetDuwitColor.gold300,
    onTertiary = NyatetDuwitColor.gold700,
    tertiaryContainer = NyatetDuwitColor.gold700,
    onTertiaryContainer = NyatetDuwitColor.gold100,
    error = NyatetDuwitColor.red100,
    onError = NyatetDuwitColor.red500,
    errorContainer = NyatetDuwitColor.red500,
    onErrorContainer = NyatetDuwitColor.red100,
    background = NyatetDuwitColor.gray900,
    onBackground = NyatetDuwitColor.gray100,
    surface = NyatetDuwitColor.gray800,
    onSurface = NyatetDuwitColor.gray100,
    surfaceVariant = NyatetDuwitColor.gray700,
    onSurfaceVariant = NyatetDuwitColor.gray300,
    outline = NyatetDuwitColor.gray500,
    outlineVariant = NyatetDuwitColor.gray700,
    inverseSurface = NyatetDuwitColor.gray100,
    inverseOnSurface = NyatetDuwitColor.gray800,
    inversePrimary = NyatetDuwitColor.teal200,
    surfaceTint = NyatetDuwitColor.teal200,
)

@Composable
fun NyatetDuwitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkScheme else LightScheme

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
