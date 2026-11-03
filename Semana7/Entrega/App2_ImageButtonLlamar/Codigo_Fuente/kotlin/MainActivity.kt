/*
 * App: ImageButtonLlamar
 * Proyecto del documento: Proyecto007 - Control ImageButton
 * Integrantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.imagebuttonllamar

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

// AppCompatActivity es el equivalente actual de la ActionBarActivity del documento.
class MainActivity : AppCompatActivity() {

    // Referencia al TextView donde se muestra el mensaje "Llamando".
    private lateinit var tv1: TextView

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

        // Obtenemos la referencia del TextView tv1.
        tv1 = findViewById(R.id.tv1)
    }

    // Método asociado a la propiedad onClick del ImageButton bi1 (definida en el XML).
    // Debe ser público y recibir un View, igual que en el documento.
    fun llamar(view: View) {
        // Mostramos el mensaje "Llamando" en el TextView.
        tv1.text = getString(R.string.llamando)
        // Complemento visual: un Toast corto con "Llamando...".
        Toast.makeText(this, R.string.llamando_toast, Toast.LENGTH_SHORT).show()
    }
}
