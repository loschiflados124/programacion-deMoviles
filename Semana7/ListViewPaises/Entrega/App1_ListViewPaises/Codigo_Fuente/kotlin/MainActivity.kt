/*
 * App: ListViewPaises
 * Proyecto del documento: Proyecto006 - Control ListView (con una lista de String)
 * Integrantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.listviewpaises

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

// AppCompatActivity es el equivalente actual de la ActionBarActivity del documento.
class MainActivity : AppCompatActivity() {

    // Arreglo con los nombres de los países de Sudamérica (se muestran en el ListView).
    private val paises = arrayOf(
        "Argentina", "Chile", "Paraguay", "Bolivia", "Peru",
        "Ecuador", "Brasil", "Colombia", "Venezuela", "Uruguay"
    )

    // Arreglo paralelo con la cantidad de habitantes de cada país (misma posición).
    private val habitantes = arrayOf(
        "40000000", "17000000", "6500000", "10000000", "30000000",
        "14000000", "183000000", "44000000", "29000000", "3500000"
    )

    // Referencias a los controles definidos en activity_main.xml.
    private lateinit var tv1: TextView
    private lateinit var lv1: ListView

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

        // Obtenemos las referencias del TextView y del ListView.
        tv1 = findViewById(R.id.tv1)
        lv1 = findViewById(R.id.listView)

        // Creamos un ArrayAdapter con el layout predefinido simple_list_item_1
        // y el arreglo de países, y lo asociamos al ListView.
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paises)
        lv1.adapter = adapter

        // Al seleccionar un país, mostramos en tv1 su cantidad de habitantes
        // (se usa la posición seleccionada para acceder al arreglo paralelo).
        lv1.setOnItemClickListener { _, _, posicion, _ ->
            tv1.text = getString(
                R.string.poblacion_de,
                lv1.getItemAtPosition(posicion),
                habitantes[posicion]
            )
        }
    }
}
