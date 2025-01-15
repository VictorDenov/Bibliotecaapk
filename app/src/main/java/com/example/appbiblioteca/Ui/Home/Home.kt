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
import com.example.appbiblioteca.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(LibrosFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.librosbt -> replaceFragment(LibrosFragment())
                R.id.prestamosbt -> replaceFragment(PrestamosFragment())
                R.id.usuariosbt -> replaceFragment(LibrosFragment())
                R.id.estadisticabt-> replaceFragment(LibrosFragment())
                R.id.perfilbt -> replaceFragment(LibrosFragment())
                else ->{

                }

            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }

}

