package com.example.appbiblioteca.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbiblioteca.R
import com.example.appbiblioteca.adaptadores.CategoriaAdapter
import com.example.appbiblioteca.data.Categoria
import com.example.appbiblioteca.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


