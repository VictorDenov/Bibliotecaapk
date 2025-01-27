package com.example.appbiblioteca.Ui

// ActivityAgregarLibro.kt
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.appbiblioteca.R
import com.example.appbiblioteca.data.Categoria
import com.example.appbiblioteca.data.CreateLibroDto
import com.example.appbiblioteca.utils.RetrofitClient
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ActivityAgregarLibro : AppCompatActivity() {

    private lateinit var ivPortada: ShapeableImageView
    private lateinit var etIsbn: EditText
    private lateinit var etTipo: EditText
    private lateinit var etTitulo: EditText
    private lateinit var etAreaDeConocimiento: EditText
    private lateinit var etAutor: EditText
    private lateinit var etNumeroEjemplares: EditText
    private lateinit var etCodigoUbicacionFisica: EditText
    private lateinit var etVerificacion: EditText
    private lateinit var etEditorial: EditText
    private lateinit var etAno: EditText
    private lateinit var etEdicion: EditText
    private lateinit var spinnerCategoria: Spinner
    private lateinit var btnSeleccionarImagen: MaterialButton
    private lateinit var btnGuardar: MaterialButton
    private var imageUri: Uri? = null
    private val pickImage = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_libro)

        val toolbar: Toolbar = findViewById(R.id.toolbarlibro)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        ivPortada = findViewById(R.id.iv_portada)
        etIsbn = findViewById(R.id.et_isbn)
        etTipo = findViewById(R.id.et_tipo)
        etTitulo = findViewById(R.id.et_titulo)
        etAreaDeConocimiento = findViewById(R.id.et_area_de_conocimiento)
        etAutor = findViewById(R.id.et_autor)
        etNumeroEjemplares = findViewById(R.id.et_numero_ejemplares)
        etCodigoUbicacionFisica = findViewById(R.id.et_codigo_ubicacion_fisica)
        etVerificacion = findViewById(R.id.et_verificacion)
        etEditorial = findViewById(R.id.et_editorial)
        etAno = findViewById(R.id.et_ano)
        etEdicion = findViewById(R.id.et_edicion)
        spinnerCategoria = findViewById(R.id.spinner_categoria)
        btnSeleccionarImagen = findViewById(R.id.btn_seleccionar_imagen)
        btnGuardar = findViewById(R.id.btn_guardar)

        // Seleccionar imagen
        btnSeleccionarImagen.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        // Guardar libro
        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val autor = etAutor.text.toString()

            if (imageUri != null) {
                // Obtener la lista de categorías completas desde el tag del Spinner
                val categorias = spinnerCategoria.tag as List<Categoria>

                // Obtener el objeto completo de la categoría seleccionada
                val categoriaSeleccionada = categorias[spinnerCategoria.selectedItemPosition]

                // Obtener el ID de la categoría seleccionada
                val categoriaId = categoriaSeleccionada.id

                // Llamar a la función para subir el libro
                uploadLibro(titulo, autor, imageUri!!, categoriaId)
            } else {
                Toast.makeText(this, "Por favor selecciona una imagen", Toast.LENGTH_SHORT).show()
            }
        }

        // Llamar a la función para cargar las categorías
        obtenerCategorias()
    }




    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // Al hacer clic en el icono de retroceso, se hace un back
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Método para cargar categorías
    private fun obtenerCategorias() {
        val apiService = RetrofitClient.apiService

        apiService.getCategorias().enqueue(object : Callback<List<Categoria>> {
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                if (response.isSuccessful && response.body() != null) {
                    val categorias = response.body() ?: emptyList()

                    // Guardamos la lista completa de categorías en el tag del Spinner
                    spinnerCategoria.tag = categorias

                    // Actualizamos el Spinner con los nombres de las categorías
                    val categoriaNames = categorias.map { it.nombre }
                    val adapter = ArrayAdapter(
                        this@ActivityAgregarLibro,
                        android.R.layout.simple_spinner_item,
                        categoriaNames
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerCategoria.adapter = adapter
                } else {
                    Toast.makeText(this@ActivityAgregarLibro, "Error al cargar las categorías", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                Toast.makeText(this@ActivityAgregarLibro, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Método para manejar la selección de la imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            ivPortada.setImageURI(imageUri)
        }
    }

    // Método para subir el libro
    private fun uploadLibro(titulo: String, autor: String, imageUri: Uri, categoriaId: Int) {
        val isbn = etIsbn.text.toString()
        val tipo = etTipo.text.toString()
        val areaDeConocimiento = etAreaDeConocimiento.text.toString()
        val numeroEjemplares = etNumeroEjemplares.text.toString().toInt()
        val codigoUbicacionFisica = etCodigoUbicacionFisica.text.toString()
        val verificacion = etVerificacion.text.toString()
        val editorial = etEditorial.text.toString()
        val ano = etAno.text.toString().toInt()
        val edicion = etEdicion.text.toString()

        // Crear el objeto DTO con todos los campos, incluyendo el categoriaId
        val libroDto = CreateLibroDto(
            isbn, tipo, titulo, areaDeConocimiento, autor, numeroEjemplares,
            codigoUbicacionFisica, verificacion, editorial, ano, edicion,
            emptyList(), categoriaId
        )

        val isbnBody = RequestBody.create("text/plain".toMediaTypeOrNull(), libroDto.isbn)
        val tipoBody = RequestBody.create("text/plain".toMediaTypeOrNull(), libroDto.tipo)
        val tituloBody = RequestBody.create("text/plain".toMediaTypeOrNull(), libroDto.titulo)
        val areaDeConocimientoBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), libroDto.areaDeConocimiento)
        val autorBody = RequestBody.create("text/plain".toMediaTypeOrNull(), libroDto.autor)
        val numeroEjemplaresBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            libroDto.numeroEjemplares.toString()
        )
        val codigoUbicacionFisicaBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), libroDto.codigoUbicacionFisica)
        val verificacionBody = verificacion.takeIf { it.isNotEmpty() }
            ?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val editorialBody = RequestBody.create("text/plain".toMediaTypeOrNull(), libroDto.editorial)
        val anoBody = RequestBody.create("text/plain".toMediaTypeOrNull(), libroDto.ano.toString())
        val edicionBody = RequestBody.create("text/plain".toMediaTypeOrNull(), libroDto.edicion)
        val categoriaIdBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), libroDto.categoriaId.toString())
        val imagenes = emptyList<MultipartBody.Part>()  // Ajusta esto según tus necesidades

        val stream = contentResolver.openInputStream(imageUri)
        val requestFile = stream?.readBytes()?.toRequestBody("image/*".toMediaTypeOrNull())
        val imagePart =
            requestFile?.let { MultipartBody.Part.createFormData("image", "image.jpg", it) }

        imagePart?.let {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = RetrofitClient.apiService.createLibro(
                        isbnBody,
                        tipoBody,
                        tituloBody,
                        areaDeConocimientoBody,
                        autorBody,
                        numeroEjemplaresBody,
                        codigoUbicacionFisicaBody,
                        verificacionBody,
                        editorialBody,
                        anoBody,
                        edicionBody,
                        imagenes,
                        categoriaIdBody,
                        it
                    )
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ActivityAgregarLibro, "Libro creado con éxito", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@ActivityAgregarLibro,
                                "Error al crear el libro",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ActivityAgregarLibro,
                            "Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
