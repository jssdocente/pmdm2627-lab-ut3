package com.pmdm.lab.ut3.codelabs.codelab08_pomodoro

// Las dos fases se alternan. La duración es un parámetro del ViewModel
enum class FasePomodoro(val titulo: String) {
    TRABAJO("Trabajo"),
    DESCANSO("Descanso");

    fun siguiente(): FasePomodoro = if (this == TRABAJO) DESCANSO else TRABAJO
}

// Todo lo que pinta la pantalla. El Job no está aquí: no es un dato que se muestre
data class PomodoroUiState(
    val fase: FasePomodoro = FasePomodoro.TRABAJO,
    val segundosRestantes: Int = 0,
    val segundosDeLaFase: Int = 0,
    val enMarcha: Boolean = false,
    // La espera al cambiar de fase: mientras dura, los botones están bloqueados
    val cambiandoDeFase: Boolean = false,
    val ciclosCompletados: Int = 0
) {
    // Valores derivados: se calculan, no se guardan
    val progreso: Float
        get() = if (segundosDeLaFase == 0) 0f else segundosRestantes.toFloat() / segundosDeLaFase

    val tiempoTexto: String
        get() = "%02d:%02d".format(segundosRestantes / 60, segundosRestantes % 60)
}
