package com.example.baselib

import com.example.myapplication.BaseViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.baselib.arouter.ARouterManager
import com.example.baselib.utils.PUtils
import com.example.myapplication.BaseActivity

abstract class BaseUIActivity<M : BaseViewModel, out V : ViewDataBinding> : BaseActivity() {

    abstract fun getLayoutId(): Int
    abstract fun init()
    abstract fun initEvent()
    abstract fun initData()

    private lateinit var dataBinding: V
    private lateinit var viewModel:M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this@BaseUIActivity, getLayoutId()) as V
        ARouterManager.instance.inject(this@BaseUIActivity)
        viewModel = PUtils.create<M>(this@BaseUIActivity)!!
        init()
        initEvent()
        initData()
    }

    fun getDataBinding(): V {
        return dataBinding
    }

    fun getViewModel():M{
        return viewModel
    }

    fun showShortToast(s:String){
        Toast.makeText(this@BaseUIActivity, s, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(s:String){
        Toast.makeText(this@BaseUIActivity, s, Toast.LENGTH_LONG).show()
    }
}