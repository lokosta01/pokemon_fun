package com.android.pokemon_fun.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("pokemon?")
    fun getPokemons(@Query("limit") limit: Int = 100,  @Query("offset") offset: Int = 50)

    @GET("https://pokeapi.co/api/v2/pokemon/{id}/")
    fun getDetailsPokemon(@Path("id") idPokemon: Int)
}