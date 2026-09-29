/*
 * App: Hola Mundo Kotlin
 * Estudiantes:
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Isael Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.holamundokotlin

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // El texto completo viene de activity_main.xml; aquí solo se resalta
        // la primera línea (el título) en negrita y a 22sp.
        val tvMensaje = findViewById<TextView>(R.id.tvMensaje)
        val texto = SpannableString(tvMensaje.text)
        val finTitulo = texto.indexOf('\n').takeIf { it >= 0 } ?: texto.length
        texto.setSpan(StyleSpan(Typeface.BOLD), 0, finTitulo, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        texto.setSpan(AbsoluteSizeSpan(22, true), 0, finTitulo, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvMensaje.text = texto
    }
}
