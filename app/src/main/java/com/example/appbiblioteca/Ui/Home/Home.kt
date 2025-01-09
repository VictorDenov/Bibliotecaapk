package com.example.appbiblioteca.Ui.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.appbiblioteca.R
import com.example.appbiblioteca.Ui.Login


import com.auth0.android.jwt.JWT

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Recuperamos el token de SharedPreferences
        val preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val token = preferences.getString("jwt_token", null)  // Recuperamos el token

        if (token != null) {
            // Si el token está disponible, decodificamos y mostramos el correo
            Log.d("HomeActivity", "Token JWT: $token")
            displayEmailFromToken(token)
        } else {
            Log.d("HomeActivity", "Token no disponible")
            // Si no hay token, redirige al Login
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun displayEmailFromToken(token: String) {
        try {
            val jwt = JWT(token)  // Usamos la librería JWTDecode para decodificar el token
            val email = jwt.getClaim("Email").asString()  // Suponiendo que 'email' está en el payload

            val emailTextView = findViewById<TextView>(R.id.emailTextView)
            emailTextView.text = "Correo: $email"  // Muestra el correo en un TextView

        } catch (e: Exception) {
            Log.e("HomeActivity", "Error al decodificar el token: ${e.message}")
        }
    }
}
