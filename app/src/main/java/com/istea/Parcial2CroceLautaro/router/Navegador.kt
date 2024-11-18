import com.istea.Parcial2CroceLautaro.enrutador.navegador
import com.istea.Parcial2CroceLautaro.enrutador.RutaDestino
import android.annotation.SuppressLint
import androidx.navigation.NavHostController


class Navegador(
    val controladorNavegacion: NavHostController
) : navegador {
    @SuppressLint("DefaultLocale")
    override fun navegar(destino: RutaDestino) {
        when (destino) {
            RutaDestino.Ciudades -> controladorNavegacion.navigate(destino.identificador)
            is RutaDestino.Clima -> {
                val ruta = String.format(
                    format = "%s?lat=%f&lon=%f&nombre=%s",
                    destino.identificador, destino.latitud, destino.longitud, destino.nombre
                )
                controladorNavegacion.navigate(ruta)
            }
        }
    }
}