/*
 * App: Registro Estudiantil UTESA
 * Integrantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.registroestudiantil

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // La lista de estudiantes vive en el ViewModel (no se pierde al rotar).
    private val viewModel: EstudiantesViewModel by viewModels()

    private lateinit var etNombre: EditText
    private lateinit var etMatricula: EditText
    private lateinit var etEdad: EditText
    private lateinit var spCarrera: Spinner
    private lateinit var rgTanda: RadioGroup
    private lateinit var checkAsignaturas: List<CheckBox>
    private lateinit var tvContador: TextView
    private lateinit var tvListaVacia: TextView
    private lateinit var adaptadorLista: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Barras del sistema transparentes con íconos claros sobre el verde UTESA.
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContentView(R.layout.activity_main)

        // Los insets de las barras del sistema (y de la cámara) se aplican como padding
        // en la vista raíz, para que nada quede debajo de la barra de estado.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val barras = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom)
            insets
        }

        etNombre = findViewById(R.id.etNombre)
        etMatricula = findViewById(R.id.etMatricula)
        etEdad = findViewById(R.id.etEdad)
        spCarrera = findViewById(R.id.spCarrera)
        rgTanda = findViewById(R.id.rgTanda)
        checkAsignaturas = listOf(
            findViewById(R.id.cbMovil),
            findViewById(R.id.cbBaseDatos),
            findViewById(R.id.cbRedes),
            findViewById(R.id.cbIA)
        )
        tvContador = findViewById(R.id.tvContador)
        tvListaVacia = findViewById(R.id.tvListaVacia)

        // Spinner: las carreras se cargan desde res/values/arrays.xml.
        ArrayAdapter.createFromResource(
            this, R.array.carreras, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spCarrera.adapter = adapter
        }

        // ListView: muestra "nombre - matrícula - carrera" de cada estudiante.
        val lvEstudiantes = findViewById<ListView>(R.id.lvEstudiantes)
        adaptadorLista = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        lvEstudiantes.adapter = adaptadorLista

        // Tocar un elemento: Toast con los datos completos del estudiante.
        lvEstudiantes.setOnItemClickListener { _, _, posicion, _ ->
            viewModel.obtener(posicion)?.let { mostrarToastDetalle(detalle(it)) }
        }
        // Mantener presionado: confirmar antes de eliminar.
        lvEstudiantes.setOnItemLongClickListener { _, _, posicion, _ ->
            viewModel.obtener(posicion)?.let { confirmarEliminar(posicion, it) }
            true
        }

        // Cada cambio en la lista del ViewModel actualiza el ListView y el contador.
        viewModel.estudiantes.observe(this) { lista ->
            adaptadorLista.clear()
            adaptadorLista.addAll(lista.map {
                getString(R.string.fila_estudiante, it.nombre, it.matricula, it.carrera)
            })
            tvContador.text = getString(R.string.contador, lista.size)
            tvListaVacia.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
        }

        findViewById<Button>(R.id.btnRegistrar).setOnClickListener { registrar() }
        findViewById<Button>(R.id.btnLimpiar).setOnClickListener {
            limpiarFormulario()
            Toast.makeText(this, R.string.msg_limpio, Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.btnDesarrolladores).setOnClickListener {
            startActivity(Intent(this, DesarrolladoresActivity::class.java))
        }
    }

    /** Valida el formulario; si todo está bien, registra y muestra el AlertDialog. */
    private fun registrar() {
        val nombre = etNombre.text.toString().trim()
        val matricula = etMatricula.text.toString().trim()
        val edadTexto = etEdad.text.toString().trim()
        val edad = edadTexto.toIntOrNull()

        // Se revisan todos los campos para marcar cada error con setError().
        var primerError: EditText? = null
        fun marcar(campo: EditText, mensaje: Int) {
            campo.error = getString(mensaje)
            if (primerError == null) primerError = campo
        }

        if (nombre.isEmpty()) marcar(etNombre, R.string.error_nombre)
        when {
            matricula.isEmpty() -> marcar(etMatricula, R.string.error_matricula)
            !FORMATO_MATRICULA.matches(matricula) -> marcar(etMatricula, R.string.error_matricula_formato)
            viewModel.existeMatricula(matricula) -> marcar(etMatricula, R.string.error_matricula_repetida)
        }
        when {
            edadTexto.isEmpty() -> marcar(etEdad, R.string.error_edad)
            edad == null || edad !in 15..99 -> marcar(etEdad, R.string.error_edad_rango)
        }

        // Si falta algo, no se registra nada.
        primerError?.let {
            it.requestFocus()
            return
        }

        val tanda = findViewById<RadioButton>(rgTanda.checkedRadioButtonId).text.toString()
        val estudiante = Estudiante(
            nombre = nombre,
            matricula = matricula,
            edad = edad!!,
            carrera = spCarrera.selectedItem.toString(),
            tanda = tanda,
            asignaturas = checkAsignaturas.filter { it.isChecked }.map { it.text.toString() }
        )
        viewModel.agregar(estudiante)

        // AlertDialog con toda la información capturada.
        AlertDialog.Builder(this)
            .setTitle(R.string.dlg_registro_titulo)
            .setMessage(detalle(estudiante))
            .setPositiveButton(R.string.aceptar, null)
            .show()

        limpiarFormulario()
    }

    /** Texto con todos los datos de un estudiante. */
    private fun detalle(e: Estudiante): String = getString(
        R.string.detalle_estudiante,
        e.nombre, e.matricula, e.edad, e.carrera, e.tanda,
        e.asignaturas.ifEmpty { listOf(getString(R.string.ninguna)) }.joinToString(", ")
    )

    /** Toast con vista propia: uno de solo texto se recorta a 2 líneas en Android 12+. */
    private fun mostrarToastDetalle(texto: String) {
        val vista = layoutInflater.inflate(R.layout.toast_detalle, null) as TextView
        vista.text = texto
        @Suppress("DEPRECATION")
        Toast(this).apply {
            duration = Toast.LENGTH_LONG
            view = vista
        }.show()
    }

    private fun confirmarEliminar(posicion: Int, e: Estudiante) {
        AlertDialog.Builder(this)
            .setTitle(R.string.dlg_eliminar_titulo)
            .setMessage(getString(R.string.dlg_eliminar_mensaje, e.nombre, e.matricula))
            .setPositiveButton(R.string.eliminar) { _, _ ->
                viewModel.eliminar(posicion)
                Toast.makeText(this, R.string.msg_eliminado, Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(R.string.cancelar, null)
            .show()
    }

    /** Deja el formulario como al abrir la app. */
    private fun limpiarFormulario() {
        listOf(etNombre, etMatricula, etEdad).forEach {
            it.text.clear()
            it.error = null
        }
        spCarrera.setSelection(0)
        rgTanda.check(R.id.rbMatutina)
        checkAsignaturas.forEach { it.isChecked = false }
        etNombre.clearFocus()
    }

    companion object {
        // Formato de matrícula UTESA: 1-24-0000
        private val FORMATO_MATRICULA = Regex("""^\d-\d{2}-\d{4}$""")
    }
}
