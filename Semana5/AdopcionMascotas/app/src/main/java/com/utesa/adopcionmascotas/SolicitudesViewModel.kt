/*
 * App: Adopción de Mascotas
 * Integrantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.adopcionmascotas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Guarda la lista de solicitudes. El ViewModel sobrevive a la rotación de
 * pantalla, así que la lista no se pierde cuando la Activity se recrea.
 */
class SolicitudesViewModel : ViewModel() {

    private val _solicitudes = MutableLiveData<List<Solicitud>>(emptyList())
    val solicitudes: LiveData<List<Solicitud>> = _solicitudes

    fun agregar(solicitud: Solicitud) {
        _solicitudes.value = _solicitudes.value.orEmpty() + solicitud
    }
}
