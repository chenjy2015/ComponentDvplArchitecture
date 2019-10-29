package com.example.baselib.ui.fragment

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.baselib.viewmodel.BaseViewModel


/**
 * @route:
 * @descript: Fragment 基类 指定了调用方法顺序逻辑 不可随意切换
 * @create: chenjiayou
 * create at 2018/11/20 20:23
 */
abstract class BaseUIFragment<VM : BaseViewModel, VD : ViewDataBinding> : BaseSwipeBackFragment<VM>() {
    private var mAddFragmentListener: OnAddFragmentListener? = null
    private var mBackHandlerInterface: BackHandlerInterface? = null
    private lateinit var dataBinding: VD

    companion object {
        val TAG = this::class.java.simpleName
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun initView()
    protected abstract fun init()
    protected abstract fun initEvent()
    protected abstract fun initData()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return attachToSwipeBack(dataBinding.root)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        preCreate()
        super.onActivityCreated(savedInstanceState)
        //以下方法不可随意切换顺序调用
        initView()
        init()
        initEvent()
        initData()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAddFragmentListener) {
            mAddFragmentListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mAddFragmentListener = null
    }


    fun onBackPressed(): Boolean {
        return false
    }

    protected fun preCreate() {}


    interface OnAddFragmentListener {
        fun onAddFragment(fromFragment: Fragment, toFragment: Fragment)
    }

    interface BackHandlerInterface {
        fun setSelectedFragment(backHandledFragment: BaseUIFragment<BaseViewModel, ViewDataBinding>)
    }

}