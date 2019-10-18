package com.example.clientb

import android.app.Application
import com.example.baselib.arouter.ARouterManager

class ClientBApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        ARouterManager.instance.init(this@ClientBApplication)
    }
}