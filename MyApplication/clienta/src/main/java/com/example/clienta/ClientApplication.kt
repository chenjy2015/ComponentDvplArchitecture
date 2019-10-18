package com.example.clienta

import android.app.Application
import com.example.baselib.arouter.ARouterManager

class ClientApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        ARouterManager.instance.init(this@ClientApplication)
    }
}