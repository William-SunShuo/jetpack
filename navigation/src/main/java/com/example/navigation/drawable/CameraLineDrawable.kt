package com.example.navigation.drawable
import android.graphics.*
import android.graphics.drawable.Drawable

class CameraLineDrawable : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1f
        color = Color.parseColor("#ffffff")
    }

    override fun draw(canvas: Canvas) {
        canvas.drawLine(
            0f,
            bounds.height().toFloat() / 3,
            bounds.width().toFloat(),
            bounds.height().toFloat() / 3,
            paint
        )
        canvas.drawLine(
            0f,
            bounds.height().toFloat() * 2 / 3,
            bounds.width().toFloat(),
            bounds.height().toFloat() * 2 / 3,
            paint
        )
        canvas.drawLine(
            bounds.width().toFloat() / 3,
            0f,
            bounds.width().toFloat() / 3,
            bounds.height().toFloat(),
            paint
        )
        canvas.drawLine(
            bounds.width().toFloat() * 2 / 3,
            0f,
            bounds.width().toFloat() * 2 / 3,
            bounds.height().toFloat(),
            paint
        )
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return when (paint.alpha) {
            0xff -> PixelFormat.OPAQUE
            0x00 -> PixelFormat.TRANSPARENT
            else -> PixelFormat.TRANSLUCENT
        }
    }
}