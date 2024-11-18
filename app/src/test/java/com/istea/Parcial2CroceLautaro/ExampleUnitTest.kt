package com.istea.Parcial2CroceLautaro

import com.istea.Parcial2CroceLautaro.vista.ubicaciones.EstadoUbicaciones
import com.istea.Parcial2CroceLautaro.vista.ubicaciones.AccionUbicaciones
import com.istea.Parcial2CroceLautaro.vista.ubicaciones.ControladorUbicacionesViewModel
import com.istea.Parcial2CroceLautaro.vista.ubicaciones.FabricaControladorUbicaciones
import com.istea.Parcial2CroceLautaro.repository.RepositorioMock
import com.istea.Parcial2CroceLautaro.repository.RepositorioMockError
import com.istea.Parcial2CroceLautaro.router.MockNavegador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Test
import org.junit.Before
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class PruebaUnitariaEjemplo {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    // Dependencias mock
    private val repositorio = RepositorioMock()
    private val navegador = MockNavegador()

    @Before
    fun configurar() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun controladorUbicaciones_buscar_cor()  = runTest(timeout = 3.seconds) {
        val estadoEsperado = EstadoUbicaciones.ListaResultado(listOf(repositorio.cordoba))

        val fabrica = FabricaControladorUbicaciones(repositorio, navegador)
        val vistaModelo = fabrica.create(ControladorUbicacionesViewModel::class.java)

        launch(Dispatchers.Main) {
            vistaModelo.procesarAccion(AccionUbicaciones.BuscarUbicacion("cor"))
            delay(1.milliseconds)
            assertEquals(estadoEsperado, vistaModelo.estadoInterfaz)
        }
    }

    @Test
    fun controladorUbicaciones_buscar_plata() = runTest(timeout = 3.seconds) {
        val estadoEsperado = EstadoUbicaciones.ListaResultado(listOf(repositorio.laPlata))

        val fabrica = FabricaControladorUbicaciones(repositorio, navegador)
        val vistaModelo = fabrica.create(ControladorUbicacionesViewModel::class.java)

        launch(Dispatchers.Main) {
            vistaModelo.procesarAccion(AccionUbicaciones.BuscarUbicacion("plata"))
            delay(1.milliseconds)
            assertEquals(estadoEsperado, vistaModelo.estadoInterfaz)
        }
    }

    @Test
    fun controladorUbicaciones_buscar_vacio() = runTest(timeout = 3.seconds) {
        val estadoEsperado = EstadoUbicaciones.NoDisponible

        val fabrica = FabricaControladorUbicaciones(repositorio, navegador)
        val vistaModelo = fabrica.create(ControladorUbicacionesViewModel::class.java)

        launch(Dispatchers.Main) {
            vistaModelo.procesarAccion(AccionUbicaciones.BuscarUbicacion("jojo"))
            delay(1.milliseconds)
            assertEquals(estadoEsperado, vistaModelo.estadoInterfaz)
        }
    }

    @Test
    fun controladorUbicaciones_buscar_error() = runTest(timeout = 3.seconds) {
        val repositorioError = RepositorioMockError()

        val fabrica = FabricaControladorUbicaciones(repositorioError, navegador)
        val vistaModelo = fabrica.create(ControladorUbicacionesViewModel::class.java)

        val estadoEsperado = EstadoUbicaciones.Fallo("Error al cargar datos")

        launch(Dispatchers.Main) {
            vistaModelo.procesarAccion(AccionUbicaciones.BuscarUbicacion("jojo"))
            delay(1.milliseconds)
            assertEquals(estadoEsperado, vistaModelo.estadoInterfaz)
        }
    }

    @Test
    fun pruebaEjemplo() {
        assertEquals(4, 2 + 2)
    }
}