/*
 * App: Ciclo de Vida
 * Estudiantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.ciclodevida

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SegundaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda)

        // Cierra TODA la aplicación (todas las Activities de la tarea), no solo esta pantalla.
        findViewById<Button>(R.id.btnSalir).setOnClickListener { finishAffinity() }

        // Cierra solo esta pantalla y regresa a MainActivity (onRestart, onStart, onResume).
        findViewById<Button>(R.id.btnVolver).setOnClickListener { finish() }
    }
}
