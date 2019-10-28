package com.example.baselib.arouter

class ARouterConstants {

    object router_path_service{
        const val SERVICE_LOGIN = "/service/login"
    }

    object router_path_activity {
        const val ACTIVITY_PATH_DEMO = "/app/demo"
        const val ACTIVITY_PATH_MAIN = "/app/main"
        const val ACTIVITY_PATH_HOME = "/app/home"
        const val ACTIVITY_PATH_LOGIN = "/clienta/login"
        const val ACTIVITY_PATH_HOME_CLIENTB = "/clientb/home"
    }

    object router_path_fragment{
        const val FRAGMENT_PATH_SETTING = "/home/setting"
        const val FRAGMENT_PATH_WORKBENCH = "/home/workbench"
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