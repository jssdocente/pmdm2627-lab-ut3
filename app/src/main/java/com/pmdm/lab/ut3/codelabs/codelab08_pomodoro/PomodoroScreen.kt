package com.pmdm.lab.ut3.codelabs.codelab08_pomodoro

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme

// Con estado: obtiene el ViewModel, observa su uiState y le avisa del ciclo de vida
@Composable
fun PomodoroScreen(
    modifier: Modifier = Modifier,
    viewModel: PomodoroViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Se ejecuta al volver a primer plano; onPauseOrDispose, al salir de él
    LifecycleResumeEffect(Unit) {
        viewModel.onVolverALaApp()
        onPauseOrDispose { viewModel.onSalirDeLaApp() }
    }

    PomodoroContent(
        uiState = uiState,
        onIniciar = viewModel::onIniciar,
        onPausar = viewModel::onPausar,
        onReiniciar = viewModel::onReiniciar,
        modifier = modifier
    )
}

// Sin estado: no sabe nada de corrutinas. Las animaciones solo siguen a lo que dice uiState
@Composable
fun PomodoroContent(
    uiState: PomodoroUiState,
    onIniciar: () -> Unit,
    onPausar: () -> Unit,
    onReiniciar: () -> Unit,
    modifier: Modifier = Modifier
) {
    // El anillo avanza suave entre segundo y segundo
    val progresoAnimado by animateFloatAsState(
        targetValue = uiState.progreso,
        animationSpec = tween(durationMillis = 1_000, easing = LinearEasing),
        label = "progreso"
    )
    val colorFase by animateColorAsState(
        targetValue = if (uiState.fase == FasePomodoro.TRABAJO) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.tertiary,
        label = "colorFase"
    )
    // Durante el cambio de fase, el anillo crece un poco y vuelve
    val escala by animateFloatAsState(
        targetValue = if (uiState.cambiandoDeFase) 1.1f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "escala"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()) // en horizontal no cabe entero
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = uiState.fase.titulo,
            style = MaterialTheme.typography.headlineMedium,
            color = colorFase
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(220.dp)
                .graphicsLayer {
                    scaleX = escala
                    scaleY = escala
                }
        ) {
            CircularProgressIndicator(
                progress = { progresoAnimado },
                color = colorFase,
                strokeWidth = 12.dp,
                modifier = Modifier.size(220.dp)
            )
            Text(text = uiState.tiempoTexto, style = MaterialTheme.typography.displayMedium)
        }
        Text(
            text = if (uiState.cambiandoDeFase) "Cambiando de fase…"
            else "Ciclos completados: ${uiState.ciclosCompletados}",
            style = MaterialTheme.typography.bodyLarge
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = if (uiState.enMarcha) onPausar else onIniciar,
                // Bloqueo: sale del estado, no de una variable de la pantalla
                enabled = !uiState.cambiandoDeFase
            ) {
                Text(if (uiState.enMarcha) "Pausar" else "Iniciar")
            }
            OutlinedButton(onClick = onReiniciar, enabled = !uiState.cambiandoDeFase) {
                Text("Reiniciar")
            }
        }
    }
}

@Preview(name = "En marcha", showBackground = true)
@Composable
private fun PomodoroEnMarchaPreview() {
    LabUt3Theme {
        PomodoroContent(
            uiState = PomodoroUiState(segundosRestantes = 15, segundosDeLaFase = 25, enMarcha = true),
            onIniciar = {}, onPausar = {}, onReiniciar = {}
        )
    }
}

@Preview(name = "Cambiando de fase", showBackground = true)
@Composable
private fun PomodoroCambiandoPreview() {
    LabUt3Theme {
        PomodoroContent(
            uiState = PomodoroUiState(
                fase = FasePomodoro.DESCANSO,
                segundosRestantes = 0,
                segundosDeLaFase = 5,
                enMarcha = true,
                cambiandoDeFase = true,
                ciclosCompletados = 2
            ),
            onIniciar = {}, onPausar = {}, onReiniciar = {}
        )
    }
}
