package com.example.baselib.arouter

import androidx.fragment.app.Fragment

class FragmentUtils {

    fun getSettingFragment():Fragment{
        return ARouterManager.instance.navigation(ARouterConstants.router_path_fragment.FRAGMENT_PATH_SETTING) as Fragment
    }

    fun getWorkbenchFragment():Fragment{
        return ARouterManager.instance.navigation(ARouterConstants.router_path_fragment.FRAGMENT_PATH_WORKBENCH) as Fragment
    }

}