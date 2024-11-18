package com.istea.Parcial2CroceLautaro.vista.ubicaciones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.istea.Parcial2CroceLautaro.repository.modelos.Ciudad
import com.istea.Parcial2CroceLautaro.ui.theme.*

@Composable
fun NavegadorUbicaciones(
    modifier: Modifier = Modifier,
    estadoActual: EstadoUbicaciones,
    onAccion: (AccionUbicaciones) -> Unit
) {
    var textoPorBuscar by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(12.dp)
            .background(VerdeClaro)
    ) {
        BarraBusquedaCiudad(textoPorBuscar, alCambiar = {
            textoPorBuscar = it
            onAccion(AccionUbicaciones.BuscarUbicacion(textoPorBuscar))
        })

        DesplegarEstado(estadoActual, onAccion)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraBusquedaCiudad(texto: String, alCambiar: (String) -> Unit) {
    TextField(
        value = texto,
        onValueChange = alCambiar,
        label = { Text("Escriba el nombre de una ciudad") },
        modifier = Modifier
            .fillMaxWidth()
            .background(VerdeGrisClaro),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = VerdeOlivaClaro,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun DesplegarEstado(
    estadoActual: EstadoUbicaciones,
    onAccion: (AccionUbicaciones) -> Unit
) {
    when (estadoActual) {
        EstadoUbicaciones.Cargando -> IndicadorCargando()
        is EstadoUbicaciones.Fallo -> MostrarMensajeError(estadoActual.mensajeError)
        is EstadoUbicaciones.ListaResultado -> PresentarListaCiudades(estadoActual.listaCiudades, onAccion)
        EstadoUbicaciones.NoDisponible -> SinDatosDisponiblesTexto()
    }
}

@Composable
fun IndicadorCargando() {
    Text(
        text = "Espere un momento...",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = VerdeOscuro
    )
}

@Composable
fun MostrarMensajeError(mensajeError: String) {
    Text(
        text = mensajeError,
        color = Color.Red,
        modifier = Modifier
            .padding(8.dp)
            .background(VerdeGrisOscuro)
    )
}

@Composable
fun SinDatosDisponiblesTexto() {
    Text(
        text = "No se hallaron resultados.",
        color = VerdeGrisOscuro,
        modifier = Modifier.padding(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresentarListaCiudades(listaCiudades: List<Ciudad>, alSeleccionar: (AccionUbicaciones) -> Unit) {
    LazyColumn {
        items(listaCiudades) { ciudad ->
            TarjetaCiudad(ciudad) {
                alSeleccionar(AccionUbicaciones.ElegirUbicacion(ciudad))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarjetaCiudad(ciudad: Ciudad, alPresionar: () -> Unit) {
    Card(
        onClick = alPresionar,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = VerdeGrisClaro
        )
    ) {
        Text(
            text = ciudad.name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium.copy(color = VerdeOscuro)
        )
    }
}