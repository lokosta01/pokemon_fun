package com.android.pokemon_fun.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.pokemon_fun.database.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categories: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon WHERE pokemon_id = :id")
    fun getPokemonById(id: Long) : PokemonEntity
}