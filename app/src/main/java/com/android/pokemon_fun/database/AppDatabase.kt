package com.android.pokemon_fun.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.pokemon_fun.R
import com.android.pokemon_fun.database.dao.PokemonDao

@Database(version = 1, exportSchema = true, entities = [PokemonEntity::class])
abstract class AppDatabase: RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {

        /**
         * Экземпляр базы данных
         */
        @Volatile
        private var instance: AppDatabase? = null

        /**
         * Метод для получения экземпляра базы данных
         * @param context - контекст
         */
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        /**
         * Метод для создания экземпляра базы данных
         * @param context - контекст приложения
         */
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, createAppDatabaseName(context))
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() // Опционально
                .build()
        }

        private fun createAppDatabaseName(context: Context): String =
            "${context.getString(R.string.app_name)}_database.db"
    }
}