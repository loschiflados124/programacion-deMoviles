# Notas del proceso – Semana 5: Diseño de interfaces gráficas en Android

**Grupo 1:** Eymer Baez (1-24-3833), Ebelio Francisco (1-24-3647), Enrique Rincon (1-24-3689), Angel Ventura (1-24-3690)
**Asignatura:** Programación de Dispositivos Móviles (UTESA). Profesor: Eduardo Arvelo.
**Apps:** `RegistroEstudiantil` (com.utesa.registroestudiantil) y `AdopcionMascotas` (com.utesa.adopcionmascotas), en Kotlin, con vistas XML (sin Jetpack Compose).

## 1. Versiones usadas

| Componente | Versión |
|---|---|
| Android Studio | build 261.25134.95 |
| JDK | OpenJDK 21.0.10 (carpeta `jbr` de Android Studio; `JAVA_HOME` no estaba configurado) |
| Gradle (wrapper) | 8.14 |
| Android Gradle Plugin | 8.13.2 |
| Kotlin | 2.2.20 |
| compileSdk / targetSdk / minSdk | 36 / 36 / 24 |
| core-ktx / appcompat / material / constraintlayout | 1.15.0 / 1.6.1 / 1.12.0 / 2.2.1 |
| lifecycle-viewmodel-ktx / livedata-ktx / activity-ktx | 2.8.7 / 2.8.7 / 1.9.3 |
| Emulador | AVD Pixel_7_Pro, Android 17, emulador 36.6.11 |

## 2. Archivos creados

La carpeta `Semana5` no existía. Los dos proyectos se crearon con la estructura de la plantilla "Empty Views Activity", copiando el esqueleto de Gradle del proyecto vecino `MensajedeDespedida` (`build.gradle.kts`, `settings.gradle.kts` con el nombre cambiado, `gradle.properties`, `local.properties`, `gradlew`, `gradlew.bat`, `gradle/wrapper/*`, `gradle/libs.versions.toml`, `.gitignore`, `app/proguard-rules.pro`). En `app/build.gradle.kts` solo cambió el `namespace`/`applicationId`.

**App 1 – RegistroEstudiantil** (`app/src/main/…`)
- Kotlin: `MainActivity.kt`, `DesarrolladoresActivity.kt`, `EstudiantesViewModel.kt`, `Estudiante.kt`
- Layouts: `activity_main.xml`, `activity_desarrolladores.xml`, `toast_detalle.xml`
- Drawable: `fondo_toast.xml`
- Valores: `strings.xml`, `arrays.xml`, `colors.xml`, `themes.xml`, `values-night/themes.xml`
- `AndroidManifest.xml`

**App 2 – AdopcionMascotas** (`app/src/main/…`)
- Kotlin: `MainActivity.kt`, `DesarrolladoresActivity.kt`, `SolicitudesViewModel.kt`, `Solicitud.kt`, `SolicitudAdapter.kt`
- Layouts: `activity_main.xml`, `activity_desarrolladores.xml`, `item_solicitud.xml`, `dialog_resumen.xml`, `toast_detalle.xml`
- Drawables: `ic_perro.xml`, `ic_gato.xml`, `ic_conejo.xml`, `ic_pajaro.xml`, `ic_pez.xml` (VectorDrawables hechos a mano, sin imágenes descargadas), `fondo_toast.xml`
- Valores: `strings.xml`, `arrays.xml`, `colors.xml`, `themes.xml`, `values-night/themes.xml`
- `AndroidManifest.xml`

**Otros:** `Entrega/`, `Semana5_InterfacesGraficas_Grupo1_Baez_Francisco_Rincon_Ventura.zip`, `Capturas/` (capturas de prueba de la App 1) y este archivo.

## 3. Vistas y layouts usados

