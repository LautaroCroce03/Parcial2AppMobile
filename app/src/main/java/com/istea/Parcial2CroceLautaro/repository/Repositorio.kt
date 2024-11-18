package com.istea.Parcial2CroceLautaro.repository

import com.istea.Parcial2CroceLautaro.repository.modelos.Ciudad
import com.istea.Parcial2CroceLautaro.repository.modelos.Clima
import com.istea.Parcial2CroceLautaro.repository.modelos.ListForecast

interface Repositorio {
    suspend fun buscarCiudad(ciudad: String): List<Ciudad>
    suspend fun traerClima(lat: Float, lon: Float): Clima
    suspend fun traerPronostico(nombre: String): List<ListForecast>
}