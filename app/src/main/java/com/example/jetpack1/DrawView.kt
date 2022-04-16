package com.example.jetpack1
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DrawView : View {

    private var paint = Paint()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        paint.apply {
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 2F
            textSize = 40F
        }
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        drawAxis(canvas)
        drawText(canvas)
    }

    fun drawAxis(canvas: Canvas){
        val canvasWidth = canvas.width
        val canvasHeight = canvas.height
        paint.apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 20F
        }

        paint.color = 0xff00ff00.toInt()
        canvas.drawLine(0F,0F, canvasWidth.toFloat(), 0F,paint)

        paint.color = 0xff0000ff.toInt()
        canvas.drawLine(0F,0F, 0F, canvasHeight.toFloat(),paint)

        canvas.translate(canvasWidth/4F,canvasHeight/4F)

        paint.color = 0xff00ff00.toInt()
        canvas.drawLine(0F,0F, canvasWidth.toFloat(), 0F,paint)

        paint.color = 0xff0000ff.toInt()
        canvas.drawLine(0F,0F, 0F, canvasHeight.toFloat(),paint)

        canvas.translate(canvasWidth/4F,canvasHeight/4F)
        canvas.rotate(30F)

        paint.color = 0xff00ff00.toInt()
        canvas.drawLine(0F,0F, canvasWidth.toFloat(), 0F,paint)

        paint.color = 0xff0000ff.toInt()
        canvas.drawLine(0F,0F, 0F, canvasHeight.toFloat(),paint)

    }

    fun drawText(canvas: Canvas){
        val canvasWidth = canvas.width
        val halfCanvasWidth = canvasWidth / 2
        var translateY = 40F
        paint.color = 0xff000000.toInt()
        canvas.translate(0F,translateY)
        canvas.drawText("正常文本绘制",0F,0F,paint)

        translateY += 40F
        paint.color = 0xffff0000.toInt()
//        canvas.save()
        canvas.translate(0F,translateY)
        canvas.drawText("绿色文本绘制",0F,0F,paint)
//        canvas.restore()
    }
}