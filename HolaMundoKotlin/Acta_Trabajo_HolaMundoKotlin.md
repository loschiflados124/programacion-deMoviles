# Acta de Trabajo en Equipo

**Universidad Tecnológica de Santiago (UTESA)**

**Asignatura:** [COMPLETAR]
**Profesor:** [COMPLETAR]
**Sección:** [COMPLETAR]
**Fecha:** [COMPLETAR]

**Proyecto:** Aplicación Android "Hola Mundo Kotlin"

---

## 1. Integrantes

| Nombre | Matrícula | Rol |
|---|---|---|
| Enrique Rincon | 1-24-3689 | Configuración del ambiente de desarrollo |
| Eymer Baez | 1-24-3833 | Desarrollo (layout y código Kotlin) |
| Ebelio Francisco | 1-24-3647 | Documentación y redacción del acta |
| Angel Ventura | 1-24-3690 | QA / pruebas, generación del APK y empaquetado |

## 2. Objetivo del proyecto

El objetivo fue desarrollar una aplicación Android en Kotlin que muestre en pantalla el mensaje "Hola Mundo Kotlin de los estudiantes:", seguido del nombre y la matrícula de cada integrante del grupo. Además de la app, la tarea pedía entregar un solo archivo comprimido con el APK, el archivo MainActivity.kt y el layout activity_main.xml.

## 3. Herramientas y tecnologías utilizadas

Trabajamos con Android Studio 2026.1 (build AI-261.25134.95) en macOS. El proyecto compila con Gradle 8.14 a través del Gradle Wrapper, con el Android Gradle Plugin 8.13.2 y Kotlin 2.2.20. Como JDK usamos el OpenJDK 21.0.10 que viene integrado en Android Studio, en la carpeta `jbr`.

El paquete de la aplicación es `com.utesa.holamundokotlin`. Se configuró con `compileSdk` 36, `targetSdk` 36 y `minSdk` 24, así que la app funciona desde Android 7.0 en adelante. La interfaz usa el sistema de vistas clásico con layout XML y ConstraintLayout, sin Jetpack Compose. Las dependencias son `core-ktx` 1.15.0, `appcompat` 1.6.1, `material` 1.12.0 y `constraintlayout` 2.2.1. Para probar la app usamos un emulador Pixel 7 Pro con Android 17 (API 37).

## 4. Desarrollo del trabajo

### Enrique Rincon: ambiente de desarrollo

Enrique arrancó revisando qué había en la máquina. Android Studio ya estaba instalado y el SDK tenía las plataformas 34, 35, 36 y 36.1, además de las build-tools 35.0.0 y 36.0.0, así que no tuvo que descargar nada del SDK. El problema apareció al buscar el proyecto: la carpeta del repositorio `programacion-deMoviles` estaba vacía y en la computadora no había ningún proyecto con el paquete `com.utesa.holamundokotlin`. Por eso creó el proyecto desde cero en `programacion-deMoviles/HolaMundoKotlin`, con la misma estructura de la plantilla "Empty Views Activity": el módulo `app`, el catálogo de versiones en `gradle/libs.versions.toml`, los temas en `values` y `values-night`, y el `AndroidManifest.xml` con MainActivity como actividad de inicio.

Para escoger las versiones de AGP y Kotlin se fijó en las que ya estaban en la caché de Gradle, y así evitó descargas largas. También generó el Gradle Wrapper con la distribución 8.14 que estaba descargada. Por último dejó listo el emulador Pixel 7 Pro que usamos en las pruebas.

### Eymer Baez: layout y código

Eymer escribió los dos archivos principales. En `activity_main.xml` colocó un ConstraintLayout con un solo TextView, anclado a los cuatro bordes del padre para que quede centrado. El mensaje completo va directo en `android:text`, con `\n` para los saltos de línea, y se agregó `tools:ignore="HardcodedText"` para quitar la advertencia de Android Studio. Le puso 24dp de padding al contenedor y 16dp al TextView, un tamaño de 18sp para el texto y un poco de espacio entre líneas para que no se viera apretado.

