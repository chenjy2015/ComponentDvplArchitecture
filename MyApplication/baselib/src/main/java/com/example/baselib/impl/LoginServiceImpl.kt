package com.example.baselib.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.arouter.ARouterManager
import com.example.baselib.bean.UserVO
import com.example.baselib.utils.IntentManager
import com.example.baselib.service.LoginService

// 实现接口
@Route(path = ARouterConstants.router_service.SERVICE_LOGIN, name = "login service")
class LoginServiceImpl : LoginService {

    override fun init(context: Context?) {
    }

    override fun intentLogin(user: UserVO) {
        ARouterManager.instance.getPostcard(ARouterConstants.router_clienta_path.ACTIVITY_URL_LOGIN)
            .withParcelable("user", user)
            .navigation()
    }

}