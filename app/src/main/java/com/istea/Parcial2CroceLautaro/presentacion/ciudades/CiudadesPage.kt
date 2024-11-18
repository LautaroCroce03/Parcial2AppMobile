package com.istea.Parcial2CroceLautaro.vista.ubicaciones

import Navegador
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.istea.Parcial2CroceLautaro.repository.RepositorioApi


@Composable
fun PantallaCiudades(
    navController: NavHostController
) {
    val vistaModelo: ControladorUbicacionesViewModel = viewModel(
        factory = FabricaControladorUbicaciones(
            fuenteDatos = RepositorioApi(),
            navegador = Navegador(navController)
        )
    )

    NavegadorUbicaciones(
        estadoActual = vistaModelo.estadoInterfaz,
        onAccion = { accion ->
            vistaModelo.procesarAccion(accion)
        }
    )
}
