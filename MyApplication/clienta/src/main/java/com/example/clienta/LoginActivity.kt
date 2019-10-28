package com.example.clienta

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselib.ui.activity.BaseUIActivity
import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.bean.UserVO
import com.example.baselib.utils.IntentManager
import com.example.baselib.viewmodel.LoginViewModel
import com.example.clienta.databinding.ActivityLoginBinding

@Route(path = ARouterConstants.router_path_activity.ACTIVITY_PATH_LOGIN)
class LoginActivity : BaseUIActivity<LoginViewModel, ActivityLoginBinding>() {

    var userVO: UserVO? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun init() {
        userVO = intent.getParcelableExtra("user")
        if (userVO !=null){
            getDataBinding().user = userVO
        }
    }

    override fun initEvent() {
        getDataBinding().login.setOnClickListener {
            IntentManager.intentHomeActivity()
            finish()
        }

        getDataBinding().loginOut.setOnClickListener {
            IntentManager.intentMainActivity()
            finish()
        }
    }

    override fun initData() {
    }

}
