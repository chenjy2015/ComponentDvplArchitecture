package com.example.clientb

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselib.ui.activity.BaseUIActivity
import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.viewmodel.HomeViewModel
import com.example.clientb.databinding.ActivityHomeBinding

@Route(path = ARouterConstants.router_clientb_path.ACTIVITY_URL_HOME)
class HomeActivity : BaseUIActivity<HomeViewModel, ActivityHomeBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun init() {
    }

    override fun initEvent() {
    }

    override fun initData() {
    }
}
