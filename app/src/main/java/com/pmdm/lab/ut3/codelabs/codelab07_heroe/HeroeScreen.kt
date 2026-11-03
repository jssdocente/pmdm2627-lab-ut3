package com.pmdm.lab.ut3.codelabs.codelab07_heroe

import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pmdm.lab.ut3.R
import com.pmdm.lab.ut3.ui.theme.LabUt3Theme

// El valor más alto que puede tener una estadística en el juego
const val ESTADISTICA_MAXIMA = 120

// Cada clase guarda los IDs de sus recursos (textos, imagen y lista de habilidades)
// y sus estadísticas. Los textos no van aquí: dependen del idioma del móvil.
enum class ClaseHeroe(
    @param:StringRes val nombre: Int,
    @param:DrawableRes val imagen: Int,
    @param:ArrayRes val habilidades: Int,
    val vidaMax: Int,
    val manaMax: Int,
    val ataque: Int
) {
    GUERRERO(R.string.heroe_clase_guerrero, R.drawable.ic_hero_warrior, R.array.habilidades_guerrero, 120, 20, 85),
    MAGO(R.string.heroe_clase_mago, R.drawable.ic_hero_mage, R.array.habilidades_mago, 60, 110, 95),
    PICARO(R.string.heroe_clase_picaro, R.drawable.ic_hero_rogue, R.array.habilidades_picaro, 80, 50, 75)
}

@Composable
fun HeroeScreen(modifier: Modifier = Modifier) {
    var clase by rememberSaveable { mutableStateOf(ClaseHeroe.GUERRERO) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.heroe_titulo),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.heroe_elige_clase),
            style = MaterialTheme.typography.titleMedium
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ClaseHeroe.entries.forEach { opcion ->
                FilterChip(
                    selected = opcion == clase,
                    onClick = { clase = opcion },
                    label = { Text(stringResource(opcion.nombre)) }
                )
            }
        }
        FichaHeroe(clase = clase)
    }
}

@Composable
fun FichaHeroe(clase: ClaseHeroe, modifier: Modifier = Modifier) {
    // El color de cada clase sale del tema: funciona en claro y en oscuro
    val colorClase = when (clase) {
        ClaseHeroe.GUERRERO -> MaterialTheme.colorScheme.tertiary
        ClaseHeroe.MAGO -> MaterialTheme.colorScheme.primary
        ClaseHeroe.PICARO -> MaterialTheme.colorScheme.secondary
    }
    val nombre = stringResource(clase.nombre)
    val habilidades = stringArrayResource(clase.habilidades)

    ElevatedCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Box apila capas: el círculo con la ilustración y, encima, el escudo
            Box(modifier = Modifier.size(128.dp)) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(colorClase.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    // Crossfade funde la imagen anterior con la nueva al cambiar de clase
                    Crossfade(targetState = clase.imagen, label = "imagenHeroe") { imagen ->
                        Image(
                            painter = painterResource(imagen),
                            contentDescription = stringResource(R.string.heroe_descripcion_imagen, nombre),
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }
                // El mismo escudo para todas las clases, teñido con su color
                Image(
                    painter = painterResource(R.drawable.ic_shield),
                    contentDescription = stringResource(R.string.heroe_descripcion_escudo),
                    colorFilter = ColorFilter.tint(colorClase),
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.BottomEnd)
                )
            }

            Text(
                text = nombre,
                style = MaterialTheme.typography.headlineMedium,
                color = colorClase,
                modifier = Modifier.padding(top = 8.dp)
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            // Textos con huecos: %1$d y %2$d se rellenan con los argumentos
            Estadistica(
                texto = stringResource(R.string.heroe_vida, clase.vidaMax, ESTADISTICA_MAXIMA),
                valor = clase.vidaMax.toFloat() / ESTADISTICA_MAXIMA,
                color = MaterialTheme.colorScheme.error
            )
            Estadistica(
                texto = stringResource(R.string.heroe_mana, clase.manaMax, ESTADISTICA_MAXIMA),
                valor = clase.manaMax.toFloat() / ESTADISTICA_MAXIMA,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.heroe_ataque, clase.ataque),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.heroe_habilidades),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                habilidades.forEach { habilidad ->
                    Text(text = "• $habilidad", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
private fun Estadistica(texto: String, valor: Float, color: Color) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = texto, style = MaterialTheme.typography.bodyMedium)
        LinearProgressIndicator(
            progress = { valor },
            color = color,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )
    }
}

@Preview(name = "Guerrero", showBackground = true)
@Composable
private fun FichaGuerreroPreview() {
    LabUt3Theme { FichaHeroe(clase = ClaseHeroe.GUERRERO) }
}

@Preview(name = "Maga en inglés", showBackground = true, locale = "en")
@Composable
private fun FichaMagoInglesPreview() {
    LabUt3Theme { FichaHeroe(clase = ClaseHeroe.MAGO) }
}
