package com.pmdm.lab.ut3.codelabs.codelab06_quiz

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

// El ViewModel no depende de Android: se prueba en la JVM, sin emulador
class QuizViewModelTest {

    private val preguntas = listOf(
        Pregunta("¿2 + 2?", listOf("3", "4"), "4"),
        Pregunta("¿3 + 3?", listOf("6", "7"), "6")
    )

    @Test
    fun alEmpezar_muestraLaPrimeraPreguntaSinPuntos() {
        val viewModel = QuizViewModel(preguntas)

        val estado = viewModel.uiState.value
        assertEquals("¿2 + 2?", estado.enunciado)
        assertEquals(1, estado.numeroPregunta)
        assertEquals(0, estado.puntos)
    }

    @Test
    fun elegirLaCorrecta_sumaDiezPuntos() {
        val viewModel = QuizViewModel(preguntas)

        viewModel.elegirOpcion("4")

        assertEquals(10, viewModel.uiState.value.puntos)
        assertEquals(true, viewModel.uiState.value.esCorrecta)
    }

    @Test
    fun noSePuedeCambiarLaRespuesta() {
        val viewModel = QuizViewModel(preguntas)

        viewModel.elegirOpcion("3")
        viewModel.elegirOpcion("4")

        assertEquals("3", viewModel.uiState.value.opcionElegida)
        assertEquals(0, viewModel.uiState.value.puntos)
    }

    @Test
    fun trasLaUltimaPregunta_elQuizTermina() {
        val viewModel = QuizViewModel(preguntas)

        viewModel.elegirOpcion("4")
        viewModel.siguiente()
        assertFalse(viewModel.uiState.value.terminado)
        viewModel.elegirOpcion("6")
        viewModel.siguiente()

        assertTrue(viewModel.uiState.value.terminado)
        assertEquals(20, viewModel.uiState.value.puntos)
    }
}
