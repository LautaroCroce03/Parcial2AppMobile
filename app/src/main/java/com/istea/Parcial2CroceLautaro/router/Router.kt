package com.istea.Parcial2CroceLautaro.enrutador

interface navegador {
    fun navegar(destino: RutaDestino)
}

sealed class RutaDestino(val identificador: String) {
    object Ciudades : RutaDestino("ciudades")
    data class Clima(val latitud: Float, val longitud: Float, val nombre: String) : RutaDestino("clima")
}