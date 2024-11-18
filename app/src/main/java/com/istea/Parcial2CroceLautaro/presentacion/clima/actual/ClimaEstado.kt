package com.istea.Parcial2CroceLautaro.vista.clima.actual

sealed class EstadoClima {

    data class Correcto(
        val ciudad: String = "",
        val temperatura: Double = 0.0,
        val descripcion: String = "",
        val sensacionTermica: Double = 0.0,
    ) : EstadoClima()

    data class Fallo(
        val mensaje: String = "",
    ) : EstadoClima()

    object SinDatos : EstadoClima()

    object Cargando : EstadoClima()
}