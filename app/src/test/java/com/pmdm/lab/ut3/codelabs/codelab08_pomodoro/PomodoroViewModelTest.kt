package com.pmdm.lab.ut3.codelabs.codelab08_pomodoro

import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

// Tiempo virtual: los delay no esperan de verdad. El temporizador es un bucle infinito,
// así que cada test lo para al final y ninguno usa advanceUntilIdle(), que no terminaría
class PomodoroViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val viewModel = PomodoroViewModel(segundosTrabajo = 3, segundosDescanso = 2, esperaCambioMs = 1_000)
    private val estado get() = viewModel.uiState.value

    // advanceTimeBy no ejecuta lo programado justo en ese instante: runCurrent sí
    private fun TestScope.avanzar(ms: Long) {
        advanceTimeBy(ms)
        runCurrent()
    }

    @Test
    fun `al iniciar descuenta un segundo por cada segundo`() = runTest {
        viewModel.onIniciar()

        avanzar(2_000)

        assertEquals(1, estado.segundosRestantes)
        viewModel.onPausar()
    }

    @Test
    fun `iniciar dos veces no lanza dos temporizadores`() = runTest {
        viewModel.onIniciar()
        viewModel.onIniciar()

        avanzar(1_000)

        assertEquals(2, estado.segundosRestantes)
        viewModel.onPausar()
    }

    @Test
    fun `pausar detiene el tiempo`() = runTest {
        viewModel.onIniciar()
        avanzar(1_000)

        viewModel.onPausar()
        avanzar(5_000)

        assertEquals(2, estado.segundosRestantes)
        assertFalse(estado.enMarcha)
    }

    @Test
    fun `al llegar a cero bloquea durante la espera y pasa a la siguiente fase`() = runTest {
        viewModel.onIniciar()

        avanzar(3_000)
        assertTrue(estado.cambiandoDeFase)

        // Durante la espera, pausar no hace nada
        viewModel.onPausar()
        assertTrue(estado.enMarcha)

        avanzar(1_000)
        assertFalse(estado.cambiandoDeFase)
        assertEquals(FasePomodoro.DESCANSO, estado.fase)
        assertEquals(2, estado.segundosRestantes)
        viewModel.onPausar()
    }

    @Test
    fun `un ciclo se completa al terminar el descanso`() = runTest {
        viewModel.onIniciar()

        // 3 s de trabajo + 1 s de espera + 2 s de descanso + 1 s de espera
        avanzar(7_000)

        assertEquals(1, estado.ciclosCompletados)
        assertEquals(FasePomodoro.TRABAJO, estado.fase)
        viewModel.onPausar()
    }

    @Test
    fun `al salir de la app se para y al volver continua`() = runTest {
        viewModel.onIniciar()
        avanzar(1_000)

        viewModel.onSalirDeLaApp()
        avanzar(10_000)
        assertEquals(2, estado.segundosRestantes)
        assertTrue(estado.enMarcha)

        viewModel.onVolverALaApp()
        avanzar(1_000)
        assertEquals(1, estado.segundosRestantes)
        viewModel.onPausar()
    }

    @Test
    fun `al volver a la app no arranca si el usuario lo habia pausado`() = runTest {
        viewModel.onIniciar()
        avanzar(1_000)
        viewModel.onPausar()

        viewModel.onSalirDeLaApp()
        viewModel.onVolverALaApp()
        avanzar(5_000)

        assertEquals(2, estado.segundosRestantes)
    }

    @Test
    fun `reiniciar vuelve al estado inicial y para el temporizador`() = runTest {
        viewModel.onIniciar()
        avanzar(2_000)

        viewModel.onReiniciar()
        avanzar(5_000)

        assertEquals(3, estado.segundosRestantes)
        assertFalse(estado.enMarcha)
    }
}
