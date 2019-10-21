package com.example.myapplication

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselib.BaseUIActivity
import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.bean.UserVO
import com.example.baselib.service.LoginService
import com.example.baselib.utils.IntentManager
import com.example.baselib.viewmodel.LoginViewModel
import com.example.myapplication.databinding.ActivityMainBinding

/**
 *
 * @Author :  chenjiayou
 * @Dscription :  app 主界面
 * @Create by : 2019/10/21
 *
 */
@Route(path = ARouterConstants.router_path.ACTIVITY_URL_MAIN)
class MainActivity : BaseUIActivity<LoginViewModel, ActivityMainBinding>() {

    @Autowired(name = ARouterConstants.router_service.SERVICE_LOGIN)
    lateinit var lgService: LoginService

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        getDataBinding().title.text = "this is a test demo!!!"
        getViewModel().destroy()
    }

    override fun initEvent() {
        getDataBinding().launchBtn.setOnClickListener {
            IntentManager.intentDemoActivity("this is from main call")
        }

        getDataBinding().loginBtn.setOnClickListener {
            lgService.intentLogin(UserVO("刘先生",28))
        }
    }

    override fun initData() {
    }

}
