package com.pmdm.lab.ut3.codelabs.codelab05_navegacion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.lab.ut3.codelabs.codelab02_catalogo.Curso
import com.pmdm.lab.ut3.codelabs.codelab02_catalogo.catalogoCursos
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme

// No sabe nada de la pila: avisa con el id del curso pulsado
@Composable
fun ListaCursosScreen(
    cursos: List<Curso>,
    onCursoClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            Text(
                text = "Elige un curso",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(cursos, key = { it.id }) { curso ->
            ListItem(
                headlineContent = { Text(curso.titulo) },
                supportingContent = { Text("${curso.nivel} · ${curso.horas} h") },
                modifier = Modifier.clickable { onCursoClick(curso.id) }
            )
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListaCursosPreview() {
    LabUt3Theme {
        ListaCursosScreen(cursos = catalogoCursos.take(4), onCursoClick = {})
    }
}
