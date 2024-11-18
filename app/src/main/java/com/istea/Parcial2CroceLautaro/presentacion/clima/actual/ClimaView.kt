package com.istea.Parcial2CroceLautaro.vista.clima.actual

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.istea.Parcial2CroceLautaro.ui.estilo.TemaClimaApp

@Composable
fun VistaClima(
    modificador: Modifier = Modifier,
    estado: EstadoClima,
    alEjecutar: (IntencionClima) -> Unit
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        alEjecutar(IntencionClima.Actualizar)
    }
    Column(
        modifier = modificador
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (estado) {
            is EstadoClima.Fallo -> VistaError(mensaje = estado.mensaje)
            is EstadoClima.Correcto -> DetalleClima(
                ciudad = estado.ciudad,
                temperatura = estado.temperatura,
                descripcion = estado.descripcion,
                sensacionTermica = estado.sensacionTermica
            )
            EstadoClima.SinDatos -> VistaCargando()
            EstadoClima.Cargando -> VistaVacia()
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun VistaVacia() {
    Text(text = "No hay datos disponibles", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
}

@Composable
fun VistaCargando() {
    Text(text = "Cargando...", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
}

@Composable
fun VistaError(mensaje: String) {
    Text(text = mensaje, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun DetalleClima(ciudad: String, temperatura: Double, descripcion: String, sensacionTermica: Double) {
    Column {
        Text(text = ciudad, style = MaterialTheme.typography.titleMedium)
        Text(text = "${temperatura}°", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        Text(text = descripcion, style = MaterialTheme.typography.bodyMedium)
        Text(text = "Sensación Térmica: ${sensacionTermica}°", style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true)
@Composable
fun VistaClimaPreviewVacia() {
    TemaClimaApp {
        VistaClima(estado = EstadoClima.SinDatos, alEjecutar = {})
    }
}

@Preview(showBackground = true)
@Composable
fun VistaClimaPreviewError() {
    TemaClimaApp {
        VistaClima(estado = EstadoClima.Fallo("Se produjo un error"), alEjecutar = {})
    }
}

@Preview(showBackground = true)
@Composable
fun VistaClimaPreviewExitoso() {
    TemaClimaApp {
        VistaClima(estado = EstadoClima.Correcto(ciudad = "Mendoza", temperatura = 20.0, descripcion = "Despejado", sensacionTermica = 18.0), alEjecutar = {})
    }
}