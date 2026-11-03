package com.pmdm.lab.ut3.retos.reto02_wordle

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pmdm.lab.ut3.retos.RetoPendiente

/*
 * Reto 3.02 · Wordle
 * Enunciado completo: apuntes, UT3 › 3.6 Laboratorio › Reto 3.02.
 *
 * TODO 1. Dominio (sin Compose): enum class Pista { ACIERTO, PRESENTE, AUSENTE } y una
 *         función pura evaluar(secreta: String, intento: String): List<Pista>.
 *         Pruébala con tests unitarios (ojo con las letras repetidas).
 * TODO 2. WordleUiState: los intentos ya hechos (con sus pistas), el intento que se está
 *         escribiendo, el estado de la partida y el estado de cada letra del teclado.
 * TODO 3. WordleViewModel: escribirLetra(), borrar(), enviar() y reiniciar().
 *         Solo se envían palabras de 5 letras; hay 6 intentos.
 * TODO 4. El tablero: 6 filas de 5 casillas (Column de Row). Cada casilla, una Surface
 *         con bordes redondeados y la letra centrada. Colores del tema:
 *         ACIERTO -> primaryContainer, PRESENTE -> tertiaryContainer, AUSENTE -> surfaceVariant.
 * TODO 5. El teclado: las letras en filas, más los botones Borrar y Enviar.
 *         Las teclas también se pintan con la mejor pista conseguida.
 */
@Composable
fun WordleScreen(modifier: Modifier = Modifier) {
    RetoPendiente(titulo = "🟩 Wordle", modifier = modifier)
}
