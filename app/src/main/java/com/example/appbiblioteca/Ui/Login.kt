package com.example.appbiblioteca.Ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.example.appbiblioteca.R
import com.example.appbiblioteca.Ui.Home.Home
import com.example.appbiblioteca.data.api.AuthService
import com.example.appbiblioteca.data.models.LoginBibliotecarioRequest
import com.example.appbiblioteca.data.models.TokenResponse

import com.auth0.android.jwt.JWT
import com.example.appbiblioteca.utils.RetrofitClient1

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("AuthPreferences", Context.MODE_PRIVATE)

        // Verificar si el token ya está guardado
        val token = sharedPreferences.getString("authToken", null)
        if (!token.isNullOrEmpty()) {
            // Si hay un token guardado, decodificarlo y redirigir según el rol
            val jwt = JWT(token) // Ahora el token es no nulo
            val roles = jwt.getClaim("roles").asList(String::class.java)

            if (roles.contains("bibliotecario")) {
                // Si el rol "bibliotecario" está en el array de roles, redirigir a la pantalla de inicio
                val intent = Intent(this@Login, Home::class.java)
                startActivity(intent)
                finish()
            } else {
                // Si el rol no es bibliotecario, mostrar un mensaje o redirigir a otro lugar
                mostrarErrorDialogo("Rol no autorizado.")
            }
        }

        val nombreBibliotecarioEditText = findViewById<EditText>(R.id.loginEmail)
        val passwordEditText = findViewById<EditText>(R.id.loginPass)
        val loginButton = findViewById<Button>(R.id.loginBtn)

        loginButton.setOnClickListener {
            val nombreBibliotecario = nombreBibliotecarioEditText.text.toString()
            val password = passwordEditText.text.toString()

            val request = LoginBibliotecarioRequest(nombreBibliotecario, password)
            val authService = RetrofitClient1.getClient().create(AuthService::class.java)
            val call = authService.loginBibliotecario(request)

            call.enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val tokenResponse = response.body()
                        val token = tokenResponse?.token

                        if (!token.isNullOrEmpty()) {
                            // Guardar el token de autenticación en SharedPreferences
                            sharedPreferences.edit().putString("authToken", token).apply()

                            // Decodificar el token para obtener el rol
                            val jwt = JWT(token)
                            val roles = jwt.getClaim("roles").asList(String::class.java)

                            // Verificar si el rol "bibliotecario" está en el array de roles
                            if (roles.contains("bibliotecario")) {
                                // Si el rol es bibliotecario, redirigir a la pantalla de inicio
                                val intent = Intent(this@Login, Home::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // Si el rol no es bibliotecario, mostrar un mensaje de error
                                mostrarErrorDialogo("Rol no autorizado.")
                            }
                        } else {
                            mostrarErrorDialogo("Token no válido.")
                        }
                    } else {
                        // Mostrar mensaje de error del backend
                        val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                        mostrarErrorDialogo(errorMessage)
                    }
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    // Manejar la falla en la solicitud
                    Toast.makeText(this@Login, "Error de red: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    fun mostrarErrorDialogo(errorMessage: String) {
        MaterialDialog(this).show {
            title(text = "Error")
            message(text = errorMessage)
            positiveButton(text = "OK")
            onDismiss {
                // Acción cuando se cierra el diálogo
            }
        }
    }
}
