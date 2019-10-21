package com.example.clienta

import android.util.Log
import com.example.baselib.BaseApplication
import com.example.baselib.arouter.ARouterManager

class ClientApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Application", "ClientApplication -- create()")
        ARouterManager.instance.init(this@ClientApplication)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouterManager.instance.destroy()
    }
}