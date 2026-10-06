# Notas del proceso: app "Ciclo de Vida"

**Grupo 1:** Eymer Baez (1-24-3833), Ebelio Francisco (1-24-3647), Enrique Rincon (1-24-3689), Angel Ventura (1-24-3690)
**Asignatura:** Programación de Dispositivos Móviles (UTESA). Tema: Principios de arquitectura y Activities en Android.
**Paquete:** `com.utesa.ciclodevida`

## 1. Versiones usadas

| Componente | Versión |
|---|---|
| Android Studio | 2026.1 (build 261.25134.95) |
| JDK | OpenJDK 21.0.10 (carpeta `jbr` de Android Studio; `JAVA_HOME` no estaba configurado) |
| Gradle (wrapper) | 8.14 |
| Android Gradle Plugin (AGP) | 8.13.2 |
| Kotlin | 2.2.20 |
| compileSdk | 36 |
| minSdk | 24 |
| targetSdk | 36 |
| core-ktx / appcompat / material / constraintlayout | 1.15.0 / 1.6.1 / 1.12.0 / 2.2.1 |
| lifecycle-viewmodel-ktx / lifecycle-livedata-ktx | 2.8.7 |
| activity-ktx | 1.9.3 |
| Emulador de prueba | Pixel 7 Pro, Android 17 (imagen API 37.1) |

## 2. Archivos creados o modificados

La carpeta `MensajedeDespedida` estaba vacía, así que el proyecto se creó desde cero con la estructura de la plantilla "Empty Views Activity" (copiando el esqueleto de Gradle de `HolaMundoKotlin`).

Copiados de `HolaMundoKotlin` (con el nombre del proyecto cambiado a `CicloDeVida` en `settings.gradle.kts`): `build.gradle.kts`, `settings.gradle.kts`, `gradle.properties`, `local.properties`, `gradlew`, `gradlew.bat`, `gradle/wrapper/*`, `.gitignore`, `app/proguard-rules.pro`.

Modificados respecto a esa base:
- `gradle/libs.versions.toml`: se agregaron `lifecycle`, `activity` y las librerías `lifecycle-viewmodel-ktx`, `lifecycle-livedata-ktx` y `activity-ktx`.
- `app/build.gradle.kts`: namespace/applicationId `com.utesa.ciclodevida` y las tres dependencias nuevas.

Creados:
- `app/src/main/java/com/utesa/ciclodevida/MainActivity.kt`
- `app/src/main/java/com/utesa/ciclodevida/SegundaActivity.kt`
- `app/src/main/java/com/utesa/ciclodevida/CicloVidaViewModel.kt`
- `app/src/main/res/layout/activity_main.xml`
- `app/src/main/res/layout/activity_segunda.xml`
- `app/src/main/res/values/colors.xml` (verde `#076633`)
- `app/src/main/res/values/strings.xml`
- `app/src/main/res/values/themes.xml` y `values-night/themes.xml` (tema claro fijo, para que el verde se vea igual en modo noche)
- `app/src/main/AndroidManifest.xml` (MainActivity como LAUNCHER y registro de SegundaActivity)
- `Entrega/` y el `.zip` de entrega
- Este archivo

No se usa Jetpack Compose.

## 3. Errores que ocurrieron realmente y cómo se resolvieron

- **El proyecto no existía.** La carpeta `MensajedeDespedida` estaba vacía y no había ningún proyecto con el paquete `com.utesa.ciclodevida` en la computadora. Se creó el proyecto manualmente con la estructura de la plantilla.
- **`JAVA_HOME` vacío y sin Java en el PATH** (`java -version` daba "Unable to locate a Java Runtime"). Se resolvió apuntando `JAVA_HOME` al JDK de Android Studio: `/Applications/Android Studio.app/Contents/jbr/Contents/Home`.
- **Errores de compilación: ninguno.** `./gradlew assembleDebug` terminó en BUILD SUCCESSFUL en el primer intento.
- **Primera prueba en el emulador falló por culpa del script de prueba, no de la app.** Los toques con `adb input tap` estaban en coordenadas adivinadas y no cayeron sobre el botón (el BACK posterior cerró la app). Además, las primeras capturas de pantalla se tomaron con la pantalla de arranque (splash) todavía visible. Se corrigió buscando las coordenadas reales de los botones con `uiautomator dump` y esperando más tiempo tras abrir la app.
- **El emulador quedó en horizontal** después de probar la rotación, lo que descuadró la segunda corrida. Se volvió a vertical con `settings put system user_rotation 0`.
- **Se cerró el emulador antes de probar el botón "Salir"** por un error del script. Se volvió a arrancar el emulador y se repitió la prueba completa.

## 4. Resultados de las pruebas (emulador Pixel 7 Pro)

| Prueba | Resultado |
|---|---|
| `./gradlew assembleDebug` | BUILD SUCCESSFUL; existe `app/build/outputs/apk/debug/app-debug.apk` (6 261 479 bytes) |
| Instalación con `adb install` | Success |
| Arranque de la app | Logcat (TAG `CicloDeVida`) registra `onCreate`, `onStart`, `onResume` |
| Botón "Ir a la segunda pantalla" | Se muestra SegundaActivity con el mensaje y los 4 integrantes; MainActivity registra `onPause` y `onStop` |
| Botón "Volver" | Regresa a MainActivity; se registran `onRestart`, `onStart`, `onResume` |
| Rotación de pantalla | Logcat: `onPause`, `onStop`, `onDestroy`, `onCreate`, `onStart`, `onResume`. En pantalla la lista conservó todos los eventos anteriores y siguió con los de la nueva instancia (el ViewModel guarda el estado) |
| Botón "Salir" (`finishAffinity()`) | MainActivity registró `onPause`, `onStop`, `onDestroy`; no quedó ninguna Activity de la app y el launcher quedó al frente |
| Zip de entrega | `unzip -tq`: "No errors detected"; contiene el APK, MainActivity.kt, SegundaActivity.kt, activity_main.xml y activity_segunda.xml (sin subcarpeta) |

No se probó: el botón "Limpiar registro" se revisó solo por código (llama a `viewModel.limpiar()`), no se ejecutó en el emulador. Tampoco se probó en un dispositivo físico ni en otras versiones de Android.
