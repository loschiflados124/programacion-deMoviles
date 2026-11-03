/*
 * App: Adopción de Mascotas
 * Integrantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.adopcionmascotas

/** Datos capturados en una solicitud de adopción. */
data class Solicitud(
    val tipo: String,
    val imagen: Int, // id del VectorDrawable de la mascota
    val nombreMascota: String,
    val tamano: String,
    val edadMeses: Int,
    val condiciones: List<String>,
    val adoptante: String,
    val telefono: String
)
