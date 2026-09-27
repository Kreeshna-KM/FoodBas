package com.example.ui.theme

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
  primary = FoodBasRed,
  onPrimary = Color.White,
  primaryContainer = FoodBasRedLight,
  onPrimaryContainer = FoodBasRedDark,
  secondary = FoodBasOrange,
  onSecondary = Color.White,
  tertiary = FoodBasGreen,
  onTertiary = Color.White,
  background = FoodBasScreenBg,
  onBackground = FoodBasTextPrimary,
  surface = FoodBasCardBg,
  onSurface = FoodBasTextPrimary,
  surfaceVariant = FoodBasLightGray,
  onSurfaceVariant = FoodBasTextSecondary,
  outline = FoodBasBorder
)

private val DarkColorScheme = darkColorScheme(
  primary = FoodBasRed,
  onPrimary = Color.White,
  primaryContainer = FoodBasRedDark,
  onPrimaryContainer = Color.White,
  secondary = FoodBasOrange,
  onSecondary = Color.White,
  tertiary = FoodBasGreen,
  onTertiary = Color.White,
  background = Color(0xFF121212),
  onBackground = Color(0xFFEEEEEE),
  surface = Color(0xFF1E1E1E),
  onSurface = Color(0xFFEEEEEE),
  surfaceVariant = Color(0xFF2A2A2A),
  onSurfaceVariant = Color(0xFFB0B0B0),
  outline = Color(0xFF383838)
)

@Composable
fun FoodBasTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as? Activity)?.window
      if (window != null) {
        window.statusBarColor = Color.Transparent.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
      }
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
