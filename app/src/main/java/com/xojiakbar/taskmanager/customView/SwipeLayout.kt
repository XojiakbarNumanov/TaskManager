package com.xojiakbar.taskmanager.customView

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper



class SwipeLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) :
    LinearLayout(context, attrs, defStyleAttr) {
    val viewDragHelper: ViewDragHelper
    var contentView: View? = null
    private var actionView: View? = null
    var dragDistance = 0
    private val AUTO_OPEN_SPEED_LIMIT = 800.0
    private var draggedX = 0

    init {
        viewDragHelper = ViewDragHelper.create(this, DragHelperCallback())
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        contentView = getChildAt(0)
        actionView = getChildAt(1)
        actionView?.setVisibility(GONE)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        dragDistance = actionView!!.measuredWidth
    }

    private inner class DragHelperCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(view: View, i: Int): Boolean {
            return view === contentView || view === actionView
        }

        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            draggedX = left
            if (changedView === contentView) {
                actionView!!.offsetLeftAndRight(dx)
            } else {
                contentView!!.offsetLeftAndRight(dx)
            }
            if (actionView!!.visibility == GONE) {
                actionView!!.visibility = VISIBLE
            }
            invalidate()
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return if (child === contentView) {
                val leftBound = paddingLeft
                val minLeftBound = -leftBound - dragDistance
                Math.min(Math.max(minLeftBound, left), 0)
            } else {
                val minLeftBound = paddingLeft + contentView!!.measuredWidth - dragDistance
                val maxLeftBound = paddingLeft + contentView!!.measuredWidth + paddingRight
                Math.min(Math.max(left, minLeftBound), maxLeftBound)
            }
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return dragDistance
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            var settleToOpen = false
            if (xvel > AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = false
            } else if (xvel < -AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = true
            } else if (draggedX <= -dragDistance / 2) {
                settleToOpen = true
            } else if (draggedX > -dragDistance / 2) {
                settleToOpen = false
            }
            val settleDestX = if (settleToOpen) -dragDistance else 0
            viewDragHelper.smoothSlideViewTo(contentView!!, settleDestX, 0)
            ViewCompat.postInvalidateOnAnimation(this@SwipeLayout)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (viewDragHelper.shouldInterceptTouchEvent(ev)) {
            true
        } else super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }
}
