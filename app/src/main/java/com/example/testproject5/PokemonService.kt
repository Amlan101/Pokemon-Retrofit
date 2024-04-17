package com.example.testproject5

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon/{idOrName}/")
    fun getPokemon(@Path("idOrName") idOrName: String): Call<Pokemon>
}