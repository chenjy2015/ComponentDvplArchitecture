package com.example.myapplication

import android.util.Log
import com.example.baselib.BaseApplication
import com.example.baselib.arouter.ARouterManager
import com.example.baselib.http.HttpRequestManager

class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Application", "MyApplication -- create()")
        ARouterManager.instance.init(this@MyApplication)
        HttpRequestManager.instance.init(this@MyApplication)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouterManager.instance.destroy()
    }
}