Tuvo una dificultad con el título. La tarea pide que "Hola Mundo Kotlin de los estudiantes:" se vea en negrita o más grande, pero un texto escrito directo en el atributo `android:text` no admite estilos en una sola parte: o se pone todo en negrita o nada. En vez de separar el mensaje en dos TextView, que se alejaba de lo pedido, dejó el texto completo en el XML y resolvió el estilo en `MainActivity.kt`. Ahí, después de `setContentView(R.layout.activity_main)`, toma el texto del TextView y le aplica un `StyleSpan` en negrita y un `AbsoluteSizeSpan` de 22sp solo a la primera línea. La clase extiende `AppCompatActivity`, como pedía la tarea. Al inicio de los dos archivos agregó un comentario con el nombre de la app y los cuatro integrantes con sus matrículas.

### Ebelio Francisco: documentación

Ebelio fue anotando cada etapa a medida que el grupo avanzaba: qué se configuró, qué versiones se usaron, qué comandos se corrieron y cuál fue el resultado de cada uno. Así pudimos reconstruir el proceso con datos exactos y no de memoria. Por ejemplo, anotó que la primera compilación terminó en 50 segundos con 34 tareas ejecutadas, y la ruta exacta donde quedó el APK.

También llevó la bitácora de incidencias. Anotó cada problema con su causa y su solución en el momento en que ocurría, incluidos los que no tuvieron que ver con el código, como el rechazo al subir los cambios a GitHub. Con esas notas redactó esta acta y fue confirmando con cada compañero que la descripción de su parte fuera correcta.

### Angel Ventura: pruebas, APK y entrega

Antes de compilar, Angel verificó el JDK. `JAVA_HOME` no estaba configurado y el sistema respondió "Unable to locate a Java Runtime", así que apuntó `JAVA_HOME` al JDK que trae Android Studio (`/Applications/Android Studio.app/Contents/jbr/Contents/Home`) y comprobó con `java -version` que respondía. Después corrió `./gradlew assembleDebug`, que terminó con `BUILD SUCCESSFUL` en el primer intento y generó `app/build/outputs/apk/debug/app-debug.apk`, de unos 6.2 MB.

Con `aapt dump badging` revisó el APK y confirmó que el paquete era `com.utesa.holamundokotlin` y que el nombre visible era "Hola Mundo Kotlin". Luego lo instaló en el emulador con `adb install`, abrió la actividad y tomó una captura de pantalla. Comparó línea por línea los nombres y las matrículas con la lista oficial del grupo, y todo coincidía. Le reportó a Eymer que, a 22sp, el título no cabe en una sola línea en el Pixel 7 Pro y se divide en dos. Lo evaluamos juntos y, por ahora, lo dejamos en 22sp, porque sigue siendo legible y está dentro del rango de tamaños que pide la tarea.

Para cerrar, creó la carpeta `Entrega` con el APK renombrado como `HolaMundoKotlin.apk`, `MainActivity.kt` y `activity_main.xml`, y la comprimió en `HolaMundoKotlin_Baez_Francisco_Rincon_Ventura.zip`. Como trabajamos en macOS, usó `zip` en lugar de `Compress-Archive`, que es de PowerShell en Windows. Después listó el contenido del zip y corrió la prueba de integridad para confirmar que tenía los tres archivos y que no estaba dañado.

## 5. Bitácora de incidencias

