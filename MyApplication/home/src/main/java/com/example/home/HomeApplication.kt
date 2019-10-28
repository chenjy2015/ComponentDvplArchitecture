package com.example.home

import android.util.Log
import com.example.baselib.BaseApplication
import com.example.baselib.arouter.ARouterManager

class HomeApplication  : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Application", "ClientApplication -- create()")
        ARouterManager.instance.init(this@HomeApplication)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouterManager.instance.destroy()
    }
}