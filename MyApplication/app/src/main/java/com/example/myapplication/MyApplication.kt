package com.example.myapplication

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselib.arouter.ARouterManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ARouterManager.instance.init(this@MyApplication)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouterManager.instance.destroy()
    }
}