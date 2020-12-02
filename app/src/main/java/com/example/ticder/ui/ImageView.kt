package com.example.ticder.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.math.min

class ImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val paint = Paint()

    private var scaleBitmap: Bitmap? = null

    var bitmap: Bitmap? = null
        set(value) {
            field = value
            if (width > 0 && height > 0) {
                calculateBitmap(width, height)
            }
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        scaleBitmap?.let { bitmap ->
            canvas.drawBitmap(bitmap, 0f, 0f, paint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateBitmap(w, h)
    }

    private fun calculateBitmap(width: Int, height: Int) {
        bitmap?.let { bitmap ->
            coroutineScope.launch {
                val newBitmap = coroutineScope.async(Dispatchers.IO) {
                    val size = min(width, height)
                    Bitmap.createScaledBitmap(bitmap, size, size, true)
                }.await()
                scaleBitmap = newBitmap
                invalidate()
            }
        }
    }
}