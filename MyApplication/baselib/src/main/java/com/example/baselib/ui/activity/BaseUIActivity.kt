package com.example.baselib.ui.activity

import com.example.baselib.viewmodel.BaseViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.baselib.arouter.ARouterManager
import com.example.baselib.utils.ViewUtils
import androidx.recyclerview.widget.RecyclerView
import android.view.View


abstract class BaseUIActivity<VM : BaseViewModel, out VD : ViewDataBinding> : BaseSwipeBackActivity<VM>() {

    private lateinit var dataBinding: VD
    private lateinit var viewUtils: ViewUtils

    abstract fun getLayoutId(): Int
    abstract fun init()
    abstract fun initEvent()
    abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this@BaseUIActivity, getLayoutId()) as VD
        ARouterManager.instance.inject(this@BaseUIActivity)
        viewUtils = ViewUtils()
        init()
        initEvent()
        initData()
    }

    fun getDataBinding(): VD {
        return dataBinding
    }

    fun showShortToast(s: String) {
        Toast.makeText(this@BaseUIActivity, s, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(s: String) {
        Toast.makeText(this@BaseUIActivity, s, Toast.LENGTH_LONG).show()
    }

    /**
     * 滑动监听 包含到顶部监听和到底部监听
     *
     * @param recyclerView
     * @param onScrollListener
     */
    fun registerScrollChangeListener(recyclerView: RecyclerView, onScrollListener: ViewUtils.OnScrollListener) {
        viewUtils.registerScrollChangeListener(recyclerView, onScrollListener)
    }

    /**
     * 根据当前recyclerview 获取当前可见item的索引值
     *
     * @param recyclerView
     * @param onScrollListener 可见索引值变化监听
     */
    fun getVisibleIndex(recyclerView: RecyclerView, onScrollListener: ViewUtils.OnScrollListener) {
        viewUtils.getVisibleIndexs(recyclerView, onScrollListener)
    }

    /**
     * 获取当前recyclerview 一页可加载item的数量
     *
     * @param view
     */
    fun getVisibleCount(view: View, orientation: Int): Int {
        return viewUtils.getVisibleCount(view, orientation)
    }
}