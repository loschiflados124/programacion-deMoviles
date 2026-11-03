/*
 * App: Adopción de Mascotas
 * Integrantes (Grupo 1):
 *   Eymer Baez - Matrícula: 1-24-3833
 *   Ebelio Francisco - Matrícula: 1-24-3647
 *   Enrique Rincon - Matrícula: 1-24-3689
 *   Angel Ventura - Matrícula: 1-24-3690
 */
package com.utesa.adopcionmascotas

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // La lista de solicitudes vive en el ViewModel (no se pierde al rotar).
    private val viewModel: SolicitudesViewModel by viewModels()

    private lateinit var spTipo: Spinner
    private lateinit var ivMascota: ImageView
    private lateinit var tvTipoMascota: TextView
    private lateinit var tvDescripcionMascota: TextView
    private lateinit var etAdoptante: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etNombreMascota: EditText
    private lateinit var rgTamano: RadioGroup
    private lateinit var checkCondiciones: List<CheckBox>
    private lateinit var sbEdad: SeekBar
    private lateinit var tvEdad: TextView
    private lateinit var tvContador: TextView
    private lateinit var tvListaVacia: TextView
    private lateinit var adaptador: SolicitudAdapter

    // Imagen y descripción de cada mascota, en el mismo orden que el Spinner (arrays.xml).
    private lateinit var imagenes: IntArray
    private lateinit var descripciones: Array<String>

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

        spTipo = findViewById(R.id.spTipo)
        ivMascota = findViewById(R.id.ivMascota)
        tvTipoMascota = findViewById(R.id.tvTipoMascota)
        tvDescripcionMascota = findViewById(R.id.tvDescripcionMascota)
        etAdoptante = findViewById(R.id.etAdoptante)
        etTelefono = findViewById(R.id.etTelefono)
        etNombreMascota = findViewById(R.id.etNombreMascota)
        rgTamano = findViewById(R.id.rgTamano)
        checkCondiciones = listOf(
            findViewById(R.id.cbVacunado),
            findViewById(R.id.cbEsterilizado),
            findViewById(R.id.cbTransporte)
        )
        sbEdad = findViewById(R.id.sbEdad)
        tvEdad = findViewById(R.id.tvEdad)
        tvContador = findViewById(R.id.tvContador)
        tvListaVacia = findViewById(R.id.tvListaVacia)

        resources.obtainTypedArray(R.array.imagenes_mascota).let { arreglo ->
            imagenes = IntArray(arreglo.length()) { arreglo.getResourceId(it, 0) }
            arreglo.recycle()
        }
        descripciones = resources.getStringArray(R.array.descripciones_mascota)

        // Spinner: los tipos de mascota se cargan desde res/values/arrays.xml.
        ArrayAdapter.createFromResource(
            this, R.array.tipos_mascota, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spTipo.adapter = adapter
        }

        // Al elegir una mascota en el Spinner, cambia la imagen y sus datos.
        spTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, posicion: Int, id: Long) {
                ivMascota.setImageResource(imagenes[posicion])
                tvTipoMascota.text = parent.getItemAtPosition(posicion).toString()
                tvDescripcionMascota.text = descripciones[posicion]
            }

            override fun onNothingSelected(parent: AdapterView<*>) = Unit
        }

        // SeekBar: progreso 0..119 equivale a 1..120 meses.
        sbEdad.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progreso: Int, delUsuario: Boolean) {
                mostrarEdad()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar) = Unit
        })
        mostrarEdad()

        // ListView con adaptador personalizado; al tocar una fila se muestra el detalle.
        val lvSolicitudes = findViewById<ListView>(R.id.lvSolicitudes)
        adaptador = SolicitudAdapter(this)
        lvSolicitudes.adapter = adaptador
        lvSolicitudes.setOnItemClickListener { _, _, posicion, _ ->
            mostrarToastDetalle(resumen(adaptador.getItem(posicion)))
        }

        // Cada cambio en la lista del ViewModel actualiza el ListView y el contador.
        viewModel.solicitudes.observe(this) { lista ->
            adaptador.actualizar(lista)
            tvContador.text = getString(R.string.contador, lista.size)
            tvListaVacia.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
        }

        findViewById<Button>(R.id.btnSolicitar).setOnClickListener { solicitar() }
        findViewById<Button>(R.id.btnLimpiar).setOnClickListener {
            limpiarFormulario()
            Toast.makeText(this, R.string.msg_limpio, Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.btnDesarrolladores).setOnClickListener {
            startActivity(Intent(this, DesarrolladoresActivity::class.java))
        }
    }

    private fun edadMeses(): Int = sbEdad.progress + 1

    private fun textoEdad(meses: Int): String =
        resources.getQuantityString(R.plurals.edad_meses, meses, meses)

    private fun mostrarEdad() {
        tvEdad.text = textoEdad(edadMeses())
    }

    /** Valida; si todo está bien, muestra el diálogo con la imagen y agrega la solicitud. */
    private fun solicitar() {
        val adoptante = etAdoptante.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()
        val nombreMascota = etNombreMascota.text.toString().trim()

        // Se revisan todos los campos para marcar cada error con setError().
        var primerError: EditText? = null
        fun marcar(campo: EditText, mensaje: Int) {
            campo.error = getString(mensaje)
            if (primerError == null) primerError = campo
        }

        if (adoptante.isEmpty()) marcar(etAdoptante, R.string.error_adoptante)
        when {
            telefono.isEmpty() -> marcar(etTelefono, R.string.error_telefono)
            telefono.count { it.isDigit() } != 10 -> marcar(etTelefono, R.string.error_telefono_formato)
        }
        if (nombreMascota.isEmpty()) marcar(etNombreMascota, R.string.error_nombre_mascota)

        // Si falta algo, no se registra nada.
        primerError?.let {
            it.requestFocus()
            return
        }

        val posicion = spTipo.selectedItemPosition
        val solicitud = Solicitud(
            tipo = spTipo.selectedItem.toString(),
            imagen = imagenes[posicion],
            nombreMascota = nombreMascota,
            tamano = findViewById<RadioButton>(rgTamano.checkedRadioButtonId).text.toString(),
            edadMeses = edadMeses(),
            condiciones = checkCondiciones.filter { it.isChecked }.map { it.text.toString() },
            adoptante = adoptante,
            telefono = telefono
        )

        mostrarDialogoResumen(solicitud.imagen, resumen(solicitud))
        // Luego de mostrar el resumen, la solicitud se agrega a la lista.
        viewModel.agregar(solicitud)
        limpiarFormulario()
    }

    /** Texto con todos los datos de una solicitud. */
    private fun resumen(s: Solicitud): String = getString(
        R.string.resumen_solicitud,
        s.tipo, s.nombreMascota, s.tamano, textoEdad(s.edadMeses),
        s.condiciones.ifEmpty { listOf(getString(R.string.sin_condiciones)) }.joinToString(", "),
        s.adoptante, s.telefono
    )

    /** AlertDialog con layout propio (dialog_resumen.xml): imagen + resumen. */
    private fun mostrarDialogoResumen(@DrawableRes imagen: Int, texto: String) {
        val vista = layoutInflater.inflate(R.layout.dialog_resumen, null)
        vista.findViewById<ImageView>(R.id.ivDialogoMascota).setImageResource(imagen)
        vista.findViewById<TextView>(R.id.tvDialogoResumen).text = texto
        AlertDialog.Builder(this)
            .setTitle(R.string.dlg_solicitud_titulo)
            .setView(vista)
            .setPositiveButton(R.string.aceptar) { _, _ ->
                Toast.makeText(this, R.string.msg_solicitud_agregada, Toast.LENGTH_SHORT).show()
            }
            .show()
    }

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

    /** Deja el formulario como al abrir la app (la mascota elegida se mantiene). */
    private fun limpiarFormulario() {
        listOf(etAdoptante, etTelefono, etNombreMascota).forEach {
            it.text.clear()
            it.error = null
        }
        rgTamano.check(R.id.rbMediano)
        checkCondiciones.forEach { it.isChecked = false }
        sbEdad.progress = 11
        etAdoptante.clearFocus()
    }
}
