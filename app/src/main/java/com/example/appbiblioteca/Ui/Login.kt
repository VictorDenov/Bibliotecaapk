package com.example.appbiblioteca.Ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appbiblioteca.R
import com.example.appbiblioteca.Ui.Home.Home

import com.example.appbiblioteca.data.api.AuthService
import com.example.appbiblioteca.data.models.LoginRequest
import com.example.appbiblioteca.response.LoginResponse
import com.example.appbiblioteca.utils.PreferenceHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response






class Login : AppCompatActivity() {

    private val authService: AuthService by lazy {
        AuthService.create() // Crear la instancia del servicio de autenticación
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Verificamos si el usuario ya está logueado, usando SharedPreferences
        val preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val token = preferences.getString("jwt_token", null)
        if (token != null && preferences.getBoolean("logged", false)) {
            // Si el token existe y el usuario está marcado como logueado, redirigimos a Home
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }

        // Configuramos el botón de login
        val loginButton = findViewById<Button>(R.id.loginBtn)
        loginButton.setOnClickListener {
            performLogin()  // Llamamos a la función para realizar el login
        }
    }

    private fun performLogin() {
        val email = findViewById<EditText>(R.id.loginEmail).text.toString()
        val password = findViewById<EditText>(R.id.loginPass).text.toString()

        // Verificar si los campos de correo y contraseña están vacíos
        if (email.isEmpty() || password.isEmpty()) {
            showErrorMessage("Por favor ingrese correo y contraseña.")
            return
        }

        // Creamos el objeto de la solicitud
        val loginRequest = LoginRequest(email, password)

        // Llamamos al servicio de Retrofit para hacer el login
        val call = authService.postLogin(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    token?.let {
                        // Guardamos el token en SharedPreferences
                        saveTokenToPreferences(it)
                        saveUserLoggedInStatus(true)  // Marcamos que el usuario ha iniciado sesión
                        val intent = Intent(this@Login, Home::class.java)
                        startActivity(intent)
                        finish() // Finalizamos la actividad de login
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Fallo el login"
                    handleErrorMessage(errorMessage)  // Manejar el error según el mensaje recibido
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Si ocurre un error en la conexión, mostramos el mensaje correspondiente
                showErrorMessage(t.message ?: "Error desconocido")
            }
        })
    }

    // Función para mostrar mensajes de error en la UI
    private fun showErrorMessage(message: String) {
        val errorTextView = findViewById<TextView>(R.id.textView2)
        errorTextView.text = message
    }

    // Función para manejar el error de login, que puede ser "usuario no existe" o "contraseña incorrecta"
    private fun handleErrorMessage(errorMessage: String) {
        when {
            errorMessage.contains("No existe el usuario", ignoreCase = true) -> {
                showErrorMessage("No existe el usuario")
            }
            errorMessage.contains("Contraseña erronea", ignoreCase = true) -> {
                showErrorMessage("Contraseña incorrecta")
            }
            else -> {
                showErrorMessage("Error desconocido. Intenta nuevamente.")
            }
        }
    }

    // Función para guardar el token en SharedPreferences
    private fun saveTokenToPreferences(token: String) {
        val preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(preferences.edit()) {
            putString("jwt_token", token)  // Guardamos el token
            apply()
        }
    }

    // Función para guardar el estado de sesión del usuario en SharedPreferences
    private fun saveUserLoggedInStatus(isLoggedIn: Boolean) {
        val preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(preferences.edit()) {
            putBoolean("logged", isLoggedIn)  // Guardamos el estado de sesión
            apply()
        }
    }
}