| Problema | Causa | Solución |
|---|---|---|
| No se encontraba el proyecto `com.utesa.holamundokotlin` | El repositorio `programacion-deMoviles` estaba vacío y el proyecto no existía en la máquina | Se creó el proyecto desde cero en `programacion-deMoviles/HolaMundoKotlin` con la estructura de la plantilla "Empty Views Activity" |
| "Unable to locate a Java Runtime" al verificar el JDK | `JAVA_HOME` no estaba configurado y no había un JDK instalado aparte | Se usó el JDK que trae Android Studio (carpeta `jbr`, OpenJDK 21.0.10) |
| No se podía poner en negrita solo el título desde el XML | Un texto escrito directo en `android:text` no admite estilos en una sola parte | Se aplicó un `StyleSpan` y un `AbsoluteSizeSpan` a la primera línea desde `MainActivity.kt`, sin sacar el texto del XML |
| `aapt dump badging` mostró un error sobre el atributo `android:icon` | `aapt` no tenía cargados los recursos del sistema al leer el ícono `@android:drawable/sym_def_app_icon` | No afectó la app: se instaló y abrió sin problemas en el emulador |
| El título se divide en dos líneas en el Pixel 7 Pro | A 22sp, el texto no cabe en el ancho de la pantalla | Se mantuvo en 22sp porque sigue siendo legible; bajarlo a 20sp queda como opción |
| Error 403 al subir la rama `eymer-dev` a GitHub | La cuenta `baezeydel-source` no tiene permiso de escritura en `loschiflados124/programacion-deMoviles` | Pendiente: hay que solicitar acceso como colaborador al dueño del repositorio |

## 6. Pruebas realizadas y resultados

La compilación con el Gradle Wrapper terminó sin errores en el primer intento. Revisamos el APK con `aapt` y confirmamos que tiene el paquete y el nombre correctos. Después lo instalamos en el emulador Pixel 7 Pro (Android 17, API 37): la instalación terminó con "Success" y la actividad abrió en frío sin cierres inesperados, en unos 4 segundos.

En la captura de pantalla se ve el título en negrita y más grande que el resto, y debajo los cuatro nombres con sus matrículas, todo centrado. Comparamos cada matrícula con la lista del grupo y no encontramos errores. También confirmamos que el proyecto no tiene dependencias de Jetpack Compose. Al zip le corrimos una prueba de integridad y no reportó errores.

## 7. Entregables

Archivo entregado: `HolaMundoKotlin_Baez_Francisco_Rincon_Ventura.zip`, ubicado en la raíz del proyecto `HolaMundoKotlin`.

| Archivo dentro del zip | Descripción | Origen en el proyecto |
|---|---|---|
| `Entrega/HolaMundoKotlin.apk` | APK de depuración de la app (6.2 MB) | `app/build/outputs/apk/debug/app-debug.apk` |
| `Entrega/MainActivity.kt` | Actividad principal en Kotlin | `app/src/main/java/com/utesa/holamundokotlin/MainActivity.kt` |
| `Entrega/activity_main.xml` | Layout de la pantalla principal | `app/src/main/res/layout/activity_main.xml` |

## 8. Conclusiones del equipo

Aunque la app es de una sola pantalla, la mayor parte del trabajo estuvo en el ambiente y no en el código. Aprendimos que Android Studio trae su propio JDK y que se puede usar para compilar desde la terminal sin instalar Java aparte. También vimos cómo se relacionan las versiones de Gradle, del Android Gradle Plugin y de Kotlin, y por qué conviene revisar cuáles están disponibles antes de fijarlas.

En lo técnico, nos quedó claro que un texto escrito directo en el layout es cómodo, pero tiene límites a la hora de darle estilo, y que los *spans* permiten resaltar una parte del texto sin cambiar la estructura de la vista. Como grupo, nos sirvió repartir los roles desde el principio. Al tener a alguien probando y reportando, detalles como el título dividido en dos líneas se vieron a tiempo, y llevar la bitácora al día hizo mucho más fácil redactar esta acta. Nos queda pendiente resolver el acceso al repositorio de GitHub para que el código quede en el repositorio del grupo.

## 9. Firmas

&nbsp;

______________________________
Enrique Rincon
Matrícula: 1-24-3689

&nbsp;

______________________________
Eymer Baez
Matrícula: 1-24-3833

&nbsp;

______________________________
Ebelio Francisco
Matrícula: 1-24-3647

&nbsp;

______________________________
Angel Ventura
Matrícula: 1-24-3690
