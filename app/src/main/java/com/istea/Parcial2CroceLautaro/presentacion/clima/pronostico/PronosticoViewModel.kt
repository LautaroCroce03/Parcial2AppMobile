package com.istea.Parcial2CroceLautaro.presentacion.clima.pronostico


import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.istea.Parcial2CroceLautaro.enrutador.navegador
import com.istea.Parcial2CroceLautaro.repository.Repositorio
import kotlinx.coroutines.launch

class PronosticoViewModel(
    private val repositorio: Repositorio,
    private val navegador: navegador,
    private val ciudadNombre: String
) : ViewModel() {

    var estadoUi by mutableStateOf<PronosticoEstado>(PronosticoEstado.Vacio)
        private set

    fun ejecutar(intencion: PronosticoIntencion) {
        when (intencion) {
            PronosticoIntencion.actualizarClima -> traerPronostico()
        }
    }

    private fun traerPronostico() {
        estadoUi = PronosticoEstado.Cargando
        viewModelScope.launch {
            try {
                val pronostico = repositorio.traerPronostico(ciudadNombre).filter {
                    // TODO: agregar lógica de filtrado
                    true
                }
                estadoUi = PronosticoEstado.Exitoso(pronostico)
            } catch (excepcion: Exception) {
                estadoUi = PronosticoEstado.Error(excepcion.localizedMessage ?: "Error desconocido")
            }
        }
    }
}

class PronosticoViewModelFactory(
    private val repositorio: Repositorio,
    private val navegador: navegador,
    private val ciudadNombre: String,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PronosticoViewModel::class.java)) {
            return PronosticoViewModel(repositorio, navegador, ciudadNombre) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}