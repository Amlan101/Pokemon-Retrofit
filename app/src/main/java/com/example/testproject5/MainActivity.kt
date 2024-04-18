package com.example.testproject5

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private var showHeight: Boolean = false
    private lateinit var searchView: SearchView
    val pokemonList = mutableListOf<Pokemon>()

    private fun fetchPokemonData() {
        val service = RetrofitClient.pokemonService

        for (i in 1..50) {
            service.getPokemon(i.toString()).enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    pokemon?.let {
                        pokemonList.add(it)
                    }
                    if (pokemonList.size == 50) {
                        adapter.updatePokemonList(pokemonList)
                    }
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to fetch Pokémon data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter(emptyList(), showHeight)
        recyclerView.adapter = adapter

        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                if (query != null && query.isNotEmpty()) {
                    val filteredList = pokemonList.filter { it.name.contains(query, true) }
                    adapter.updatePokemonList(filteredList)
                } else {
                    adapter.updatePokemonList(pokemonList)
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = pokemonList.filter { it.name.contains(newText.orEmpty(), true) }
                adapter.updatePokemonList(filteredList)
                return true
            }
        })

        val heightSwitch: SwitchCompat = findViewById(R.id.switch1)
        heightSwitch.setOnCheckedChangeListener { _, isChecked ->
            showHeight = isChecked
            adapter.setShowHeight(showHeight)
        }
        fetchPokemonData()

    }
}