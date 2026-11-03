# Lab UT3 · Codelabs de la UT3 (PMDM 26/27)

Proyecto de laboratorio de la UT3: una única app que **crece codelab a codelab**. Cada codelab parte del resultado del anterior y añade una pieza nueva de una app Android moderna: el tema de Material 3, listas, `Scaffold`, formularios, Navigation 3, ViewModel y recursos.

Parte de la plantilla `ProyectoComposeBase` del curso (IntelliJ IDEA 2026.2, AGP 9.1.1, compileSdk 37). Los requisitos, la configuración del SDK y del JDK, y los errores frecuentes son los mismos que los de la plantilla: consulta su README en el curso.

Los enunciados de cada codelab están en los apuntes: <https://jssdocente.github.io/pmdm2627d>, en *UT3 › Laboratorio*.

## Cómo seguir los codelabs

Cada codelab tiene dos *tags*: el punto de partida (`-inicio`) y el resultado al terminar (`-fin`). El inicio de un codelab es el fin del anterior.

| Codelab | Inicio | Fin |
| :--- | :--- | :--- |
| 3.01 · Tema de Material 3: colores, tipografía y formas | `c3.01-inicio` | `c3.01-fin` |
| 3.02 · Catálogo de cursos: `LazyColumn` y `key` | `c3.02-inicio` | `c3.02-fin` |
| 3.03 · Lista de tareas: `Scaffold`, `Snackbar` y `AlertDialog` | `c3.03-inicio` | `c3.03-fin` |
| 3.04 · Calculadora de propinas: formularios y validación | `c3.04-inicio` | `c3.04-fin` |
| 3.05 · Navegar entre pantallas: Navigation 3 | `c3.05-inicio` | `c3.05-fin` |
| 3.06 · Quiz con ViewModel y `StateFlow` | `c3.06-inicio` | `c3.06-fin` |
| 3.07 · Ficha de héroe: recursos, imágenes e idiomas | `c3.07-inicio` | `c3.07-fin` |

## Descargar un tag

```bash
git clone https://github.com/jssdocente/pmdm2627-lab-ut3.git lab-ut3
cd lab-ut3
git checkout c3.01-inicio         # el punto de partida del codelab
git switch -c mi-trabajo          # crea tu rama para trabajar sobre él
```

Si te atascas, haz `git checkout c3.01-fin` para ver cómo queda el codelab terminado y continúa desde ahí.

¿No usas Git? En GitHub, en *Tags*, cada tag se puede descargar como `.zip`. Descomprímelo en una ruta corta y sin acentos, por ejemplo `C:\PMDM\lab-ut3`.
