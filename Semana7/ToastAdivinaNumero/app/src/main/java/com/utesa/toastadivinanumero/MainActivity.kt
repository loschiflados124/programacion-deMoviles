/*
 * App: ToastAdivinaNumero
 * Proyecto del documento: Proyecto008 - Notificaciones sencillas mediante la clase Toast
 * Integrantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.toastadivinanumero

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

// AppCompatActivity es el equivalente actual de la ActionBarActivity del documento.
class MainActivity : AppCompatActivity() {

    // El número generado vive en el ViewModel (no cambia al rotar la pantalla).
    private val viewModel: NumeroViewModel by viewModels()

    // Referencia al EditText donde se ingresa el número.
    private lateinit var et1: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Barras del sistema transparentes con íconos claros sobre el verde UTESA.
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContentView(R.layout.activity_main)

        // Los insets de las barras del sistema y de la cámara se aplican como padding
        // en la vista raíz, para que nada quede debajo de la barra de estado.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        // El Toolbar verde hace de ActionBar (muestra el nombre de la app).
        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))

        // Obtenemos la referencia del EditText.
        et1 = findViewById(R.id.editText)

        // Generamos el número aleatorio entre 1 y 100000 y lo mostramos con un Toast
        // de duración larga. Solo la primera vez: al rotar se conserva el del ViewModel.
        if (viewModel.num == 0) {
            val num = viewModel.generarNumero()
            Toast.makeText(this, num.toString(), Toast.LENGTH_LONG).show()
        }
    }

    // Método asociado a la propiedad onClick del botón "Controlar" (definida en el XML).
    fun controlar(v: View) {
        // Leemos el valor ingresado en el EditText.
        val valorIngresado = et1.text.toString()
        // Convertimos a entero; si está vacío o no es válido, toIntOrNull() devuelve null
        // (en el documento, Integer.parseInt fallaría y cerraría la app).
        val valor = valorIngresado.toIntOrNull()
        if (valor == null) {
            Toast.makeText(this, R.string.ingrese_numero, Toast.LENGTH_SHORT).show()
            return
        }
        // Comparamos el número generado con el ingresado e informamos con otro Toast.
        if (valor == viewModel.num) {
            Toast.makeText(this, R.string.acerto, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, R.string.no_acerto, Toast.LENGTH_LONG).show()
        }
    }

    // Método asociado a la propiedad onClick del botón "Nuevo número" (mejora):
    // genera otro número, lo muestra en un Toast y limpia el EditText para jugar de nuevo.
    fun nuevoNumero(v: View) {
        val num = viewModel.generarNumero()
        Toast.makeText(this, num.toString(), Toast.LENGTH_LONG).show()
        et1.text.clear()
    }
}
