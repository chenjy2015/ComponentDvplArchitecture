package com.example.baselib.utils

import android.content.Context;
import android.util.Pair;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import kotlin.math.ceil


/**
 * Created by chenjy on 2019/4/18.
 */

/**
 * Created by chenjy on 2019/4/18.
 */

class ViewUtils {

    //目标项是否在最后一个可见项之后
    private val mShouldScroll: Boolean = false
    //记录目标项位置
    private val mToPosition: Int = 0

    /**
     * Created by chenjy on 2019/4/12.
     */


    fun closeDefaultAnimator(mRvCustomer: RecyclerView?) {
        if (null == mRvCustomer) {
            return
        }
        mRvCustomer.itemAnimator!!.addDuration = 0
        mRvCustomer.itemAnimator!!.changeDuration = 0
        mRvCustomer.itemAnimator!!.moveDuration = 0
        mRvCustomer.itemAnimator!!.removeDuration = 0
        (mRvCustomer.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    fun registerScrollChangeListener(recyclerView: RecyclerView, onScrollListener: OnScrollListener) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (recyclerView.layoutManager is LinearLayoutManager) {
                        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                        var isTop = false
                        var isBottom = false
                        if (linearLayoutManager!!.orientation == LinearLayoutManager.VERTICAL) {
                            isTop = calcTopV(recyclerView)
                            if (!isTop) {
                                isBottom = calcBottomV(recyclerView)
                            }
                        } else {
                            isTop = calcTopH(recyclerView)
                            if (!isTop) {
                                isBottom = calcBottomH(recyclerView)
                            }
                        }
                        if (isTop) {
                            onScrollListener.onScrollTop()
                        } else if (isBottom) {
                            onScrollListener.onScrollBottom()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //得到第一个view
                val headView = recyclerView.layoutManager!!.getChildAt(0) ?: return
                //通过这个headView得到这个view当前的position值
                val headViewPosition = recyclerView.layoutManager!!.getPosition(headView)

                //得到当前显示的最后一个item的view
                val lastChildView =
                    recyclerView.layoutManager!!.getChildAt(recyclerView.layoutManager!!.childCount - 1) ?: return
                //得到lastChildView的bottom坐标值
                val lastPosition = recyclerView.layoutManager!!.getPosition(lastChildView)

                onScrollListener.onVisibleItemChange(headViewPosition, lastPosition)

            }
        })
    }

    fun calcTopV(recyclerView: RecyclerView): Boolean {
        //得到第一个view
        val headView = recyclerView.layoutManager!!.getChildAt(0) ?: return false
        //通过这个headView得到这个view当前的position值
        val headViewPosition = recyclerView.layoutManager!!.getPosition(headView)
        //得到headView的bottom坐标值
        val headViewBottom = headView.bottom

        //得到recyclerview的第一个坐标减去底部padding值，也就是显示内容最顶部的坐标
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        val firstChildPosition = layoutManager!!.findFirstVisibleItemPosition()
        //这里是判断前提是有加headview
        val firstChildView = layoutManager.getChildAt(firstChildPosition) ?: return false
        val firstChildBottom = firstChildView.bottom

        //如果两个条件都满足则说明是真正的滑动到了顶部
        //判断head是否处于可见状态
        return headViewBottom == firstChildBottom && firstChildPosition == headViewPosition
    }

    fun calcTopH(recyclerView: RecyclerView): Boolean {
        //得到第一个view
        val headView = recyclerView.layoutManager!!.getChildAt(0) ?: return false
        //通过这个headView得到这个view当前的position值
        val headViewPosition = recyclerView.layoutManager!!.getPosition(headView)
        //得到headView的bottom坐标值
        val headViewBottom = headView.left

        //得到recyclerview的第一个坐标减去底部padding值，也就是显示内容最顶部的坐标
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        val firstChildPosition = layoutManager!!.findFirstVisibleItemPosition()
        //这里是判断前提是有加headview
        val firstChildView = layoutManager.getChildAt(firstChildPosition) ?: return false
        val firstChildBottom = firstChildView.left

        //如果两个条件都满足则说明是真正的滑动到了顶部
        //判断head是否处于可见状态
        return headViewBottom == firstChildBottom && firstChildPosition == headViewPosition
    }

