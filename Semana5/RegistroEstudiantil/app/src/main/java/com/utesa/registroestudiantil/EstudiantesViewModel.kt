/*
 * App: Registro Estudiantil UTESA
 * Integrantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.registroestudiantil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Guarda la lista de estudiantes registrados. El ViewModel sobrevive a la
 * rotación de pantalla, así que la lista no se pierde cuando la Activity se recrea.
 */
class EstudiantesViewModel : ViewModel() {

    private val _estudiantes = MutableLiveData<List<Estudiante>>(emptyList())
    val estudiantes: LiveData<List<Estudiante>> = _estudiantes

    fun agregar(estudiante: Estudiante) {
        _estudiantes.value = _estudiantes.value.orEmpty() + estudiante
    }

    fun eliminar(posicion: Int) {
        _estudiantes.value = _estudiantes.value.orEmpty().filterIndexed { i, _ -> i != posicion }
    }

    fun obtener(posicion: Int): Estudiante? = _estudiantes.value?.getOrNull(posicion)

    fun existeMatricula(matricula: String): Boolean =
        _estudiantes.value.orEmpty().any { it.matricula == matricula }
}
