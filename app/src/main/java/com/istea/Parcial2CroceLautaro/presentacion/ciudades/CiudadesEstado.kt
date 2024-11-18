package com.istea.Parcial2CroceLautaro.vista.ubicaciones

import com.istea.Parcial2CroceLautaro.repository.modelos.Ciudad

sealed class EstadoUbicaciones {
    object NoDisponible : EstadoUbicaciones()
    object Cargando : EstadoUbicaciones()
    data class ListaResultado(val listaCiudades: List<Ciudad>) : EstadoUbicaciones()
    data class Fallo(val mensajeError: String) : EstadoUbicaciones()
}