package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.baselib.arouter.ARouterManager

abstract class BaseActivity<T : BaseViewModel, out V : ViewDataBinding> : AppCompatActivity() {

    abstract fun getLayoutId(): Int
    abstract fun init()
    abstract fun initEvent()
    abstract fun initData()

    private lateinit var dataBinding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this@BaseActivity, getLayoutId()) as V
        ARouterManager.instance.inject(this@BaseActivity)
        init()
        initEvent()
        initData()
    }

    fun getDataBinding(): V {
        return dataBinding
    }

    fun showShortToast(s:String){
        Toast.makeText(this@BaseActivity, s, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(s:String){
        Toast.makeText(this@BaseActivity, s, Toast.LENGTH_LONG).show()
    }
}