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
import com.pmdm.lab.ut3.codelabs.codelab01_tema.TemaScreen
import com.pmdm.lab.ut3.codelabs.codelab02_catalogo.CatalogoScreen
import com.pmdm.lab.ut3.codelabs.codelab03_tareas.TareasScreen
import com.pmdm.lab.ut3.codelabs.codelab04_propinas.PropinasScreen
import com.pmdm.lab.ut3.codelabs.codelab05_navegacion.CursosApp
import com.pmdm.lab.ut3.codelabs.codelab06_quiz.QuizScreen
import com.pmdm.lab.ut3.codelabs.codelab07_heroe.HeroeScreen
import com.pmdm.lab.ut3.ui.menu.LabMenuScreen

// Cada pantalla del laboratorio. Para añadir un codelab: una entrada aquí
// y una rama en el when de LabApp.
enum class PantallaLab(
    val titulo: String,
    val descripcion: String,
    val esReto: Boolean = false
) {
    MENU("Menú", "Catálogo de codelabs"),
    TEMA("3.01 · Tema de Material 3", "Colores, tipografía, formas y modo oscuro"),
    CATALOGO("3.02 · Catálogo de cursos", "LazyColumn, key y tarjetas desplegables"),
    TAREAS("3.03 · Lista de tareas", "Scaffold, TopAppBar, FAB, Snackbar y AlertDialog"),
    PROPINAS("3.04 · Calculadora de propinas", "TextField con validación, Slider y Switch"),
    NAVEGACION("3.05 · Navegar entre pantallas", "Navigation 3: pila, claves y argumentos"),
    QUIZ("3.06 · Quiz con ViewModel", "UiState, StateFlow y flujo unidireccional"),
    HEROE("3.07 · Ficha de héroe", "Recursos, imágenes, idiomas y ColorFilter")
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
        PantallaLab.TEMA -> PantallaConVolver({ pantalla = PantallaLab.MENU }, modifier) {
            TemaScreen()
        }
        PantallaLab.CATALOGO -> PantallaConVolver({ pantalla = PantallaLab.MENU }, modifier) {
            CatalogoScreen()
        }
        PantallaLab.TAREAS -> PantallaConVolver({ pantalla = PantallaLab.MENU }, modifier) {
            TareasScreen()
        }
        PantallaLab.PROPINAS -> PantallaConVolver({ pantalla = PantallaLab.MENU }, modifier) {
            PropinasScreen()
        }
        PantallaLab.NAVEGACION -> PantallaConVolver({ pantalla = PantallaLab.MENU }, modifier) {
            CursosApp()
        }
        PantallaLab.QUIZ -> PantallaConVolver({ pantalla = PantallaLab.MENU }, modifier) {
            QuizScreen()
        }
        PantallaLab.HEROE -> PantallaConVolver({ pantalla = PantallaLab.MENU }, modifier) {
            HeroeScreen()
        }
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
