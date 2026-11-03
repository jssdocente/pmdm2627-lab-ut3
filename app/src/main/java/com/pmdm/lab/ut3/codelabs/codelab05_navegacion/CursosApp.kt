package com.pmdm.lab.ut3.codelabs.codelab05_navegacion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pmdm.lab.ut3.codelabs.codelab02_catalogo.catalogoCursos

/**
 * Raíz de la navegación del codelab: la pila y qué pantalla corresponde a cada clave.
 * Es el único sitio que toca la pila: las pantallas solo reciben lambdas.
 */
@Composable
fun CursosApp(modifier: Modifier = Modifier) {
    // La pila empieza con una sola pantalla. rememberNavBackStack la conserva al girar
    val pila = rememberNavBackStack(ListaCursos)

    Column(modifier = modifier) {
        NavDisplay(
            backStack = pila,
            // Atrás (gesto, botón del sistema o flecha): quitar la pantalla de arriba
            onBack = { pila.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<ListaCursos> {
                    ListaCursosScreen(
                        cursos = catalogoCursos,
                        onCursoClick = { id -> pila.add(DetalleCurso(cursoId = id)) }
                    )
                }
                // La clave trae los argumentos: clave.cursoId
                entry<DetalleCurso> { clave ->
                    DetalleCursoScreen(
                        curso = catalogoCursos.find { it.id == clave.cursoId },
                        onSiguiente = { pila.add(DetalleCurso(cursoId = clave.cursoId + 1)) },
                        onVolver = { pila.removeLastOrNull() },
                        onVolverAlInicio = {
                            // Quitamos todo lo que hay encima de la primera pantalla
                            while (pila.size > 1) pila.removeLastOrNull()
                        }
                    )
                }
            },
            modifier = Modifier.weight(1f)
        )

        // Solo para el codelab: así ves cómo crece y decrece la pila
        Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
            Text(
                text = "Pila: " + pila.joinToString(" › ") { describir(it) },
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )
        }
    }
}

private fun describir(clave: NavKey): String = when (clave) {
    is ListaCursos -> "Lista"
    is DetalleCurso -> "Detalle(${clave.cursoId})"
    else -> "?"
}
