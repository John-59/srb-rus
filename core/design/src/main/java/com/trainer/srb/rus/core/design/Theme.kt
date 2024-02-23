package com.trainer.srb.rus.core.design

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Colors.Purple80,
    secondary = Colors.PurpleGrey80,
    tertiary = Colors.Pink80,

    inverseSurface = Colors.Green,
    inverseOnSurface = Colors.Black,

    error = Colors.Red,
    onError = Colors.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Colors.PrimaryBlue,
    onPrimary = Colors.White,

    surface = Colors.White,
    onSurface = Colors.Black,

    inverseSurface = Colors.Green,
    inverseOnSurface = Colors.Black,

    background = Colors.White,
    onBackground = Colors.Black,

    outline = Colors.PrimaryBlue,

    surfaceVariant = Colors.Dark_40,
    
    error = Colors.Red,
    onError = Colors.White,

//    surfaceTint = Colors.White

    secondaryContainer = Colors.LightBlue

    /* Other default colors to override
    secondary = Colors.PurpleGrey40,
    tertiary = Colors.Pink40,
    background = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = !darkTheme
            controller.isAppearanceLightNavigationBars = !darkTheme
        }
    }

//    CompositionLocalProvider(
//        LocalContentColor provides Color.Red
//    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
//    }
}

//object MainTheme {
//    val colors = Colors
//
//    val typography = Typography
//
//    val sizes = Sizes
//}