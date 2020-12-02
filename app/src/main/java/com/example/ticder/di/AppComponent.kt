package com.example.ticder.di

import com.example.ticder.MainActivity
import com.example.ticder.viewmodel.ImageViewModel
import dagger.Component

@Component(modules = [AppModule::class, ActivityModule::class])
interface AppComponent {
    fun inject(imageViewModel: ImageViewModel)
    fun inject(activity: MainActivity)
}