package com.example.ticder.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ticder.R
import com.example.ticder.model.Data
import com.example.ticder.viewmodel.IImageViewModel
import com.example.ticder.viewmodel.ImageViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideViewModel(provider: ImageViewModelProvider): IImageViewModel {
        return ViewModelProvider(activity, provider).get(
            ImageViewModel::class.java
        )
    }

    @Provides
    fun provideImagesViewModelProvider(data: Data): ImageViewModelProvider {
        return ImageViewModelProvider(data)
    }

    @Provides
    fun provideImages(): Data {
        return Data(
            listOf(
                R.drawable.ic_baseline_access_time_24,
                R.drawable.ic_baseline_access_time_24
            )
        )
    }

    class ImageViewModelProvider(private val data: Data) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ImageViewModel(data) as T
        }
    }
}