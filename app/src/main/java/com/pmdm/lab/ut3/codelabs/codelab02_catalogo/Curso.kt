package com.pmdm.lab.ut3.codelabs.codelab02_catalogo

// Un curso del catálogo. El id es único: lo usaremos como key de la lista
data class Curso(
    val id: Int,
    val titulo: String,
    val nivel: String,
    val horas: Int,
    val resumen: String,
    val temario: String
)

val catalogoCursos = listOf(
    Curso(1, "Kotlin desde Java", "Básico", 20,
        "El lenguaje del curso, partiendo de lo que ya sabes de Java.",
        "val y var, null safety, data class, funciones de extensión, lambdas y colecciones."),
    Curso(2, "Compose: primeros pasos", "Básico", 25,
        "Interfaces declarativas con funciones @Composable.",
        "Column, Row y Box, modificadores, Text, Image, @Preview y recomposición."),
    Curso(3, "Estado en Compose", "Básico", 15,
        "Interfaces que cambian cuando cambian los datos.",
        "remember, mutableStateOf, rememberSaveable y state hoisting."),
    Curso(4, "Material Design 3", "Intermedio", 15,
        "Un tema propio para toda la app.",
        "ColorScheme, roles de color, Typography, Shapes y modo oscuro."),
    Curso(5, "Listas con Lazy layouts", "Intermedio", 10,
        "Listas largas sin que la app se resienta.",
        "LazyColumn, LazyRow, LazyVerticalGrid, key y contentPadding."),
    Curso(6, "Scaffold y componentes", "Intermedio", 15,
        "La estructura de una pantalla de Material 3.",
        "TopAppBar, FloatingActionButton, Snackbar, AlertDialog e innerPadding."),
    Curso(7, "Navigation 3", "Intermedio", 15,
        "Varias pantallas y una pila que controlas tú.",
        "NavKey, rememberNavBackStack, NavDisplay, entryProvider y argumentos."),
    Curso(8, "ViewModel y StateFlow", "Avanzado", 20,
        "El estado de la pantalla fuera de la interfaz.",
        "UiState inmutable, MutableStateFlow, flujo unidireccional y collectAsStateWithLifecycle."),
    Curso(9, "Persistencia con Room", "Avanzado", 25,
        "Bases de datos locales con corrutinas.",
        "@Entity, @Dao, @Database, consultas con Flow y migraciones."),
    Curso(10, "APIs REST con Retrofit", "Avanzado", 20,
        "Datos de un servidor en tu app.",
        "Retrofit, kotlinx.serialization, corrutinas y gestión de errores de red."),
    Curso(11, "Inyección con Koin", "Avanzado", 10,
        "Dependencias sin crearlas a mano en cada pantalla.",
        "Módulos, single, factory, viewModel y koinViewModel()."),
    Curso(12, "Publicar en Google Play", "Intermedio", 10,
        "De tu proyecto a la tienda.",
        "Firma de la app, App Bundle, ficha de la tienda y pistas de prueba.")
)
