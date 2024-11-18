package com.istea.Parcial2CroceLautaro.vista.clima.actual

import Navegador
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.istea.Parcial2CroceLautaro.repository.Repositorio
import kotlinx.coroutines.launch

class ModeloVistaClima(
    private val repositorio: Repositorio,
    private val navegador: Navegador,
    private val latitud: Float,
    private val longitud: Float,
    private val ciudad: String
) : ViewModel() {

    var estadoInterfaz by mutableStateOf<EstadoClima>(EstadoClima.SinDatos)
        private set

    fun procesarAccion(accion: IntencionClima) {
        when (accion) {
            IntencionClima.Actualizar -> cargarClima()
        }
    }

    private fun cargarClima() {
        estadoInterfaz = EstadoClima.Cargando
        viewModelScope.launch {
            try {
                val datosClima = repositorio.traerClima(latitud, longitud)
                estadoInterfaz = EstadoClima.Correcto(
                    ciudad = datosClima.name,
                    temperatura = datosClima.main.temp,
                    descripcion = datosClima.weather.first().description,
                    sensacionTermica = datosClima.main.feels_like
                )
            } catch (error: Exception) {
                estadoInterfaz = EstadoClima.Fallo(error.localizedMessage ?: "Error inesperado")
            }
        }
    }
}

class FabricaModeloVistaClima(
    private val repositorio: Repositorio,
    private val navegador: Navegador,
    private val latitud: Float,
    private val longitud: Float,
    private val ciudad: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(claseModelo: Class<T>): T {
        if (claseModelo.isAssignableFrom(ModeloVistaClima::class.java)) {
            return ModeloVistaClima(repositorio, navegador, latitud, longitud, ciudad) as T
        }
        throw IllegalArgumentException("Clase de ViewModel no reconocida")
    }
}