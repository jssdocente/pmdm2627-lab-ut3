package com.pmdm.lab.ut3.retos.reto03_memory

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pmdm.lab.ut3.retos.RetoPendiente

/*
 * Reto 3.03 · Memory
 * Enunciado completo: apuntes, UT3 › 3.6 Laboratorio › Reto 3.03.
 *
 * TODO 1. data class Carta(id, simbolo, bocaArriba, emparejada) y MemoryUiState con
 *         las 16 cartas, los intentos y si la partida ha terminado.
 * TODO 2. MemoryViewModel: 8 símbolos (emojis) duplicados y barajados con shuffled().
 *         voltear(id): la primera carta se queda boca arriba; con la segunda se suma
 *         un intento y se comprueba la pareja.
 * TODO 3. Si no son pareja, se muestran un momento y se vuelven a ocultar:
 *         viewModelScope.launch { delay(800) ... }. Mientras tanto, bloquea las pulsaciones.
 * TODO 4. El tablero: LazyVerticalGrid(columns = GridCells.Fixed(4)) con key = { it.id }.
 *         Cada carta es una ElevatedCard: boca abajo, del color primary; boca arriba, el emoji.
 * TODO 5. Arriba, el número de intentos y un botón para reiniciar. Al terminar, un AlertDialog.
 */
@Composable
fun MemoryScreen(modifier: Modifier = Modifier) {
    RetoPendiente(titulo = "🧠 Memory", modifier = modifier)
}
