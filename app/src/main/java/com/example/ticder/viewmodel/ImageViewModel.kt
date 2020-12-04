package com.example.ticder.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ticder.model.Data
import com.example.ticder.model.SwipeInfo
import kotlinx.coroutines.*

class ImageViewModel(data: Data) : ViewModel(), IImageViewModel {

    private val originalImages = ArrayList(data.imageResources)
    private val likedImages = ArrayList<Int>()
    private val activeItems = ArrayList(originalImages)

    private val job = Job()
    private val coroutinesScope = CoroutineScope(Dispatchers.Main + job)
    private val bitmapsLiveData = MutableLiveData<HashMap<Int, Bitmap>>()
    private val swipeLiveData = MutableLiveData<SwipeInfo>()
    private val completedLiveData = MutableLiveData<ArrayList<Int>>()

    init {
        bitmapsLiveData.value = HashMap()
    }

    override fun decodeBitmapIfNeed(context: Context, position: Int) {
        coroutinesScope.launch {
            val bitmap = coroutinesScope.async(Dispatchers.IO) {
                BitmapFactory.decodeResource(context.resources, activeItems[position])
                    ?: kotlin.run {
                        val res = activeItems[position]
                        val drawable = ContextCompat.getDrawable(context, res)!!.let {
                            DrawableCompat.wrap(it).mutate()
                        }
                        val bitmap = Bitmap.createBitmap(
                            drawable.intrinsicWidth,
                            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
                        )
                        val canvas = Canvas(bitmap)
                        drawable.setBounds(0, 0, canvas.width, canvas.height)
                        drawable.draw(canvas)
                        bitmap
                    }
            }.await()
            bitmapsLiveData.value?.put(position, bitmap)
            bitmapsLiveData.value = bitmapsLiveData.value
        }
    }

    override fun getItemCount() = activeItems.size

    override fun onSwipeItem(swipeInfo: SwipeInfo) {
        if (swipeInfo.like) {
            likedImages.add(activeItems[swipeInfo.position])
        }
        activeItems.removeAt(swipeInfo.position)
        swipeLiveData.value = swipeInfo
        if (activeItems.isEmpty()) {
            bitmapsLiveData.value?.clear()
            activeItems.addAll(likedImages)
            completedLiveData.value = activeItems
        }
    }

    override fun getImageLiveData(position: Int): LiveData<HashMap<Int, Bitmap>> = bitmapsLiveData

    override fun getSwipeLiveData(): LiveData<SwipeInfo> = swipeLiveData

    override fun getCompletedLiveData(): LiveData<ArrayList<Int>> = completedLiveData

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}