package com.example.view
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class ArcProgressView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attributes, defStyleAttr, defStyleRes) {

    private var angle = 0f

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = Color.RED
        strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        canvas?.drawArc(15f, 15f, width.toFloat() - 15, height.toFloat() - 15, 180f, 180f, false, paint)
        paint.color = Color.BLACK
        paint.strokeWidth = 20f
        canvas?.drawArc(
            15f,
            15f,
            width.toFloat() - 15f,
            height.toFloat() - 15f,
            180f,
            angle,
            false,
            paint
        )
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(
            (width / 2 - (width / 2 - 15) * cos(angle * Math.PI / 180)).toFloat(),
            (width / 2 - (width / 2 - 15) * sin(angle * Math.PI / 180)).toFloat(),
            15f,
            paint
        )
        paint.color = Color.WHITE
        canvas?.drawCircle(
            (width / 2 - (width / 2 - 15) * cos(angle * Math.PI / 180)).toFloat(),
            (width / 2 - (width / 2 - 15) * sin(angle * Math.PI / 180)).toFloat(),
            5f,
            paint
        )
        paint.color = Color.BLACK
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 18f
        canvas?.drawText("afgd", 0, 4, width / 2f, height / 2f, paint)
        val rect = Rect()
        paint.getTextBounds("afgd",0,4,rect)

        paint.color = Color.RED
        paint.strokeWidth = 1f
        paint.style = Paint.Style.STROKE
        canvas?.drawLine(width / 2f - rect.width()/2,height / 2f + paint.fontMetrics.top,width / 2f + rect.width()/2,height / 2f  + paint.fontMetrics.top,paint)
        canvas?.drawLine(width / 2f - rect.width()/2,height / 2f + paint.fontMetrics.ascent,width / 2f + rect.width()/2,height / 2f  + paint.fontMetrics.ascent,paint)
        canvas?.drawLine(width / 2f - rect.width()/2,height / 2f,width / 2f + rect.width()/2,height / 2f,paint)
        paint.color = Color.BLUE
        canvas?.drawLine(width / 2f - rect.width()/2,height / 2f + paint.fontMetrics.descent,width / 2f + rect.width()/2,height / 2f  + paint.fontMetrics.descent,paint)
        paint.color = Color.BLACK
        canvas?.drawLine(width / 2f - rect.width()/2 - 20,height / 2f + paint.fontMetrics.bottom,width / 2f + rect.width()/2 + 20,height / 2f  + paint.fontMetrics.bottom,paint)
//        canvas?.drawText("$angle", 0, angle.toString().length, width / 2f, height / 2f - 40, paint)
        if (angle < 180) {
            postDelayed({
                angle++
                invalidate()
            }, 5L)
        }
    }
}