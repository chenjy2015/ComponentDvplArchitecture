package com.example.baselib.widget.reside_menu

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup


class TouchDisableView : ViewGroup {

    private var mContent: View? = null
    //	private int mMode;
    private var mTouchDisabled = false

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    fun setContent(v: View) {
        if (mContent != null) {
            this.removeView(mContent)
        }
        mContent = v
        addView(mContent)
    }

    fun getContent(): View? {
        return mContent
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val width = getDefaultSize(0, widthMeasureSpec)
        val height = getDefaultSize(0, heightMeasureSpec)
        setMeasuredDimension(width, height)

        val contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, width)
        val contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, height)
        mContent!!.measure(contentWidth, contentHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = r - l
        val height = b - t
        mContent!!.layout(0, 0, width, height)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mTouchDisabled
    }

    fun setTouchDisable(disableTouch: Boolean) {
        mTouchDisabled = disableTouch
    }

    fun isTouchDisabled(): Boolean {
        return mTouchDisabled
    }
}