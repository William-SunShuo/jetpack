package com.example.view
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.EdgeEffect
import android.widget.LinearLayout
import android.widget.OverScroller
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import kotlin.math.abs
import kotlin.math.max

class MyLinearlayout: LinearLayout {

    var mActivePointerId = MotionEvent.INVALID_POINTER_ID
    var mSecondaryPointerId = MotionEvent.INVALID_POINTER_ID
    var mIsBeingDragged = false

    private val scroller: OverScroller = OverScroller(context)
    private val mVelocityTracker: VelocityTracker = VelocityTracker.obtain()
    private var mTouchSlop: Int = 0
    private var mMinimumVelocity: Int = 0
    private var mMaximumVelocity: Int = 0
    private var mOverscrollDistance: Int = 0
    private var mOverflingDistance: Int = 0
    private var mVerticalScrollFactor: Int = 0
    private val mEdgeEffectBottom = EdgeEffect(context)
    private val mEdgeEffectTop = EdgeEffect(context)


    var lastY: Int = 0

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)

    constructor(context: Context) : super(context, null)

    init {
        setWillNotDraw(false)
        val configuration = ViewConfiguration.get(context)
        mTouchSlop = configuration.scaledTouchSlop
        mMinimumVelocity = configuration.scaledMinimumFlingVelocity
        mMaximumVelocity = configuration.scaledMaximumFlingVelocity
        mOverscrollDistance = configuration.scaledOverscrollDistance
        mOverflingDistance = configuration.scaledOverflingDistance
        mOverscrollDistance = 25
        overScrollMode = OVER_SCROLL_ALWAYS
        mEdgeEffectTop.color = Color.RED
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i(Constant.TAG, "onMeasure")
        Log.i(Constant.TAG, "measuredHeight:${measuredHeight}")
        forEachIndexed { index, view ->
            Log.i(Constant.TAG, "child $index: ${view.measuredHeight}")
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        Log.i(Constant.TAG, "onLayout")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i(Constant.TAG, "onSizeChanged")
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        Log.i(Constant.TAG, "dispatchDraw")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i(Constant.TAG, "onDraw")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        Log.i(Constant.TAG, "onFinishInflate")
        forEachIndexed { index, view ->
            Log.i(Constant.TAG, "child $index ,childId: ${view.id}")
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        Log.i(Constant.TAG, "onVisibilityChanged")
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        Log.i(Constant.TAG, "onWindowVisibilityChanged")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.i(Constant.TAG, "onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mVelocityTracker.recycle()
        Log.i(Constant.TAG, "onDetachedFromWindow")
    }

    override fun canScrollVertically(direction: Int): Boolean {
        val canScrollVertically = super.canScrollVertically(direction)
        Log.i(Constant.TAG, "canScrollVertically:$canScrollVertically")
        Log.i(Constant.TAG, "computeVerticalScrollExtent:${super.computeVerticalScrollExtent()}")
        return canScrollVertically
    }

    public override fun computeVerticalScrollOffset(): Int {
        val offset = super.computeVerticalScrollOffset()
        Log.i(Constant.TAG, "computeVerticalScrollOffset:${offset}")
        return offset
    }

    public override fun computeVerticalScrollRange(): Int {
        var range = 0
        forEach { view ->
            range += view.height
        }
        Log.i(Constant.TAG, "computeVerticalScrollRange:${range}")
        return range
    }

    public override fun computeVerticalScrollExtent(): Int {
        return super.computeVerticalScrollExtent()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.also {
            mVelocityTracker.addMovement(event)
            when (it.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    Log.i(Constant.TOUCH, "ACTION_DOWN,ActionIndex:${it.actionIndex}")
                    lastY = event.y.toInt()
                    mActivePointerId = it.getPointerId(it.actionIndex)
                    if (!scroller.isFinished) {
                        scroller.abortAnimation()
                    }
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    Log.i(Constant.TOUCH, "ACTION_POINTER_DOWN,ActionIndex:${it.actionIndex}")
                    mSecondaryPointerId = it.getPointerId(it.actionIndex)
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.i(Constant.TOUCH, "ACTION_MOVE")
                    val y = event.y.toInt()
                    var deltaY = lastY - y
                    if (!mIsBeingDragged && abs(deltaY) > mTouchSlop) {
                        mIsBeingDragged = true
                        // 减少滑动的距离
                        if (deltaY > 0) {
                            deltaY -= mTouchSlop
                        } else {
                            deltaY += mTouchSlop
                        }
                    }
                    if (mIsBeingDragged) {
        //                      scrollBy(0, deltaY)
                        overScrollBy(0, deltaY, 0, scrollY, 0, computeVerticalScrollRange() - computeVerticalScrollExtent(), 0, mOverscrollDistance, true)
                        lastY = y

                        //EdgeEffect
                        val pulledToY = (scrollY + deltaY)
                        if (pulledToY < 0) {
                            Log.e("TEST", "pulledTOY top" + height + "deltaY" + deltaY)
                            mEdgeEffectTop.onPull(
                                (deltaY / height).toFloat(),
                                event.getX(mActivePointerId) / width
                            )
                            if (!mEdgeEffectBottom.isFinished) {
                                mEdgeEffectBottom.onRelease()
                            }
                        } else if (pulledToY > computeVerticalScrollRange() - computeVerticalScrollExtent()) {
                            Log.e("TEST", "pulledTOY top" + height + "deltaY" + deltaY)
                            mEdgeEffectBottom.onPull(
                                (deltaY / height).toFloat(),
                                1.0f - event.getX(mActivePointerId) / width
                            )
                            if (!mEdgeEffectTop.isFinished) {
                                mEdgeEffectTop.onRelease()
                            }
                        } else {
                        }
                        if (!mEdgeEffectTop.isFinished || !mEdgeEffectBottom.isFinished) {
                            postInvalidate()
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    Log.i(Constant.TOUCH, "ACTION_UP,ActionIndex:${it.actionIndex}")
                    mVelocityTracker.computeCurrentVelocity(1000)
                    val yVelocity = mVelocityTracker.yVelocity
                    smoothScrollBy(yVelocity.toInt())
                    mVelocityTracker.clear()
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    Log.i(Constant.TOUCH, "ACTION_POINTER_UP,ActionIndex:${it.actionIndex}")
                    onSecondaryPointerUp(it)
                }
            }
        }
        return true
    }

    private fun onSecondaryPointerUp(ev: MotionEvent) {
        val pointerIndex = ev.actionIndex
        val pointerId = ev.getPointerId(pointerIndex)
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            // TODO: Make this decision more intelligent.
            val newPointerIndex = if (pointerIndex == 0) 1 else 0
            lastY = ev.getY(newPointerIndex).toInt()
            mActivePointerId = ev.getPointerId(newPointerIndex)
//            if (mVelocityTracker != null) {
//                mVelocityTracker.clear()
//            }
        }
    }

    private fun smoothScrollBy(speed: Int) {
        //  scroller.startScroll(0, scrollY, 0, -dy, 500)
        scroller.fling(0, scrollY, 0, -speed, 0, 0, 0, computeVerticalScrollRange() - computeVerticalScrollExtent()
        )
        invalidate()
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {

            // This is called at drawing time by ViewGroup.  We don't want to
            // re-show the scrollbars at this point, which scrollTo will do,
            // so we replicate most of scrollTo here.
            //
            //         It's a little odd to call onScrollChanged from inside the drawing.
            //
            //         It is, except when you remember that computeScroll() is used to
            //         animate scrolling. So unless we want to defer the onScrollChanged()
            //         until the end of the animated scrolling, we don't really have a
            //         choice here.
            //
            //         I agree.  The alternative, which I think would be worse, is to post
            //         something and tell the subclasses later.  This is bad because there
            //         will be a window where mScrollX/Y is different from what the app
            //         thinks it is.
            //
//            scrollTo(scroller.currX, scroller.currY)
            overScrollBy(0, scroller.currY - scrollY, 0, scrollY, 0, computeVerticalScrollRange() - computeVerticalScrollExtent(), 0, mOverscrollDistance, false)
            postInvalidate()
        }
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        if (!scroller.isFinished) {
            val oldX = getScrollX()
            val oldY = getScrollY()
            scrollTo(scrollX, scrollY)
            onScrollChanged(scrollX, scrollY, oldX, oldY)
            if (clampedY) {
                scroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, computeVerticalScrollRange())
            }
        } else {
            super.scrollTo(scrollX, scrollY)// TouchEvent中的overScrollBy调用
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val scrollY = scrollY
        if (!mEdgeEffectTop.isFinished) {
            val count = canvas.save()
            val width = width - paddingLeft - paddingRight
            canvas.translate(paddingLeft.toFloat(), Math.min(0, scrollY).toFloat())
            mEdgeEffectTop.setSize(width, height)
            if (mEdgeEffectTop.draw(canvas)) {
                postInvalidate()
            }
            canvas.restoreToCount(count)
        }
        if (!mEdgeEffectBottom.isFinished) {
            val count = canvas.save()
            val width = width - paddingLeft - paddingRight
            canvas.translate(
                (-width + paddingLeft).toFloat(),
                (max(
                    computeVerticalScrollRange() - computeVerticalScrollExtent(),
                    scrollY
                ) + height).toFloat()
            )
            canvas.rotate(180f, width.toFloat(), 0f)
            mEdgeEffectBottom.setSize(width, height)
            if (mEdgeEffectBottom.draw(canvas)) {
                postInvalidate()
            }
            canvas.restoreToCount(count)
        }
    }


}