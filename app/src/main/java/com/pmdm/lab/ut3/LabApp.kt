package com.pmdm.lab.ut3

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.pmdm.lab.ut3.ui.menu.LabMenuScreen

// Cada pantalla del laboratorio. Para añadir un codelab: una entrada aquí
// y una rama en el when de LabApp.
enum class PantallaLab(
    val titulo: String,
    val descripcion: String,
    val esReto: Boolean = false
) {
    MENU("Menú", "Catálogo de codelabs")
}

@Composable
fun LabApp(modifier: Modifier = Modifier) {
    // La pantalla visible también es estado: al cambiarla, Compose recompone
    var pantalla by rememberSaveable { mutableStateOf(PantallaLab.MENU) }

    // El botón «atrás» del sistema vuelve al menú en lugar de cerrar la app
    BackHandler(enabled = pantalla != PantallaLab.MENU) {
        pantalla = PantallaLab.MENU
    }

    when (pantalla) {
        PantallaLab.MENU -> LabMenuScreen(
            onAbrir = { pantalla = it },
            modifier = modifier
        )
    }
}

// Añade a cualquier pantalla un botón para volver al menú
@Composable
private fun PantallaConVolver(
    onVolver: () -> Unit,
    modifier: Modifier = Modifier,
    contenido: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        TextButton(onClick = onVolver) {
            Text("← Menú")
        }
        contenido()
    }
}
