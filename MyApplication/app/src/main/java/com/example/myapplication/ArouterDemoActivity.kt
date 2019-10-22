package com.example.myapplication

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselib.activity.BaseUIActivity
import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.utils.IntentManager
import com.example.baselib.viewmodel.LoginViewModel
import com.example.myapplication.databinding.ActivityArouterDemoBinding

@Route(path = ARouterConstants.router_path.ACTIVITY_URL_DEMO)
class ArouterDemoActivity : BaseUIActivity<LoginViewModel, ActivityArouterDemoBinding>() {

    @Autowired
    lateinit var name:String

    override fun getLayoutId(): Int {
        return R.layout.activity_arouter_demo
    }

    override fun init() {
        getDataBinding().title.text = name
    }

    override fun initEvent() {
        getDataBinding().back.setOnClickListener {
            IntentManager.intentMainActivity()
            finish()
        }
    }

    override fun initData() {
    }
}
