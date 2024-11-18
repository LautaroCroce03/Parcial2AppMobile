package com.istea.Parcial2CroceLautaro.router

import com.istea.Parcial2CroceLautaro.enrutador.RutaDestino
import com.istea.Parcial2CroceLautaro.enrutador.navegador

class MockNavegador : navegador {
    override fun navegar(destino: RutaDestino) {
        println("Navegando a: ${destino.identificador}")
    }
}