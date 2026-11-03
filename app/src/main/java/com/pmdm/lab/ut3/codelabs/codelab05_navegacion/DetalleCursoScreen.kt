package com.pmdm.lab.ut3.codelabs.codelab05_navegacion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.lab.ut3.R
import com.pmdm.lab.ut3.codelabs.codelab02_catalogo.Curso
import com.pmdm.lab.ut3.codelabs.codelab02_catalogo.catalogoCursos
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme

/**
 * Detalle de un curso. Recibe el curso ya buscado (puede no existir: el id
 * de la clave podría no estar en el catálogo) y avisa con lambdas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCursoScreen(
    curso: Curso?,
    onSiguiente: () -> Unit,
    onVolver: () -> Unit,
    onVolverAlInicio: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(curso?.titulo ?: "Curso no encontrado") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(painterResource(R.drawable.ic_arrow_back), contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (curso == null) {
                Text("Ese curso no está en el catálogo.", style = MaterialTheme.typography.bodyLarge)
            } else {
                ListItem(
                    overlineContent = { Text("Nivel") },
                    headlineContent = { Text(curso.nivel) }
                )
                ListItem(
                    overlineContent = { Text("Duración") },
                    headlineContent = { Text("${curso.horas} horas") }
                )
                ListItem(
                    overlineContent = { Text("Temario") },
                    headlineContent = { Text(curso.temario) }
                )
                Button(onClick = onSiguiente) {
                    Text("Siguiente curso")
                }
            }
            OutlinedButton(onClick = onVolverAlInicio) {
                Text("Volver a la lista")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetalleCursoPreview() {
    LabUt3Theme {
        DetalleCursoScreen(curso = catalogoCursos[6], onSiguiente = {}, onVolver = {}, onVolverAlInicio = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun DetalleCursoNoEncontradoPreview() {
    LabUt3Theme {
        DetalleCursoScreen(curso = null, onSiguiente = {}, onVolver = {}, onVolverAlInicio = {})
    }
}
