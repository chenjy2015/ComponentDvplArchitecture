package com.example.baselib.fragment

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.baselib.utils.ViewUtils
import com.example.myapplication.BaseViewModel


/**
 * @route:
 * @descript: Fragment 基类 指定了调用方法顺序逻辑 不可随意切换
 * @create: chenjiayou
 * create at 2018/11/20 20:23
 */
abstract class BaseUIFragment<VM : BaseViewModel, VD : ViewDataBinding> : SwipeBackFragment<VM>() {
    private var mAddFragmentListener: OnAddFragmentListener? = null
    private var mBackHandlerInterface: BackHandlerInterface? = null
    private lateinit var dataBinding: VD
    protected abstract val layoutId: Int

    companion object {
        val TAG = this::class.java.simpleName
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
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


    fun onBackPressed(): Boolean {
        return false
    }

    protected fun preCreate() {

    }

    protected abstract fun initView()

    protected abstract fun init()

    protected abstract fun initEvent()

    protected abstract fun initData()

    interface OnAddFragmentListener {
        fun onAddFragment(fromFragment: Fragment, toFragment: Fragment)
    }

    interface BackHandlerInterface {
        fun setSelectedFragment(backHandledFragment: BaseUIFragment<BaseViewModel, ViewDataBinding>)
    }

}