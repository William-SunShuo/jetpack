package com.example.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BackArrowView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attributes, defStyleAttr, defStyleRes) {

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = Color.RED
        strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.translate(400f,400f)
        canvas?.rotate(-45f)
//        canvas?.save()
        canvas?.drawLine(5f,5f,200f,5f,paint)
        canvas?.drawLine(5f,5f,5f,200f,paint)
//        canvas?.drawLine(5f,200f,200f,200f,paint)
//        canvas?.drawLine(200f,5f,200f,200f,paint)
//        canvas?.restore()

    }

}