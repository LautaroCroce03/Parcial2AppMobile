package com.istea.Parcial2CroceLautaro.repository

import com.istea.Parcial2CroceLautaro.repository.modelos.Clima
import com.istea.Parcial2CroceLautaro.repository.modelos.ForecastDTO
import com.istea.Parcial2CroceLautaro.repository.modelos.ListForecast
import com.istea.Parcial2CroceLautaro.repository.modelos.Ciudad
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class RepositorioApi : Repositorio {

    private val apiKey = "c5eb13732105d1a7d0727a770e9855a1"
    private val cliente = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override suspend fun buscarCiudad(ciudad: String): List<Ciudad> {
        val url = "https://api.openweathermap.org/geo/1.0/direct"
        return obtenerRespuesta(url, "q" to ciudad, "limit" to 100)
    }

    override suspend fun traerClima(lat: Float, lon: Float): Clima {
        val url = "https://api.openweathermap.org/data/2.5/weather"
        return obtenerRespuesta(url, "lat" to lat, "lon" to lon, "units" to "metric")
    }

    override suspend fun traerPronostico(nombre: String): List<ListForecast> {
        val url = "https://api.openweathermap.org/data/2.5/forecast"
        val respuesta = obtenerRespuesta<ForecastDTO>(url, "q" to nombre, "units" to "metric")
        return respuesta.list
    }

    private suspend inline fun <reified T> obtenerRespuesta(
        url: String,
        vararg parametros: Pair<String, Any>
    ): T {
        val respuesta = cliente.get(url) {
            parametros.forEach { parameter(it.first, it.second) }
            parameter("appid", apiKey)
        }
        if (respuesta.status == HttpStatusCode.OK) {
            return respuesta.body()
        } else {
            throw RuntimeException("Error en la solicitud: ${respuesta.status}")
        }
    }
}
