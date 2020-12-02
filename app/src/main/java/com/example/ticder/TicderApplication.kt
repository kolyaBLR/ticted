package com.example.ticder

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.ticder.di.ActivityModule
import com.example.ticder.di.AppModule
import com.example.ticder.di.DaggerAppComponent

class TicderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun create(activity: AppCompatActivity) = DaggerAppComponent.builder()
        .activityModule(ActivityModule(activity))
        .appModule(AppModule())
        .build()

    fun create() = DaggerAppComponent.builder()
        .appModule(AppModule())
        .build()

    companion object {
        lateinit var instance: TicderApplication
            private set
    }
}