package com.istea.Parcial2CroceLautaro.ui.estilo

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
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
import com.istea.Parcial2CroceLautaro.ui.theme.*

private val esquemaOscuro = darkColorScheme(
    primary = VerdeOscuro,
    secondary = VerdeGrisOscuro,
    tertiary = VerdeOlivaOscuro
)

private val esquemaClaro = lightColorScheme(
    primary = VerdeClaro,
    secondary = VerdeGrisClaro,
    tertiary = VerdeOlivaClaro
)

@Composable
fun TemaClimaApp(
    temaOscuro: Boolean = isSystemInDarkTheme(),
    colorDinamico: Boolean = true,
    contenido: @Composable () -> Unit
) {
    val esquemaColores = obtenerEsquemaColor(temaOscuro, colorDinamico)

    configurarBarraEstado(esquemaColores, temaOscuro)

    MaterialTheme(
        colorScheme = esquemaColores,
        typography = Tipografia,
        content = contenido
    )
}

@Composable
private fun obtenerEsquemaColor(temaOscuro: Boolean, colorDinamico: Boolean) = when {
    colorDinamico && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val contexto = LocalContext.current
        if (temaOscuro) dynamicDarkColorScheme(contexto) else dynamicLightColorScheme(contexto)
    }
    temaOscuro -> esquemaOscuro
    else -> esquemaClaro
}

@Composable
private fun configurarBarraEstado(esquema: ColorScheme, temaOscuro: Boolean) {
    val vista = LocalView.current
    if (!vista.isInEditMode) {
        SideEffect {
            val ventana = (vista.context as Activity).window
            ventana.statusBarColor = esquema.primary.toArgb()
            WindowCompat.getInsetsController(ventana, vista).isAppearanceLightStatusBars = temaOscuro
        }
    }
}