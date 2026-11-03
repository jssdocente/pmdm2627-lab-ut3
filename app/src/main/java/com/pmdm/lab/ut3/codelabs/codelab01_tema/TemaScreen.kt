package com.pmdm.lab.ut3.codelabs.codelab01_tema

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme

@Composable
fun TemaScreen(modifier: Modifier = Modifier) {
    var modoOscuro by rememberSaveable { mutableStateOf(false) }

    // Un MaterialTheme dentro de otro: lo de dentro usa el esquema que elijamos
    LabUt3Theme(darkTheme = modoOscuro) {
        MuestrarioTema(
            modoOscuro = modoOscuro,
            onModoOscuroChange = { modoOscuro = it },
            modifier = modifier
        )
    }
}

@Composable
fun MuestrarioTema(
    modoOscuro: Boolean,
    onModoOscuroChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    // Surface pinta el fondo con el color del tema y fija el color del texto (onSurface)
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Muestrario del tema", style = MaterialTheme.typography.headlineMedium)

            // 1. Interruptor de modo oscuro
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Modo oscuro", style = MaterialTheme.typography.titleMedium)
                    Switch(checked = modoOscuro, onCheckedChange = onModoOscuroChange)
                }
            }

            // 2. Roles de color: cada fondo con su "on-color" encima
            Text(text = "Colores", style = MaterialTheme.typography.titleLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MuestraColor("primary", MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.onPrimary, Modifier.weight(1f))
                MuestraColor("secondary", MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.onSecondary, Modifier.weight(1f))
                MuestraColor("tertiary", MaterialTheme.colorScheme.tertiary,
                    MaterialTheme.colorScheme.onTertiary, Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MuestraColor("primary\nContainer", MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.onPrimaryContainer, Modifier.weight(1f))
                MuestraColor("secondary\nContainer", MaterialTheme.colorScheme.secondaryContainer,
                    MaterialTheme.colorScheme.onSecondaryContainer, Modifier.weight(1f))
                MuestraColor("tertiary\nContainer", MaterialTheme.colorScheme.tertiaryContainer,
                    MaterialTheme.colorScheme.onTertiaryContainer, Modifier.weight(1f))
            }

            // 3. Escala tipográfica
            Text(text = "Tipografía", style = MaterialTheme.typography.titleLarge)
            Text(text = "headlineMedium", style = MaterialTheme.typography.headlineMedium)
            Text(text = "titleMedium", style = MaterialTheme.typography.titleMedium)
            Text(text = "bodyLarge: el texto normal de la app", style = MaterialTheme.typography.bodyLarge)
            Text(text = "labelMedium", style = MaterialTheme.typography.labelMedium)

            // 4. Formas: small, medium y large
            Text(text = "Formas", style = MaterialTheme.typography.titleLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MuestraForma("small", MaterialTheme.shapes.small, Modifier.weight(1f))
                MuestraForma("medium", MaterialTheme.shapes.medium, Modifier.weight(1f))
                MuestraForma("large", MaterialTheme.shapes.large, Modifier.weight(1f))
            }

            // 5. Los componentes toman sus colores del tema sin que se los digamos
            Text(text = "Componentes", style = MaterialTheme.typography.titleLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {}) { Text("Button") }
                FilledTonalButton(onClick = {}) { Text("Tonal") }
                OutlinedButton(onClick = {}) { Text("Outlined") }
            }
        }
    }
}

@Composable
private fun MuestraColor(nombre: String, fondo: Color, texto: Color, modifier: Modifier = Modifier) {
    Surface(
        color = fondo,
        contentColor = texto,
        shape = MaterialTheme.shapes.small,
        modifier = modifier.height(64.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = nombre, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun MuestraForma(nombre: String, forma: Shape, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = forma,
        modifier = modifier.height(56.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = nombre, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Preview(name = "Claro", showBackground = true, heightDp = 900)
@Composable
private fun MuestrarioClaroPreview() {
    LabUt3Theme(darkTheme = false) {
        MuestrarioTema(modoOscuro = false, onModoOscuroChange = {})
    }
}

@Preview(name = "Oscuro", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, heightDp = 900)
@Composable
private fun MuestrarioOscuroPreview() {
    LabUt3Theme(darkTheme = true) {
        MuestrarioTema(modoOscuro = true, onModoOscuroChange = {})
    }
}
