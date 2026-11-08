package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = ChurchNavySecondary,
    onPrimaryContainer = Color.White,
    secondary = DarkSecondary,
    onSecondary = Color(0xFF281E00),
    secondaryContainer = Color(0xFF4A3E00),
    onSecondaryContainer = Color(0xFFFFECC0),
    tertiary = ChurchTeal,
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFB8C7DC),
    outline = DarkOutline
)

private val LightColorScheme = lightColorScheme(
    primary = ChurchNavyPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = Color(0xFFE2EAF8),
    onPrimaryContainer = ChurchNavyPrimary,
    secondary = ChurchGold,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFFFF3CD),
    onSecondaryContainer = Color(0xFF5D4037),
    tertiary = ChurchTeal,
    background = LightBackground,
    onBackground = LightOnSurface,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = Color(0xFF4B5563),
    outline = LightOutline
)

@Composable
fun ChurchAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
