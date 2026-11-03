package com.pmdm.lab.ut3.ui.theme

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

private val LightColorScheme = lightColorScheme(
    primary = Turquesa40,
    onPrimary = Color.White,
    primaryContainer = TurquesaContainer90,
    onPrimaryContainer = OnTurquesaContainer10,
    secondary = Pizarra40,
    onSecondary = Color.White,
    secondaryContainer = PizarraContainer90,
    onSecondaryContainer = OnPizarraContainer10,
    tertiary = Coral40,
    onTertiary = Color.White,
    tertiaryContainer = CoralContainer90,
    onTertiaryContainer = OnCoralContainer10,
    background = Fondo98,
    onBackground = Texto10,
    surface = Fondo98,
    onSurface = Texto10,
    surfaceVariant = Variante90,
    onSurfaceVariant = OnVariante30,
    outline = Borde50,
    outlineVariant = BordeSuave80,
    surfaceContainerLowest = Contenedor100,
    surfaceContainerLow = Contenedor96,
    surfaceContainer = Contenedor94,
    surfaceContainerHigh = Contenedor92,
    surfaceContainerHighest = Contenedor90,
    inverseSurface = Invertido20,
    inverseOnSurface = OnInvertido95,
    inversePrimary = Turquesa80
)

private val DarkColorScheme = darkColorScheme(
    primary = Turquesa80,
    onPrimary = OnTurquesa20,
    primaryContainer = TurquesaContainer30,
    onPrimaryContainer = TurquesaContainer90,
    secondary = Pizarra80,
    onSecondary = OnPizarra20,
    secondaryContainer = PizarraContainer30,
    onSecondaryContainer = PizarraContainer90,
    tertiary = Coral80,
    onTertiary = OnCoral20,
    tertiaryContainer = CoralContainer30,
    onTertiaryContainer = CoralContainer90,
    background = Fondo6,
    onBackground = Texto90,
    surface = Fondo6,
    onSurface = Texto90,
    surfaceVariant = Variante30,
    onSurfaceVariant = OnVariante80,
    outline = Borde60,
    outlineVariant = BordeSuave30,
    surfaceContainerLowest = Contenedor4,
    surfaceContainerLow = Contenedor10,
    surfaceContainer = Contenedor12,
    surfaceContainerHigh = Contenedor17,
    surfaceContainerHighest = Contenedor22,
    inverseSurface = Texto90,
    inverseOnSurface = Invertido20,
    inversePrimary = Turquesa40
)

@Composable
fun LabUt3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Desactivado: queremos ver siempre NUESTRA paleta. Con true, en Android 12+
    // los colores salen del fondo de pantalla del usuario y la paleta se ignora.
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
        shapes = Shapes,
        content = content
    )
}
