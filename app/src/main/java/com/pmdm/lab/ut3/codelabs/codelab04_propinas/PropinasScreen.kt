package com.pmdm.lab.ut3.codelabs.codelab04_propinas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.lab.ut3.R
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme
import java.util.Locale
import kotlin.math.ceil

// Pasa un importe a texto con dos decimales: 7.5 -> "7,50 €" en español, "7.50 €" en inglés
fun formatearEuros(importe: Double, idioma: Locale = Locale.getDefault()): String =
    String.format(idioma, "%.2f €", importe)

// Con estado: guarda lo que escribe el usuario y calcula el resultado
@Composable
fun PropinasScreen(modifier: Modifier = Modifier) {
    var importeTexto by rememberSaveable { mutableStateOf("") }
    var porcentaje by rememberSaveable { mutableFloatStateOf(10f) }
    var redondear by rememberSaveable { mutableStateOf(false) }

    // Valores derivados: no se guardan, se recalculan en cada recomposición
    val importe = importeTexto.replace(',', '.').toDoubleOrNull()
    val hayError = importeTexto.isNotEmpty() && importe == null
    val propina = (importe ?: 0.0) * porcentaje / 100
    val total = (importe ?: 0.0) + propina
    val totalFinal = if (redondear) ceil(total) else total

    PropinasContent(
        importeTexto = importeTexto,
        onImporteChange = { importeTexto = it },
        hayError = hayError,
        porcentaje = porcentaje,
        onPorcentajeChange = { porcentaje = it },
        redondear = redondear,
        onRedondearChange = { redondear = it },
        propina = propina,
        total = totalFinal,
        modifier = modifier
    )
}

// Sin estado: recibe los valores y avisa de los cambios con lambdas
@Composable
fun PropinasContent(
    importeTexto: String,
    onImporteChange: (String) -> Unit,
    hayError: Boolean,
    porcentaje: Float,
    onPorcentajeChange: (Float) -> Unit,
    redondear: Boolean,
    onRedondearChange: (Boolean) -> Unit,
    propina: Double,
    total: Double,
    modifier: Modifier = Modifier
) {
    // El idioma con el que se muestra la app: decide si los decimales llevan coma o punto
    val idioma = LocalConfiguration.current.locales[0]

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Calculadora de propinas", style = MaterialTheme.typography.headlineMedium)

        // 1. Campo de texto: el valor viene de fuera y cada tecla llama a onValueChange
        OutlinedTextField(
            value = importeTexto,
            onValueChange = onImporteChange,
            label = { Text("Importe de la cuenta") },
            suffix = { Text("€") },
            trailingIcon = {
                if (importeTexto.isNotEmpty()) {
                    IconButton(onClick = { onImporteChange("") }) {
                        Icon(painterResource(R.drawable.ic_close), contentDescription = "Borrar importe")
                    }
                }
            },
            isError = hayError,
            supportingText = {
                Text(if (hayError) "Escribe un número, por ejemplo 24,50" else "Lo que marca el tique")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // 2. Slider: el porcentaje de propina, de 0 a 25 en saltos de 5
        Column {
            Text(
                text = "Propina: ${porcentaje.toInt()} %",
                style = MaterialTheme.typography.titleMedium
            )
            Slider(
                value = porcentaje,
                onValueChange = onPorcentajeChange,
                valueRange = 0f..25f,
                steps = 4
            )
        }

        // 3. Switch: redondear el total hacia arriba
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Redondear el total", style = MaterialTheme.typography.bodyLarge)
            Switch(checked = redondear, onCheckedChange = onRedondearChange)
        }

        // 4. Resultado
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Propina: ${formatearEuros(propina, idioma)}")
                Text(
                    text = "Total: ${formatearEuros(total, idioma)}",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Preview(name = "Con datos", showBackground = true)
@Composable
private fun PropinasConDatosPreview() {
    LabUt3Theme {
        PropinasContent(
            importeTexto = "50", onImporteChange = {}, hayError = false,
            porcentaje = 15f, onPorcentajeChange = {},
            redondear = false, onRedondearChange = {},
            propina = 7.5, total = 57.5
        )
    }
}

@Preview(name = "Con error", showBackground = true)
@Composable
private fun PropinasConErrorPreview() {
    LabUt3Theme {
        PropinasContent(
            importeTexto = "abc", onImporteChange = {}, hayError = true,
            porcentaje = 10f, onPorcentajeChange = {},
            redondear = false, onRedondearChange = {},
            propina = 0.0, total = 0.0
        )
    }
}
