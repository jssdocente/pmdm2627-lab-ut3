package com.pmdm.lab.ut3.codelabs.codelab08_pomodoro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Las duraciones llegan por el constructor: en clase usamos segundos para no esperar
// 25 minutos, y los tests pueden elegir las suyas
class PomodoroViewModel(
    private val segundosTrabajo: Int = 25,
    private val segundosDescanso: Int = 5,
    private val esperaCambioMs: Long = 2_000
) : ViewModel() {

    private val _uiState = MutableStateFlow(estadoInicial())
    val uiState: StateFlow<PomodoroUiState> = _uiState.asStateFlow()

    // La corrutina que cuenta. Guardarla es lo que permite pararla
    private var temporizador: Job? = null

    fun onIniciar() {
        val estado = _uiState.value
        if (estado.enMarcha || estado.cambiandoDeFase) return // regla: no se lanza dos veces

        _uiState.update { it.copy(enMarcha = true) }
        arrancar()
    }

    fun onPausar() {
        if (_uiState.value.cambiandoDeFase) return

        temporizador?.cancel()
        _uiState.update { it.copy(enMarcha = false) }
    }

    fun onReiniciar() {
        if (_uiState.value.cambiandoDeFase) return

        temporizador?.cancel()
        _uiState.value = estadoInicial()
    }

    // Al salir con el botón de inicio se para la corrutina, pero enMarcha sigue a true:
    // así, al volver, se sabe si hay que continuar o si el usuario lo había pausado
    fun onSalirDeLaApp() {
        temporizador?.cancel()
    }

    fun onVolverALaApp() {
        if (_uiState.value.enMarcha) arrancar()
    }

    private fun arrancar() {
        temporizador?.cancel() // nunca dos corrutinas contando a la vez
        temporizador = viewModelScope.launch {
            while (true) {
                if (_uiState.value.segundosRestantes > 0) {
                    delay(1_000) // suspende sin bloquear el hilo principal
                    _uiState.update { it.copy(segundosRestantes = it.segundosRestantes - 1) }
                } else {
                    cambiarDeFase()
                }
            }
        }
    }

    private suspend fun cambiarDeFase() {
        // El bloqueo se activa ANTES de esperar y se quita al terminar la espera
        _uiState.update { it.copy(cambiandoDeFase = true) }
        delay(esperaCambioMs)
        _uiState.update { estado ->
            val fase = estado.fase.siguiente()
            estado.copy(
                fase = fase,
                segundosRestantes = duracion(fase),
                segundosDeLaFase = duracion(fase),
                cambiandoDeFase = false,
                // Un ciclo se completa al terminar el descanso
                ciclosCompletados = estado.ciclosCompletados +
                    if (estado.fase == FasePomodoro.DESCANSO) 1 else 0
            )
        }
    }

    private fun duracion(fase: FasePomodoro): Int = when (fase) {
        FasePomodoro.TRABAJO -> segundosTrabajo
        FasePomodoro.DESCANSO -> segundosDescanso
    }

    private fun estadoInicial() = PomodoroUiState(
        fase = FasePomodoro.TRABAJO,
        segundosRestantes = segundosTrabajo,
        segundosDeLaFase = segundosTrabajo
    )
}
