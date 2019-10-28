package com.example.baselib.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.arouter.ARouterManager
import com.example.baselib.bean.UserVO
import com.example.baselib.i.LoginService

// 实现接口
@Route(path = ARouterConstants.router_path_service.SERVICE_LOGIN, name = "login service")
class LoginServiceImpl : LoginService {

    lateinit var context:Context
    override fun init(context: Context?) {
        this.context = context!!
    }

    override fun intentLogin(user: UserVO) {
        ARouterManager.instance.getPostcard(ARouterConstants.router_path_activity.ACTIVITY_PATH_LOGIN)
            .withParcelable("user", user)
            .navigation(context, LoginNavigationCallbackImpl())
    }

}