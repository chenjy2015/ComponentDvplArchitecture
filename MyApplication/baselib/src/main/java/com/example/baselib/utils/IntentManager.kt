package com.example.baselib.utils

import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.arouter.ARouterManager

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

        fun intentHomeActivity() {
            ARouterManager.instance.navigation(ARouterConstants.router_clientb_path.ACTIVITY_URL_HOME)
        }
    }
}