package com.example.baselib

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 *
 * @Author :  chenjiayou
 * @Dscription : 自定义application 方便在其他module单独运行或作为library运行时调用全局共享数据 例如context对象
 * @Create by : 2019/10/21
 *
 */
open class BaseApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        private lateinit var app: Context
    }

    override fun onCreate() {
        super.onCreate()
        app = applicationContext
    }

    fun getApp():Context{
        return app
    }
}