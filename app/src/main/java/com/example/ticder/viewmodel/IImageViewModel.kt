package com.example.ticder.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.example.ticder.model.SwipeInfo

interface IImageViewModel {
    fun decodeBitmapIfNeed(
        context: Context,
        position: Int
    )
    fun getItemCount(): Int
    fun onSwipeItem(swipeInfo: SwipeInfo)
    fun getImageLiveData(position: Int): LiveData<HashMap<Int, Bitmap>>
    fun getSwipeLiveData(): LiveData<SwipeInfo>
    fun getCompletedLiveData(): LiveData<ArrayList<Int>>
}