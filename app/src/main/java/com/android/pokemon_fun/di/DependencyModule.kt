package com.android.pokemon_fun.di

import com.android.pokemon_fun.database.AppDatabase
import com.android.pokemon_fun.network.ApiClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single {
        ApiClient.getInstance(androidApplication())
        AppDatabase.getInstance(androidApplication())
    }
}