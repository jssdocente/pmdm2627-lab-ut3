package com.pmdm.lab.ut3.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.lab.ut3.PantallaLab
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme

@Composable
fun LabMenuScreen(
    onAbrir: (PantallaLab) -> Unit,
    modifier: Modifier = Modifier
) {
    val codelabs = PantallaLab.entries.filter { it != PantallaLab.MENU && !it.esReto }
    val retos = PantallaLab.entries.filter { it.esReto }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Lab UT3",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(text = "Codelabs", style = MaterialTheme.typography.titleLarge)
        if (codelabs.isEmpty()) {
            Text(
                text = "Todavía no hay codelabs: empieza por el 3.01.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        codelabs.forEach { pantalla ->
            TarjetaMenu(pantalla = pantalla, onClick = { onAbrir(pantalla) })
        }

        if (retos.isNotEmpty()) {
            Text(text = "Retos", style = MaterialTheme.typography.titleLarge)
            retos.forEach { pantalla ->
                TarjetaMenu(pantalla = pantalla, onClick = { onAbrir(pantalla) })
            }
        }
    }
}

@Composable
private fun TarjetaMenu(pantalla: PantallaLab, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = pantalla.titulo,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = pantalla.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LabMenuScreenPreview() {
    LabUt3Theme {
        LabMenuScreen(onAbrir = {})
    }
}
