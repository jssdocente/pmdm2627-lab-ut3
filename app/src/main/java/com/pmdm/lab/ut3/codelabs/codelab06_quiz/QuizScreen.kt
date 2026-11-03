package com.pmdm.lab.ut3.codelabs.codelab06_quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme

// Con estado: obtiene el ViewModel y observa su uiState
@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel()
) {
    // Cada vez que el StateFlow emite un estado nuevo, la pantalla se recompone
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QuizContent(
        uiState = uiState,
        onOpcionClick = viewModel::elegirOpcion,
        onSiguiente = viewModel::siguiente,
        onReiniciar = viewModel::reiniciar,
        modifier = modifier
    )
}

// Sin estado: el estado baja, los eventos suben
@Composable
fun QuizContent(
    uiState: QuizUiState,
    onOpcionClick: (String) -> Unit,
    onSiguiente: () -> Unit,
    onReiniciar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Pregunta ${uiState.numeroPregunta} de ${uiState.totalPreguntas}")
            Text("${uiState.puntos} puntos", style = MaterialTheme.typography.titleMedium)
        }
        LinearProgressIndicator(
            progress = { uiState.progreso },
            modifier = Modifier.fillMaxWidth()
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = uiState.enunciado,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            )
        }

        val respondida = uiState.opcionElegida != null
        uiState.opciones.forEach { opcion ->
            // La opción elegida se pinta de primary si acierta y de error si falla
            val colores = when {
                opcion != uiState.opcionElegida -> ButtonDefaults.filledTonalButtonColors()
                uiState.esCorrecta == true -> ButtonDefaults.buttonColors()
                else -> ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            }
            Button(
                onClick = { onOpcionClick(opcion) },
                enabled = !respondida || opcion == uiState.opcionElegida,
                colors = colores,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(opcion)
            }
        }

        OutlinedButton(
            onClick = onSiguiente,
            enabled = respondida,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Siguiente")
        }
    }

    if (uiState.terminado) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("¡Quiz terminado!") },
            text = { Text("Has conseguido ${uiState.puntos} puntos de ${uiState.totalPreguntas * 10}.") },
            confirmButton = {
                TextButton(onClick = onReiniciar) { Text("Jugar otra vez") }
            }
        )
    }
}

// La preview usa QuizContent con un estado inventado: no necesita ViewModel
@Preview(showBackground = true)
@Composable
private fun QuizContentPreview() {
    LabUt3Theme {
        QuizContent(
            uiState = QuizUiState(
                enunciado = preguntasQuiz[3].enunciado,
                opciones = preguntasQuiz[3].opciones,
                numeroPregunta = 4,
                totalPreguntas = 5,
                puntos = 20,
                opcionElegida = "ViewModel",
                esCorrecta = true
            ),
            onOpcionClick = {},
            onSiguiente = {},
            onReiniciar = {}
        )
    }
}
