package com.istea.Parcial2CroceLautaro.vista.ubicaciones

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.istea.Parcial2CroceLautaro.repository.Repositorio
import com.istea.Parcial2CroceLautaro.repository.modelos.Ciudad
import com.istea.Parcial2CroceLautaro.enrutador.RutaDestino
import com.istea.Parcial2CroceLautaro.enrutador.navegador
import kotlinx.coroutines.launch

class ControladorUbicacionesViewModel(
    private val fuenteDatos: Repositorio,
    private val navegador: navegador
) : ViewModel() {

    var estadoInterfaz by mutableStateOf<EstadoUbicaciones>(EstadoUbicaciones.NoDisponible)
        private set

    private var listaUbicaciones: List<Ciudad> = emptyList()
        private set

    fun procesarAccion(accion: AccionUbicaciones) {
        when (accion) {
            is AccionUbicaciones.BuscarUbicacion -> buscarUbicacion(accion.ciudad)
            is AccionUbicaciones.ElegirUbicacion -> navegarA(accion.ciudad)
        }
    }

    private fun buscarUbicacion(ciudad: String) {
        estadoInterfaz = EstadoUbicaciones.Cargando
        viewModelScope.launch {
            try {
                listaUbicaciones = fuenteDatos.buscarCiudad(ciudad)
                estadoInterfaz = if (listaUbicaciones.isEmpty()) {
                    EstadoUbicaciones.NoDisponible
                } else {
                    EstadoUbicaciones.ListaResultado(listaUbicaciones)
                }
            } catch (excepcion: Exception) {
                estadoInterfaz = EstadoUbicaciones.Fallo(excepcion.message ?: "Error al cargar datos")
            }
        }
    }

    private fun navegarA(ciudadSeleccionada: Ciudad) {
        val destino = RutaDestino.Clima(
            latitud = ciudadSeleccionada.lat,
            longitud = ciudadSeleccionada.lon,
            nombre = ciudadSeleccionada.name
        )
        navegador.navegar(destino)
    }
}

class FabricaControladorUbicaciones(
    private val fuenteDatos: Repositorio,
    private val navegador: navegador
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ControladorUbicacionesViewModel::class.java)) {
            return ControladorUbicacionesViewModel(fuenteDatos, navegador) as T
        }
        throw IllegalArgumentException("Clase de ViewModel no reconocida")
    }
}