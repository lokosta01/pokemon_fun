package com.android.pokemon_fun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.pokemon_fun.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(applicationContext)
                modules(appModule)
            }
        }
        setContentView(R.layout.activity_main)

    }
}