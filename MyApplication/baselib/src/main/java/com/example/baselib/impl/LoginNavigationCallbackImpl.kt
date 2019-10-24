package com.example.baselib.impl

import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback

/**
 *
 * @Author :  chenjiayou
 * @Dscription :  跳转到登录界面拦截器回调
 * @Create by : 2019/10/21
 *
 */
class LoginNavigationCallbackImpl : NavigationCallback {
    override fun onLost(postcard: Postcard?) {

    }

    override fun onFound(postcard: Postcard?) {
    }

    override fun onArrival(postcard: Postcard?) {
    }

    override fun onInterrupt(postcard: Postcard?) {
        var path: String = postcard?.path.toString()
        Log.d("onInterrupt", "path :$path")
    }

}