    fun calcBottomV(recyclerView: RecyclerView): Boolean {
        //得到当前显示的最后一个item的view
        val lastChildView = recyclerView.layoutManager!!.getChildAt(recyclerView.layoutManager!!.childCount - 1)
            ?: return false
        //得到lastChildView的bottom坐标值
        val lastChildBottom = lastChildView.bottom
        //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
        val recyclerBottom = recyclerView.bottom - recyclerView.paddingBottom
        //通过这个lastChildView得到这个view当前的position值
        val lastPosition = recyclerView.layoutManager!!.getPosition(lastChildView)
        //判断lastChildView的bottom值跟recyclerBottom 判断lastPosition是不是最后一个position 如果两个条件都满足则说明是真正的滑动到了底部
        return lastChildBottom == recyclerBottom && lastPosition == recyclerView.layoutManager!!.itemCount - 1
    }

    fun calcBottomH(recyclerView: RecyclerView): Boolean {
        //得到当前显示的最后一个item的view
        val lastChildView = recyclerView.layoutManager!!.getChildAt(recyclerView.layoutManager!!.childCount - 1)
            ?: return false
        //得到lastChildView的bottom坐标值
        val lastChildBottom = lastChildView.right
        //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
        val recyclerBottom = recyclerView.right - recyclerView.paddingRight
        //通过这个lastChildView得到这个view当前的position值
        val lastPosition = recyclerView.layoutManager!!.getPosition(lastChildView)
        //判断lastChildView的bottom值跟recyclerBottom 判断lastPosition是不是最后一个position 如果两个条件都满足则说明是真正的滑动到了底部
        return lastChildBottom == recyclerBottom && lastPosition == recyclerView.layoutManager!!.itemCount - 1
    }


    fun getVisibleIndexs(recyclerView: RecyclerView, onScrollListener: OnScrollListener) {
        //得到当前显示的最后一个item的view
        val lastChildView = recyclerView.layoutManager!!.getChildAt(recyclerView.layoutManager!!.childCount - 1)
            ?: return
        //得到第一个view
        val headView = recyclerView.layoutManager!!.getChildAt(0) ?: return
        //通过这个headView得到这个view当前的position值
        val headViewPosition = recyclerView.layoutManager!!.getPosition(headView)
        //得到lastChildView的bottom坐标值
        val lastChildBottom = lastChildView.bottom
        //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
        val recyclerBottom = recyclerView.bottom - recyclerView.paddingBottom
        //通过这个lastChildView得到这个view当前的position值
        val lastPosition = recyclerView.layoutManager!!.getPosition(lastChildView)

        onScrollListener.onVisibleItemChange(headViewPosition, lastPosition)
    }


    /**
     * 获取当前recyclerview 一页可加载item的数量
     *
     * @param view
     */
    fun getVisibleCount(view: View, orientation: Int): Int {
        var count = 0
        val pair = onViewMeasure(view)
        val pixels = getDisplayMetrics(view.context)
        val width = pair.first
        val height = pair.second
        val measure = if (LinearLayoutManager.VERTICAL == orientation) height else width
        //四舍五入取整数
        count = ceil((pixels[1] / measure).toDouble()).toInt()
        return count
    }

    fun getDisplayMetrics(context: Context): IntArray {
        val resources = context.resources
        val dm = resources.displayMetrics
        val density1 = dm.density
        val width = dm.widthPixels
        val height = dm.heightPixels
        return intArrayOf(width, height)
    }

    /**
     * 滑动到指定位置
     */
    fun smoothMoveToPosition(mRecyclerView: RecyclerView, position: Int) {
        mRecyclerView.scrollToPosition(position)
    }


    interface OnScrollListener {
        fun onScrollBottom()

        fun onScrollTop()

        fun onVisibleItemChange(start: Int, end: Int)
    }

    abstract class SimpleScrollListener : OnScrollListener {
        override fun onScrollBottom() {

        }

        override fun onScrollTop() {

        }

        override fun onVisibleItemChange(start: Int, end: Int) {

        }
    }

    companion object {


        fun onViewMeasure(view: View): Pair<Int, Int> {
            val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view.measure(w, h)
            val height = view.measuredHeight
            val width = view.measuredWidth
            return Pair(width, height)
        }
    }

}