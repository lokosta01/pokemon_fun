package com.android.pokemon_fun.models

import com.google.gson.annotations.SerializedName

data class PokemonResponseData(
    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String,

    @SerializedName("previous")
    val previous: String,

    @SerializedName("results")
    val results: List<Results>
) {
    data class Results(
        @SerializedName("name")
        val name: String,

        @SerializedName("url")
        val url: String
    )
}