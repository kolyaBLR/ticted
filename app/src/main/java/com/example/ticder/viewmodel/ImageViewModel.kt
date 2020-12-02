package com.example.ticder.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ticder.model.Data
import kotlinx.coroutines.*

class ImageViewModel(private val data: Data) : ViewModel(), IImageViewModel {

    private val job = Job()
    private val coroutinesScope = CoroutineScope(Dispatchers.Main + job)
    private val bitmapsLiveData = MutableLiveData<HashMap<Int, Bitmap>>()

    init {
        bitmapsLiveData.value = HashMap()
    }

    override fun decodeBitmapIfNeed(
        context: Context,
        position: Int
    ) {
        coroutinesScope.launch {
            val drawable = ContextCompat.getDrawable(context, data.imageResources[position])!!.let {
                DrawableCompat.wrap(it).mutate()
            }
            val bitmap = coroutinesScope.async(Dispatchers.IO) {
                val bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                bitmap
                //BitmapFactory.decodeResource(context.resources, data.imageResources[position])
            }.await()
            bitmapsLiveData.value?.put(position, bitmap)
            bitmapsLiveData.value = bitmapsLiveData.value
        }
    }

    override fun getItemCount() = data.imageResources.size

    override fun getImageLiveData(position: Int): MutableLiveData<HashMap<Int, Bitmap>> {
        return bitmapsLiveData
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}