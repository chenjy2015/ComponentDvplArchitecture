package com.example.baselib.ui.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import com.example.baselib.widget.SwipeBackLayout
import com.example.baselib.viewmodel.BaseViewModel


/**
 * @route:
 * @descript: Activity 基类 绑定SwipeBackLayout实现可滑动关闭当前Activity
 * @create: chenjiayou
 * create at 2018/11/21 9:38
 */
abstract class BaseSwipeBackActivity<VM : BaseViewModel> : BaseActivity<VM>() {
    private var swipeBackLayout: SwipeBackLayout? = null
    /**
     * 当Fragment根布局 没有 设定background属性时,
     * 库默认使用Theme的android:windowbackground作为Fragment的背景,
     * 如果不像使用windowbackground作为背景, 可以通过该方法改变Fragment背景。
     */
    private var mDefaultFragmentBackground = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onActivityCreate()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        swipeBackLayout!!.attachToActivity(this)
    }

    private fun onActivityCreate() {
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.decorView.setBackgroundDrawable(null)
        swipeBackLayout = SwipeBackLayout(this)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        swipeBackLayout!!.layoutParams = params
    }

     fun setEdgeLevel(edgeLevel: SwipeBackLayout.EdgeLevel) {
        swipeBackLayout!!.setEdgeLevel(edgeLevel)
    }

     fun setEdgeLevel(widthPixel: Int) {
        swipeBackLayout!!.setEdgeLevel(widthPixel)
    }

     fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout!!.setEnableGesture(enable)
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity可以滑动退出, 并且总是优先; false: Activity不允许滑动退出
     */
    fun swipeBackPriority(): Boolean {
        return supportFragmentManager.backStackEntryCount <= 1
    }

    /**
     * 当Fragment根布局 没有 设定background属性时,
     * 库默认使用Theme的android:windowbackground作为Fragment的背景,
     * 如果不像使用windowbackground作为背景, 可以通过该方法改变Fragment背景。
     */
     fun setDefaultFragmentBackground(@DrawableRes backgroundRes: Int) {
        mDefaultFragmentBackground = backgroundRes
    }

    fun getDefaultFragmentBackground(): Int {
        return mDefaultFragmentBackground
    }
}
