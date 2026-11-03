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

import androidx.lifecycle.ViewModel

// Guarda el número generado. El ViewModel sobrevive a la rotación de la pantalla,
// así el número no cambia cuando la actividad se vuelve a crear.
class NumeroViewModel : ViewModel() {

    // Número aleatorio que el usuario debe recordar (0 = todavía no se ha generado).
    var num = 0
        private set

    // Genera un nuevo número aleatorio entre 1 y 100000 y lo devuelve.
    fun generarNumero(): Int {
        num = (1..100000).random()
        return num
    }
}
