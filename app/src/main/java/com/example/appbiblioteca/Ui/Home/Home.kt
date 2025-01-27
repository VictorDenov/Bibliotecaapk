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
import androidx.fragment.app.Fragment

import com.example.appbiblioteca.R
import com.example.appbiblioteca.Ui.Fragments.LibrosFragment
import com.example.appbiblioteca.Ui.Fragments.PrestamosFragment
import com.example.appbiblioteca.Ui.Login
import com.example.appbiblioteca.Ui.ActivityAgregarLibro
import com.example.appbiblioteca.Ui.Fragments.EstanteriaFragment
import com.example.appbiblioteca.Ui.Fragments.PerfilFragment
import com.example.appbiblioteca.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var currentFragment: Fragment? = null // Para evitar reemplazar el mismo fragmento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Reemplazar el fragmento inicial
        if (savedInstanceState == null) {
            replaceFragment(LibrosFragment())
        }

        // Configuración de BottomNavigationView
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.librosbt -> replaceFragment(LibrosFragment())
                R.id.estanteriabt -> replaceFragment(EstanteriaFragment())
                R.id.usuariosbt -> {
                    val intent = Intent(this, ActivityAgregarLibro::class.java)
                    startActivity(intent)
                }
                R.id.estadisticabt -> replaceFragment(PrestamosFragment())
                R.id.perfilbt -> replaceFragment(PerfilFragment())
                else -> {
                    // Puedes manejar otros casos aquí si es necesario
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        // Evitar reemplazar el mismo fragmento
        if (currentFragment == null || currentFragment!!::class.java != fragment::class.java) {
            val transaction = supportFragmentManager.beginTransaction()

            // Agregar animaciones para la transición
            transaction.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )

            // Reemplazar el fragmento
            transaction.replace(R.id.frame_layout, fragment)
            transaction.commit()

            // Actualizar el fragmento actual
            currentFragment = fragment
        }
    }
}
