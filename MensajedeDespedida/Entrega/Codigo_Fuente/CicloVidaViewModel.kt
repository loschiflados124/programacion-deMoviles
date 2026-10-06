/*
 * App: Ciclo de Vida
 * Estudiantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.ciclodevida

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Guarda el registro de eventos del ciclo de vida.
 *
 * Principio de arquitectura: las Activities no deben almacenar datos ni estado,
 * porque el sistema las destruye y recrea (por ejemplo al rotar la pantalla) y
 * todo lo que tengan en variables se pierde. El ViewModel sobrevive a esos
 * cambios de configuración, así que el estado vive aquí y la Activity solo
 * observa y muestra.
 */
class CicloVidaViewModel : ViewModel() {

    private val formatoHora = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    private val _eventos = MutableLiveData<List<String>>(emptyList())
    val eventos: LiveData<List<String>> = _eventos

    private val _estadoActual = MutableLiveData<String?>(null)
    val estadoActual: LiveData<String?> = _estadoActual

    /** Agrega una línea "HH:mm:ss - texto" al registro y actualiza el estado actual. */
    fun registrar(metodo: String, linea: (hora: String) -> String) {
        val hora = formatoHora.format(Date())
        _eventos.value = _eventos.value.orEmpty() + linea(hora)
        _estadoActual.value = metodo
    }

    /** Vacía la lista de eventos (el estado actual se conserva). */
    fun limpiar() {
        _eventos.value = emptyList()
    }
}
