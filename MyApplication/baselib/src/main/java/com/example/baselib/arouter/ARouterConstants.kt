package com.example.baselib.arouter

class ARouterConstants {

    object router_service{
        const val SERVICE_LOGIN = "/service/login"
    }

    object router_path {
        const val ACTIVITY_URL_DEMO = "/app/demo"
        const val ACTIVITY_URL_MAIN = "/app/main"
    }

    object router_clienta_path {
        const val ACTIVITY_URL_LOGIN = "/clienta/login"
    }

    object router_clientb_path {
        const val ACTIVITY_URL_HOME = "/clientb/home"
    }

    /**
     *
     * description: 跳转拦截器 优先级 因为不能重复设置 统一在此设定 便于管理
     *
     */
    object router_intercept_priority{
        const val LOGIN_INTERCEPT_PRIORITY = 5 //跳转登录界面拦截器优先级
    }
}