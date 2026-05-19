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

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4F6B4E),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD0F2CE),
    onPrimaryContainer = Color(0xFF0A200E),
    secondary = Color(0xFF546250),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD7E7D0),
    onSecondaryContainer = Color(0xFF121F10),
    tertiary = Color(0xFF38666B),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFBCEBF2),
    onTertiaryContainer = Color(0xFF002023),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFDFDF6),
    onBackground = Color(0xFF1A1C19),
    surface = Color(0xFFFDFDF6),
    onSurface = Color(0xFF1A1C19),
    surfaceVariant = Color(0xFFDFE4D7),
    onSurfaceVariant = Color(0xFF43483F),
    outline = Color(0xFF73796E),
    outlineVariant = Color(0xFFC3C8BC),
    inverseSurface = Color(0xFF2F312D),
    inverseOnSurface = Color(0xFFF1F1EC),
    inversePrimary = Color(0xFFB5D6B3),
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFB5D6B3),
    onPrimary = Color(0xFF213A23),
    primaryContainer = Color(0xFF375138),
    onPrimaryContainer = Color(0xFFD0F2CE),
    secondary = Color(0xFFBBCBB5),
    onSecondary = Color(0xFF263424),
    secondaryContainer = Color(0xFF3C4A3A),
    onSecondaryContainer = Color(0xFFD7E7D0),
    tertiary = Color(0xFFA0CFD5),
    onTertiary = Color(0xFF00363B),
    tertiaryContainer = Color(0xFF1E4E53),
    onTertiaryContainer = Color(0xFFBCEBF2),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF1A1C19),
    onBackground = Color(0xFFE2E3DE),
    surface = Color(0xFF1A1C19),
    onSurface = Color(0xFFE2E3DE),
    surfaceVariant = Color(0xFF43483F),
    onSurfaceVariant = Color(0xFFC3C8BC),
    outline = Color(0xFF8D9387),
    outlineVariant = Color(0xFF43483F),
    inverseSurface = Color(0xFFE2E3DE),
    inverseOnSurface = Color(0xFF2F312D),
    inversePrimary = Color(0xFF4F6B4E),
)

@Composable
fun NyatetDuwitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

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
        typography = Typography,
        content = content
    )
}
