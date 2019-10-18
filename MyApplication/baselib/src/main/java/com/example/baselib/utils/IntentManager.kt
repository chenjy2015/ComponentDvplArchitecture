package com.example.baselib.utils

import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.arouter.ARouterManager
import com.example.baselib.bean.UserVO

class IntentManager {
    companion object {
        fun intentDemoActivity(s: String) {
            ARouterManager.instance.getPostcard(ARouterConstants.router_path.ACTIVITY_URL_DEMO)
                .withString("name", s)
                .navigation()
        }

        fun intentMainActivity() {
            ARouterManager.instance.navigation(ARouterConstants.router_path.ACTIVITY_URL_MAIN)
        }

        fun intentLoginActivity(user: UserVO) {
            ARouterManager.instance.getPostcard(ARouterConstants.router_clienta_path.ACTIVITY_URL_LOGIN)
                .withParcelable("user", user)
                .navigation()
        }

        fun intentHomeActivity() {
            ARouterManager.instance.navigation(ARouterConstants.router_clientb_path.ACTIVITY_URL_HOME)
        }
    }
}