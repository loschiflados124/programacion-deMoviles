/*
 * App: Registro Estudiantil UTESA
 * Integrantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.registroestudiantil

/** Datos capturados de un estudiante en el formulario. */
data class Estudiante(
    val nombre: String,
    val matricula: String,
    val edad: Int,
    val carrera: String,
    val tanda: String,
    val asignaturas: List<String>
)
