/*
 * App: Ciclo de Vida
 * Estudiantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.ciclodevida

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // La Activity NO guarda la lista de eventos: vive en el ViewModel, que
    // sobrevive a la rotación. Aquí solo se observa y se muestra.
    private val viewModel: CicloVidaViewModel by viewModels()

    private lateinit var tvEstadoActual: TextView
    private lateinit var tvRegistro: TextView
    private lateinit var scrollRegistro: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvEstadoActual = findViewById(R.id.tvEstadoActual)
        tvRegistro = findViewById(R.id.tvRegistro)
        scrollRegistro = findViewById(R.id.scrollRegistro)

        // Observadores: cada cambio en el ViewModel se refleja en pantalla.
        viewModel.eventos.observe(this) { eventos ->
            tvRegistro.text = if (eventos.isEmpty()) {
                getString(R.string.registro_vacio)
            } else {
                eventos.joinToString("\n")
            }
            scrollRegistro.post { scrollRegistro.fullScroll(ScrollView.FOCUS_DOWN) }
        }
        viewModel.estadoActual.observe(this) { estado ->
            tvEstadoActual.text = getString(
                R.string.estado_actual,
                estado ?: getString(R.string.estado_inicial)
            )
        }

        findViewById<Button>(R.id.btnLimpiar).setOnClickListener { viewModel.limpiar() }
        findViewById<Button>(R.id.btnSegundaPantalla).setOnClickListener {
            // Intent explícito: se indica la clase exacta de la Activity destino.
            startActivity(Intent(this, SegundaActivity::class.java))
        }

        registrar("onCreate", R.string.desc_onCreate)
    }

    override fun onStart() {
        super.onStart()
        registrar("onStart", R.string.desc_onStart)
    }

    override fun onResume() {
        super.onResume()
        registrar("onResume", R.string.desc_onResume)
    }

    override fun onPause() {
        super.onPause()
        registrar("onPause", R.string.desc_onPause)
    }

    override fun onStop() {
        super.onStop()
        registrar("onStop", R.string.desc_onStop)
    }

    override fun onRestart() {
        super.onRestart()
        registrar("onRestart", R.string.desc_onRestart)
    }

    override fun onDestroy() {
        super.onDestroy()
        registrar("onDestroy", R.string.desc_onDestroy)
    }

    /** Escribe el evento en Logcat (TAG "CicloDeVida") y lo agrega a la lista visible. */
    private fun registrar(metodo: String, @StringRes descripcion: Int) {
        val texto = getString(descripcion)
        Log.d(TAG, "$metodo(): $texto")
        viewModel.registrar(metodo) { hora ->
            getString(R.string.evento_formato, hora, metodo, texto)
        }
    }

    private companion object {
        const val TAG = "CicloDeVida"
    }
}
