package com.android.pokemon_fun.network

import android.content.Context
import com.android.pokemon_fun.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        private var instance: Api? = null

        fun getInstance(context: Context): Api {
            return instance
                ?: synchronized(this) {
                    instance
                        ?: create(context)
                            .also { instance = it }
                }
        }

        private fun create(context: Context): Api {
            val clientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val bodyLogInterceptor =
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                val headerLogInterceptor =
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }
                val basicLogInterceptor =
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
                clientBuilder.addInterceptor(bodyLogInterceptor)
                    .addInterceptor(headerLogInterceptor).addInterceptor(basicLogInterceptor)
            }
            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(clientBuilder.build())
                .build()
                .create(Api::class.java)
        }
    }
}