package com.example.jetpack1
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyCustomView(context: Context, attributeSet: AttributeSet) : View(context,attributeSet) {

    private var paint: Paint?= null
    private var porterDuffMode = PorterDuff.Mode.CLEAR
    var mode = MyFingerView.Pen
    private var mX = 0f
    private var mY = 0f

    private var path = Path()
    private var erasePath = Path()

    init {
        paint = Paint()
        paint!!.strokeWidth = 40F
        paint!!.style = Paint.Style.STROKE
    }

    fun setPorterDuffMode(porterDuffMode: PorterDuff.Mode) {
        this.porterDuffMode = porterDuffMode
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawColor(Color.GREEN)
        val layId = canvas!!.saveLayer(null,null)
        paint!!.color = Color.RED
        canvas!!.drawPath(path, paint!!)
        paint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        paint!!.color = Color.BLUE
        canvas.drawPath(erasePath, paint!!)
        paint!!.xfermode = null
        canvas!!.restoreToCount(layId)
    }

    private fun touchStart(x: Float, y: Float) {
//        mPath.reset()
        if(mode == Pen){
            path.moveTo(x, y)
        }else{
            erasePath.moveTo(x,y)
        }
        mX = x
        mY = y
//        drawPath()
    }

    private fun touchMove(x: Float, y: Float) {
        if(mode == Pen){
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
        }else{
            erasePath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
        }
        mX = x
        mY = y
//        drawPath()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
//                touchUp()
//                invalidate()
            }
        }
        return true
    }

    companion object {
        var Pen = 1
        var Eraser = 2
    }

}