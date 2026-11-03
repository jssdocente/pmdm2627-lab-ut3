package com.pmdm.lab.ut3.retos.reto01_ahorcado.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pmdm.lab.ut3.retos.RetoPendiente

/*
 * Reto 3.01 · El Ahorcado
 * Enunciado completo: apuntes, UT3 › 3.6 Laboratorio › Reto 3.01.
 *
 * La lógica del juego ya está hecha (paquete domain, con sus tests): no la cambies.
 *
 * TODO 1. Crea AhorcadoUiState: palabra enmascarada ("K _ T _ _ N"), palabra secreta,
 *         vidas restantes y máximas, letras probadas (Set<Char>) y estado de la partida.
 * TODO 2. Crea AhorcadoViewModel con un MutableStateFlow privado y un StateFlow público.
 *         Recibe la lista de palabras por el constructor (así podrás probarlo):
 *         class AhorcadoViewModel(palabras: List<String> = ...) : ViewModel()
 *         Funciones: intentarLetra(letra: Char) y reiniciar(). Usa procesarIntento() del dominio.
 * TODO 3. Escribe AhorcadoContent, sin estado, con estos componentes:
 *         - VidasIndicator: LinearProgressIndicator y el texto "Vidas restantes: 4 / 6".
 *         - PalabraOculta: una casilla por letra.
 *         - TecladoVirtual: un FilledTonalButton por letra; las ya probadas, deshabilitadas.
 *         - Un AlertDialog al ganar o perder, con el botón "Jugar de nuevo".
 * TODO 4. AhorcadoScreen obtiene el ViewModel con viewModel() y observa el estado
 *         con collectAsStateWithLifecycle().
 * TODO 5. Test de interfaz en androidTest: con la palabra "KOTLIN", pulsar la Z
 *         muestra "Vidas restantes: 5 / 6".
 */
@Composable
fun AhorcadoScreen(modifier: Modifier = Modifier) {
    RetoPendiente(titulo = "🪢 El Ahorcado", modifier = modifier)
}
