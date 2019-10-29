package com.example.baselib.ui.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.Nullable
import com.example.baselib.R
import com.example.baselib.ui.activity.BaseSwipeBackActivity
import com.example.baselib.widget.SwipeBackLayout
import com.example.baselib.viewmodel.BaseViewModel


/**
 * @route:
 * @descript: Fragment 基类 绑定SwipeBackLayout实现可滑动关闭当前Fragment
 * @create: chenjiayou
 * create at 2018/11/21 9:38
 */
abstract class BaseSwipeBackFragment<VM : BaseViewModel> : BaseFragment<VM>() {
    private var swipeBackLayout: SwipeBackLayout? = null
    private var mNoAnim: Animation? = null
    var mLocking = false
    private lateinit var _mActivity: Activity

    private val windowBackground: Int
        get() {
            val a = activity!!.theme.obtainStyledAttributes(intArrayOf(android.R.attr.windowBackground))
            val background = a.getResourceId(0, 0)
            a.recycle()
            return background
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _mActivity = context as Activity
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            val isSupportHidden = savedInstanceState.getBoolean(SWIPE_BACK_FRAGMENT_STATE_SAVE_IS_HIDDEN)

            val ft = fragmentManager!!.beginTransaction()
            if (isSupportHidden) {
                ft.hide(this)
            } else {
                ft.show(this)
            }
            ft.commit()
        }

        mNoAnim = AnimationUtils.loadAnimation(activity, R.anim.no_anim)
        onFragmentCreate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SWIPE_BACK_FRAGMENT_STATE_SAVE_IS_HIDDEN, isHidden)
    }

    private fun onFragmentCreate() {
        swipeBackLayout = SwipeBackLayout(activity!!)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        swipeBackLayout!!.layoutParams = params
        swipeBackLayout!!.setBackgroundColor(Color.TRANSPARENT)
    }

    protected fun attachToSwipeBack(view: View): View {
        swipeBackLayout!!.attachToFragment(this, view)
        return swipeBackLayout as SwipeBackLayout
    }

    protected fun attachToSwipeBack(view: View, edgeLevel: SwipeBackLayout.EdgeLevel): View {
        swipeBackLayout!!.attachToFragment(this, view)
        swipeBackLayout!!.edge = edgeLevel
        return swipeBackLayout as SwipeBackLayout
    }

    protected fun setEdgeLevel(edgeLevel: SwipeBackLayout.EdgeLevel) {
        swipeBackLayout!!.edge = edgeLevel
    }

    protected fun setEdgeLevel(widthPixel: Int) {
        swipeBackLayout!!.setEdgeLevel(widthPixel)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden && swipeBackLayout != null) {
            swipeBackLayout!!.hiddenFragment()
        }
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val view = view
        initFragmentBackground(view)
        if (view != null) {
            view.isClickable = true
        }
    }

    private fun initFragmentBackground(view: View?) {
        if (view is SwipeBackLayout) {
            val childView = (view).getChildAt(0)
            setBackground(childView)
        } else {
            setBackground(view)
        }
    }

    private fun setBackground(view: View?) {
        if (view != null && view.background == null) {
            var defaultBg = 0
            if (_mActivity is BaseSwipeBackActivity<*>) {
                defaultBg = (_mActivity as BaseSwipeBackActivity<*>).getDefaultFragmentBackground()
            }
            if (defaultBg == 0) {
                val background = windowBackground
                view.setBackgroundResource(background)
            } else {
                view.setBackgroundResource(defaultBg)
            }
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (mLocking) {
            mNoAnim
        } else super.onCreateAnimation(transit, enter, nextAnim)
    }

    fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout!!.setEnableGesture(enable)
    }

    companion object {
        private const val SWIPE_BACK_FRAGMENT_STATE_SAVE_IS_HIDDEN = "SWIPE_BACK_FRAGMENT_STATE_SAVE_IS_HIDDEN"
    }
}