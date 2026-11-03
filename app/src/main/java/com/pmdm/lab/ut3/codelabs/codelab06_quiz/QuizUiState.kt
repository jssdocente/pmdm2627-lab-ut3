package com.pmdm.lab.ut3.codelabs.codelab06_quiz

/**
 * Todo lo que la pantalla necesita para dibujarse, en un único objeto inmutable.
 * La pantalla no calcula nada: solo lee estos valores.
 */
data class QuizUiState(
    val enunciado: String = "",
    val opciones: List<String> = emptyList(),
    val numeroPregunta: Int = 1,
    val totalPreguntas: Int = 0,
    val puntos: Int = 0,
    val opcionElegida: String? = null,
    val esCorrecta: Boolean? = null,
    val terminado: Boolean = false
) {
    // Propiedad derivada: se calcula a partir de las demás
    val progreso: Float
        get() = if (totalPreguntas > 0) numeroPregunta.toFloat() / totalPreguntas else 0f
}