**Común a las dos apps:** `LinearLayout` raíz con los insets de `WindowInsetsCompat.Type.systemBars()` (más `displayCutout()`) como padding; `MaterialToolbar` verde (#076633); `ScrollView` con el formulario; `ListView` de altura fija con desplazamiento anidado; `AlertDialog`; `Toast` con vista propia; `DesarrolladoresActivity` con `TextView` y un `TableLayout`/`TableRow` (columnas Nombre y Matrícula) y el botón "Volver". La lista vive en un `ViewModel` con `LiveData`.

**App 1:** `TextView` (título, etiquetas, contador), `EditText` (nombre `textPersonName`, matrícula con `digits="0123456789-"`, edad `number`), `Spinner` (8 carreras de `arrays.xml`, `ArrayAdapter.createFromResource`, `android:prompt`), `RadioGroup` + 3 `RadioButton` (tanda), 4 `CheckBox` (asignaturas), `Button` (Registrar, Limpiar, Desarrolladores), `ListView` con `ArrayAdapter`.

**App 2:** `RelativeLayout` para la sección superior (título, Spinner, `ImageView` de 180dp, nombre y descripción de la mascota ubicados con `layout_below`/`centerHorizontal`), `Spinner` (5 mascotas, `setOnItemSelectedListener` cambia la imagen), `EditText` (adoptante, teléfono `phone`, nombre de la mascota), `RadioGroup` (tamaño), 3 `CheckBox`, `SeekBar` (1 a 120 meses, con `TextView` del valor), `Button` (Solicitar adopción, Limpiar, Desarrolladores), `AlertDialog` con layout propio `dialog_resumen.xml` (imagen + resumen), `ListView` con `SolicitudAdapter` (`BaseAdapter` + `item_solicitud.xml`: imagen pequeña, nombre de la mascota y adoptante).

## 4. Errores que ocurrieron realmente y cómo se resolvieron

- **La carpeta `Semana5` y los proyectos no existían.** Se crearon desde cero con el esqueleto de los proyectos vecinos (ver punto 2).
- **`JAVA_HOME` vacío.** Se usó el JDK de Android Studio: `/Applications/Android Studio.app/Contents/jbr/Contents/Home`.
- **Errores de compilación: ninguno.** Las dos apps dieron BUILD SUCCESSFUL en el primer intento.
- **Avisos del compilador (no son errores):**
  - En `Solicitud.kt`, la anotación `@DrawableRes` en un parámetro del data class daba un aviso de Kotlin. Se quitó la anotación y se dejó un comentario.
  - `jvmTarget` en `app/build.gradle.kts` aparece como deprecado. Viene del esqueleto de los proyectos anteriores y no afecta la compilación, así que se dejó igual.
- **El primer emulador se cerró solo.** El emulador que se abrió desde la terminal arrancó y luego se apagó. Se usó el emulador Pixel_7_Pro abierto desde Android Studio.
- **La primera captura salió negra**, porque la pantalla del emulador estaba apagada. Se despertó con `KEYCODE_WAKEUP` y se repitió.
- **Fallas del script de prueba, no de las apps:**
  - El gesto de desplazamiento usaba coordenadas de pantalla vertical cuando el emulador estaba en horizontal. Se corrigieron las coordenadas.
  - En la App 2, `uiautomator` no encontraba "Solicitar adopción" porque estaba fuera de la pantalla. Se cambió el script para desplazarse hasta encontrar la vista.
- **Por pedido del usuario**, se dejaron de tomar capturas (las hará en vivo desde Android Studio) y el emulador se volvió a vertical con la autorrotación desactivada.

## 5. Resultados de las pruebas (emulador Pixel_7_Pro, Android 17)

| Prueba | Resultado |
|---|---|
| `./gradlew clean assembleDebug` (ambas) | BUILD SUCCESSFUL. APK: RegistroEstudiantil 6 288 655 bytes y AdopcionMascotas 6 298 143 bytes |
| `adb install` (ambas) | Success |
| App 1: vista inicial | Barra de estado verde, nada tapado ni cortado |
| App 1: Registrar con el formulario vacío | `setError` en nombre, matrícula y edad; no se registró nada |
| App 1: Spinner | Se abre en modo diálogo con el título "Seleccione la carrera" (prompt) |
| App 1: Registrar con datos válidos | AlertDialog con nombre, matrícula, edad, carrera, tanda y asignaturas; el formulario se limpia |
| App 1: lista y contador | 2 estudiantes en el formato "nombre - matrícula - carrera"; "Estudiantes registrados: 2" |
| App 1: tocar un elemento | Toast con todos los datos (sin recortar) |
| App 1: mantener presionado | Diálogo "¿Desea eliminar a …?" con Cancelar/Eliminar (se probó Cancelar) |
| App 1: rotación a horizontal | La lista y el contador se conservaron (ViewModel); el contenido no queda bajo la cámara ni bajo las barras |
| App 1: Desarrolladores / Volver | Muestra la app, la asignatura, el profesor y la tabla; Volver regresa a MainActivity |
| App 2: Spinner → Gato | La imagen cambia y aparecen "Gato" y "Independiente y cariñoso" |
| App 2: Solicitar con el formulario vacío | No se agregó nada ("Solicitudes registradas: 0"); foco en el campo con error |
| App 2: Solicitar con datos válidos | Diálogo con `ivDialogoMascota` y el resumen completo (mascota, nombre, tamaño, edad en meses, condiciones, adoptante, teléfono) |
| App 2: lista | Fila con imagen (`ivItemMascota`), "Michi · Gato" y "Adoptante: Maria Gomez"; contador en 1; al tocar la fila se muestra el Toast |
| App 2: rotación | Se rotó con la lista vacía y la app no falló. No se repitió con solicitudes (el usuario pidió dejar el emulador en vertical); usa el mismo patrón de ViewModel que la App 1 |
| App 2: Desarrolladores / Volver | Muestra "Adopción de Mascotas", "Eduardo Arvelo" y las matrículas; Volver regresa a MainActivity |
| Crashes (`logcat -b crash`) | Ninguno |
| Zip | `unzip -tq`: "No errors detected"; 41 entradas |

**No se probó:** el botón "Eliminar" del diálogo de la App 1 (solo Cancelar) y el botón "Limpiar" de las dos apps (se revisaron solo en el código); tampoco el aspecto visual de la App 2, porque el usuario pidió no tomar más capturas (lo verificará en vivo). No se probó en un dispositivo físico.
