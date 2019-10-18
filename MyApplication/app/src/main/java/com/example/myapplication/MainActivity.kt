package com.example.myapplication

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselib.arouter.ARouterManager
import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.bean.UserVO
import com.example.baselib.utils.IntentManager
import com.example.baselib.viewmodel.LoginViewModel
import com.example.myapplication.databinding.ActivityMainBinding

@Route(path = ARouterConstants.router_path.ACTIVITY_URL_MAIN)
class MainActivity : BaseActivity<LoginViewModel, ActivityMainBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        getDataBinding().title.text = "this is a test demo!!!"
    }

    override fun initEvent() {
        getDataBinding().launchBtn.setOnClickListener {
            IntentManager.intentDemoActivity("this is from main call")
        }

        getDataBinding().loginBtn.setOnClickListener {
            IntentManager.intentLoginActivity(UserVO("刘先生",28))
        }
    }

    override fun initData() {
    }

}
