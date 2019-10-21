package com.example.clientb

import android.util.Log
import com.example.baselib.BaseApplication
import com.example.baselib.arouter.ARouterManager

class ClientBApplication :BaseApplication(){
    override fun onCreate() {
        super.onCreate()
        Log.d("Application","ClientBApplication -- create()")
        ARouterManager.instance.init(this@ClientBApplication)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouterManager.instance.destroy()
    }
}