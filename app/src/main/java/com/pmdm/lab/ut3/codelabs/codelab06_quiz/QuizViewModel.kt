package com.pmdm.lab.ut3.codelabs.codelab06_quiz

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Pregunta(
    val enunciado: String,
    val opciones: List<String>,
    val correcta: String
)

val preguntasQuiz = listOf(
    Pregunta(
        "¿Qué función recuerda un valor entre recomposiciones?",
        listOf("remember", "saveState", "keep", "retain"), "remember"
    ),
    Pregunta(
        "¿Qué composable muestra listas largas de forma eficiente?",
        listOf("Column", "LazyColumn", "RecyclerView", "ScrollView"), "LazyColumn"
    ),
    Pregunta(
        "¿Qué parámetro de Scaffold hay que aplicar al contenido?",
        listOf("innerPadding", "contentColor", "topBar", "modifier"), "innerPadding"
    ),
    Pregunta(
        "¿Dónde sobrevive el estado al girar la pantalla?",
        listOf("remember", "Una variable local", "ViewModel", "@Preview"), "ViewModel"
    ),
    Pregunta(
        "En Navigation 3, ¿qué es la pila de navegación?",
        listOf("Un XML", "Una lista de claves", "Un Fragment", "Un Intent"), "Una lista de claves"
    )
)

/**
 * Guarda el estado del quiz y las reglas del juego. Sobrevive al giro:
 * la Activity se recrea, pero el ViewModel sigue siendo el mismo.
 */
class QuizViewModel(
    private val preguntas: List<Pregunta> = preguntasQuiz
) : ViewModel() {

    // Privado y mutable: solo el ViewModel lo cambia
    private val _uiState = MutableStateFlow(estadoDePregunta(0, puntos = 0))
    // Público y de solo lectura: la pantalla lo observa
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private var indice = 0

    fun elegirOpcion(opcion: String) {
        // Si ya se ha respondido, no se puede cambiar la respuesta
        if (_uiState.value.opcionElegida != null) return

        val acierto = opcion == preguntas[indice].correcta
        _uiState.update { estado ->
            estado.copy(
                opcionElegida = opcion,
                esCorrecta = acierto,
                puntos = if (acierto) estado.puntos + 10 else estado.puntos
            )
        }
    }

    fun siguiente() {
        if (indice + 1 < preguntas.size) {
            indice++
            _uiState.update { estadoDePregunta(indice, puntos = it.puntos) }
        } else {
            _uiState.update { it.copy(terminado = true) }
        }
    }

    fun reiniciar() {
        indice = 0
        _uiState.value = estadoDePregunta(0, puntos = 0)
    }

    private fun estadoDePregunta(i: Int, puntos: Int): QuizUiState {
        val pregunta = preguntas[i]
        return QuizUiState(
            enunciado = pregunta.enunciado,
            opciones = pregunta.opciones,
            numeroPregunta = i + 1,
            totalPreguntas = preguntas.size,
            puntos = puntos
        )
    }
}
