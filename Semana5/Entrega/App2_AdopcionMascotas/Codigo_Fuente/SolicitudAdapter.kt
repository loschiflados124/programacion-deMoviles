/*
 * App: Adopción de Mascotas
 * Integrantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.adopcionmascotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Adaptador personalizado del ListView: cada fila (item_solicitud.xml) muestra
 * la imagen pequeña de la mascota, su nombre y el nombre del adoptante.
 */
class SolicitudAdapter(private val context: Context) : BaseAdapter() {

    private var solicitudes: List<Solicitud> = emptyList()

    fun actualizar(nuevas: List<Solicitud>) {
        solicitudes = nuevas
        notifyDataSetChanged()
    }

    override fun getCount(): Int = solicitudes.size

    override fun getItem(position: Int): Solicitud = solicitudes[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Se reutiliza la fila si ya existe (patrón ViewHolder).
        val fila = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_solicitud, parent, false)
            .also { it.tag = ViewHolder(it) }
        val holder = fila.tag as ViewHolder

        val s = getItem(position)
        holder.imagen.setImageResource(s.imagen)
        holder.nombre.text = context.getString(R.string.fila_tipo, s.nombreMascota, s.tipo)
        holder.adoptante.text = context.getString(R.string.fila_adoptante, s.adoptante)
        return fila
    }

    private class ViewHolder(fila: View) {
        val imagen: ImageView = fila.findViewById(R.id.ivItemMascota)
        val nombre: TextView = fila.findViewById(R.id.tvItemNombre)
        val adoptante: TextView = fila.findViewById(R.id.tvItemAdoptante)
    }
}
