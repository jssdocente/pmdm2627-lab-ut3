package com.pmdm.lab.ut3.codelabs.codelab05_navegacion

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

// Cada pantalla es una clave: NavKey + @Serializable para que la pila se pueda guardar.

// Sin argumentos: data object
@Serializable
data object ListaCursos : NavKey

// Con argumentos: data class. Pasamos solo el id, no el Curso entero
@Serializable
data class DetalleCurso(val cursoId: Int) : NavKey
