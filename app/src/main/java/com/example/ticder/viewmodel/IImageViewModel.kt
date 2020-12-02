package com.example.ticder.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData

interface IImageViewModel {
    fun decodeBitmapIfNeed(
        context: Context,
        position: Int
    )
    fun getItemCount(): Int
    fun getImageLiveData(position: Int): MutableLiveData<HashMap<Int, Bitmap>>
}