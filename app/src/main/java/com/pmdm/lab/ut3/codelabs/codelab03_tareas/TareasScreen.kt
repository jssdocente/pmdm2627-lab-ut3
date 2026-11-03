package com.pmdm.lab.ut3.codelabs.codelab03_tareas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.lab.ut3.R
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme
import kotlinx.coroutines.launch

// Cada tarea con su id: dos tareas pueden tener el mismo texto, pero nunca el mismo id
data class Tarea(val id: Int, val texto: String, val hecha: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareasScreen(modifier: Modifier = Modifier) {
    val tareas = remember {
        mutableStateListOf(
            Tarea(1, "Instalar el emulador"),
            Tarea(2, "Elegir la paleta del tema", hecha = true),
            Tarea(3, "Repasar LazyColumn y key")
        )
    }
    var siguienteId by remember { mutableIntStateOf(4) }

    // La tarea que se quiere borrar: si no es null, se muestra el diálogo
    var tareaABorrar by remember { mutableStateOf<Tarea?>(null) }

    // El Snackbar necesita su estado y un ámbito de corrutinas (showSnackbar es suspend)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val pendientes = tareas.count { !it.hecha }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Mis tareas") },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Te quedan $pendientes tareas pendientes")
                        }
                    }) {
                        Icon(painterResource(R.drawable.ic_info), contentDescription = "Resumen")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                tareas.add(Tarea(siguienteId, "Tarea nueva $siguienteId"))
                siguienteId++
            }) {
                Icon(painterResource(R.drawable.ic_add), contentDescription = "Añadir tarea")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        // innerPadding: el hueco que ocupan la barra superior y las del sistema
        if (tareas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No tienes tareas. Pulsa + para añadir una.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tareas, key = { it.id }) { tarea ->
                    FilaTarea(
                        tarea = tarea,
                        onMarcar = { hecha ->
                            val i = tareas.indexOf(tarea)
                            tareas[i] = tarea.copy(hecha = hecha)
                        },
                        onBorrar = { tareaABorrar = tarea }
                    )
                }
            }
        }
    }

    // El diálogo se muestra mientras haya una tarea pendiente de confirmar
    tareaABorrar?.let { tarea ->
        AlertDialog(
            onDismissRequest = { tareaABorrar = null },
            icon = { Icon(painterResource(R.drawable.ic_warning), contentDescription = null) },
            title = { Text("¿Borrar la tarea?") },
            text = { Text("«${tarea.texto}» desaparecerá de la lista.") },
            confirmButton = {
                TextButton(onClick = {
                    val posicion = tareas.indexOf(tarea)
                    tareas.remove(tarea)
                    tareaABorrar = null
                    scope.launch {
                        val resultado = snackbarHostState.showSnackbar(
                            message = "Tarea borrada",
                            actionLabel = "Deshacer"
                        )
                        if (resultado == SnackbarResult.ActionPerformed) {
                            tareas.add(posicion.coerceAtMost(tareas.size), tarea)
                        }
                    }
                }) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                TextButton(onClick = { tareaABorrar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun FilaTarea(
    tarea: Tarea,
    onMarcar: (Boolean) -> Unit,
    onBorrar: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = tarea.hecha, onCheckedChange = onMarcar)
            Text(
                text = tarea.texto,
                style = MaterialTheme.typography.bodyLarge,
                textDecoration = if (tarea.hecha) TextDecoration.LineThrough else null,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onBorrar) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "Borrar ${tarea.texto}",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TareasScreenPreview() {
    LabUt3Theme {
        TareasScreen()
    }
}
