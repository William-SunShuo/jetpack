package com.example.navigation.drawable
import android.graphics.*
import android.graphics.drawable.Drawable

class BallDrawable : Drawable() {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#D2691E")
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = Color.BLACK
    }

    override fun draw(canvas: Canvas) {
        val radius = bounds.width().toFloat() / 2
        canvas.drawCircle(
            bounds.width().toFloat() / 2,
            bounds.height().toFloat() / 2,
            radius,
            paint
        )

        //the vertical line of the ball
        canvas.drawLine(
            bounds.width().toFloat() / 2,
            0f,
            bounds.width().toFloat() / 2,
            bounds.height().toFloat(),
            linePaint
        )
        //the transverse line of the ball
        canvas.drawLine(
            0f,
            bounds.height().toFloat() / 2,
            bounds.width().toFloat(),
            bounds.height().toFloat() / 2,
            linePaint
        )

        val path = Path()
        val sinValue = kotlin.math.sin(Math.toRadians(45.0)).toFloat()
        //left curve
        path.moveTo(radius - sinValue * radius,
            radius - sinValue * radius
        )
        path.cubicTo(radius - sinValue * radius,
            radius - sinValue * radius,
            radius,
            radius,
            radius - sinValue * radius,
            radius + sinValue * radius
        )
        //right curve
        path.moveTo(radius + sinValue * radius,
            radius - sinValue * radius
        )
        path.cubicTo(radius + sinValue * radius,
            radius - sinValue * radius,
            radius,
            radius,
            radius + sinValue * radius,
            radius + sinValue * radius
        )
        canvas.drawPath(path, linePaint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return when (paint.alpha) {
            0xff -> PixelFormat.OPAQUE
            0x00 -> PixelFormat.TRANSPARENT
            else -> PixelFormat.TRANSLUCENT
        }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}
