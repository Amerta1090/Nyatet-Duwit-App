package com.nyatetduwit.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val NyatetDuwitFont = FontFamily.Default

val NyatetDuwitTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Light,
        fontSize = 54.sp,
        lineHeight = 60.sp,
        letterSpacing = (-1).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Light,
        fontSize = 42.sp,
        lineHeight = 50.sp,
        letterSpacing = (-0.6).sp,
    ),
    displaySmall = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        lineHeight = 42.sp,
        letterSpacing = (-0.2).sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.4).sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.2).sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.15.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.2.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.2.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.3.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = NyatetDuwitFont,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 13.sp,
        letterSpacing = 0.4.sp,
    ),
)
