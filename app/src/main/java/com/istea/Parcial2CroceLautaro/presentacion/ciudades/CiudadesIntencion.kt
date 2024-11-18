package com.istea.Parcial2CroceLautaro.vista.ubicaciones

import com.istea.Parcial2CroceLautaro.repository.modelos.Ciudad

sealed class AccionUbicaciones {
    data class BuscarUbicacion(val ciudad: String) : AccionUbicaciones()
    data class ElegirUbicacion(val ciudad: Ciudad) : AccionUbicaciones()
}