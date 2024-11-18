package com.istea.Parcial2CroceLautaro.presentacion.clima

import Navegador
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.istea.Parcial2CroceLautaro.presentacion.clima.pronostico.PronosticoView
import com.istea.Parcial2CroceLautaro.presentacion.clima.pronostico.PronosticoViewModel
import com.istea.Parcial2CroceLautaro.presentacion.clima.pronostico.PronosticoViewModelFactory
import com.istea.Parcial2CroceLautaro.repository.RepositorioApi
import com.istea.Parcial2CroceLautaro.vista.clima.actual.FabricaModeloVistaClima
import com.istea.Parcial2CroceLautaro.vista.clima.actual.ModeloVistaClima
import com.istea.Parcial2CroceLautaro.vista.clima.actual.VistaClima
import com.istea.Parcial2CroceLautaro.ui.theme.*


@Composable
fun PaginaClima(
    controladorNavegacion: NavHostController,
    latitud: Float,
    longitud: Float,
    ciudadNombre: String
) {
    val climaViewModel: ModeloVistaClima = obtenerClimaViewModel(
        controladorNavegacion, latitud, longitud, ciudadNombre
    )
    val pronosticoViewModel: PronosticoViewModel = obtenerPronosticoViewModel(
        controladorNavegacion, ciudadNombre
    )

    Column {
        VistaClima(
            estado = climaViewModel.estadoInterfaz,
            alEjecutar = climaViewModel::procesarAccion,
            modificador = Modifier.background(VerdeClaro)
        )
        PronosticoView(
            state = pronosticoViewModel.estadoUi,
            onAction = pronosticoViewModel::ejecutar,
            modifier = Modifier.background(VerdeGrisClaro)
        )
    }
}

@Composable
private fun obtenerClimaViewModel(
    controladorNavegacion: NavHostController,
    latitud: Float,
    longitud: Float,
    ciudadNombre: String
): ModeloVistaClima {
    val repositorio = RepositorioApi()
    val navegar = Navegador(controladorNavegacion)
    return viewModel(factory = FabricaModeloVistaClima(repositorio, navegar, latitud, longitud, ciudadNombre))
}

@Composable
private fun obtenerPronosticoViewModel(
    controladorNavegacion: NavHostController,
    ciudadNombre: String
): PronosticoViewModel {
    val repositorio = RepositorioApi()
    val navegar = Navegador(controladorNavegacion)
    return viewModel(factory = PronosticoViewModelFactory(repositorio, navegar, ciudadNombre))
}