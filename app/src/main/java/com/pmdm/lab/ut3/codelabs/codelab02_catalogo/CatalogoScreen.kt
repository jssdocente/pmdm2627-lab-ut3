package com.pmdm.lab.ut3.codelabs.codelab02_catalogo

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.lab.ut3.R
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme

@Composable
fun CatalogoScreen(modifier: Modifier = Modifier) {
    // Una lista observable: Compose recompone al añadir o quitar elementos
    val cursos = remember { mutableStateListOf(*catalogoCursos.toTypedArray()) }

    if (cursos.isEmpty()) {
        CatalogoVacio(
            onRestaurar = { cursos.addAll(catalogoCursos) },
            modifier = modifier
        )
    } else {
        ListaCursos(
            cursos = cursos,
            onBorrar = { cursos.remove(it) },
            modifier = modifier
        )
    }
}

@Composable
fun ListaCursos(
    cursos: List<Curso>,
    onBorrar: (Curso) -> Unit,
    modifier: Modifier = Modifier
) {
    // Solo se componen las tarjetas que caben en pantalla (y alguna más)
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(text = "Catálogo de cursos", style = MaterialTheme.typography.headlineMedium)
        }
        // key: cada tarjeta queda unida a SU curso, aunque cambie de posición
        items(items = cursos, key = { it.id }) { curso ->
            TarjetaCurso(curso = curso, onBorrar = { onBorrar(curso) })
        }
    }
}

@Composable
fun TarjetaCurso(
    curso: Curso,
    onBorrar: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Cada tarjeta recuerda si está desplegada
    var desplegada by rememberSaveable { mutableStateOf(false) }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize() // anima el cambio de altura al desplegar
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Inicial del curso dentro de un círculo
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = curso.titulo.take(1), style = MaterialTheme.typography.titleMedium)
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                ) {
                    Text(text = curso.titulo, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "${curso.nivel} · ${curso.horas} h",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = { desplegada = !desplegada }) {
                    Icon(
                        painter = painterResource(
                            if (desplegada) R.drawable.ic_expand_less else R.drawable.ic_expand_more
                        ),
                        contentDescription = if (desplegada) "Ver menos" else "Ver más"
                    )
                }
                IconButton(onClick = onBorrar) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = "Borrar ${curso.titulo}",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Text(
                text = curso.resumen,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Solo se compone cuando la tarjeta está desplegada
            if (desplegada) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                Text(
                    text = "Temario",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(text = curso.temario, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun CatalogoVacio(onRestaurar: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Has borrado todos los cursos", style = MaterialTheme.typography.bodyLarge)
        OutlinedButton(onClick = onRestaurar) {
            Text("Restaurar el catálogo")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListaCursosPreview() {
    LabUt3Theme {
        ListaCursos(cursos = catalogoCursos.take(3), onBorrar = {})
    }
}
