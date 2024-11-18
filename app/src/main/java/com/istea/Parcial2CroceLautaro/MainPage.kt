package com.istea.Parcial2CroceLautaro

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.istea.Parcial2CroceLautaro.vista.ubicaciones.PantallaCiudades
import com.istea.Parcial2CroceLautaro.enrutador.RutaDestino
import com.istea.Parcial2CroceLautaro.presentacion.clima.PaginaClima

@Preview
@Composable
fun PaginaPrincipal() {
    val controlador = rememberNavController()
    ConfiguracionNavHost(controlador)
}

@Composable
fun ConfiguracionNavHost(controlador: NavHostController) {
    NavHost(
        navController = controlador,
        startDestination = RutaDestino.Ciudades.identificador
    ) {
        composable(route = RutaDestino.Ciudades.identificador) {
            PantallaCiudades(controlador)
        }
        composable(
            route = "clima?lat={lat}&lon={lon}&nombre={nombre}",
            arguments = listOf(
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lon") { type = NavType.FloatType },
                navArgument("nombre") { type = NavType.StringType }
            )
        ) {
            val latitud = it.arguments?.getFloat("lat") ?: 0.0f
            val longitud = it.arguments?.getFloat("lon") ?: 0.0f
            val nombreCiudad = it.arguments?.getString("nombre") ?: ""
            PaginaClima(controlador, latitud = latitud, longitud = longitud, ciudadNombre = nombreCiudad)
        }
    }
}