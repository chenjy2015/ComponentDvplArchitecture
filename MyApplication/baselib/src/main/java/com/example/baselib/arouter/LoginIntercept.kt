package com.example.baselib.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.example.baselib.arouter.ARouterConstants
import java.lang.RuntimeException

/**
 *
 * @Author :  chenjiayou
 * @Dscription : 跳转登录拦截器
 * @Create by : 2019/10/21
 *
 */
//@Interceptor(name = "login", priority = ARouterConstants.router_intercept_priority.LOGIN_INTERCEPT_PRIORITY)
class LoginIntercept : IInterceptor {
    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        //Do some checking work
        callback?.onInterrupt(RuntimeException("cannot find this user name!"))
        //callback?.onContinue(postcard)
    }

    override fun init(context: Context?) {
    }

}