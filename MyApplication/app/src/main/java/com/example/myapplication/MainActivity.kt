package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.ui.activity.BaseUIActivity
import com.example.baselib.utils.IntentManager
import com.example.baselib.viewmodel.LoginViewModel
import com.example.myapplication.databinding.ActivityMainBinding

/**
 *
 * @Author :  chenjiayou
 * @Dscription :  app 主界面 做中转 不做任何界面
 * @Create by : 2019/10/21
 *
 */
@Route(path = ARouterConstants.router_path_activity.ACTIVITY_PATH_MAIN)
class MainActivity : BaseUIActivity<LoginViewModel, ActivityMainBinding>() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        IntentManager.intentHomeActivity()
    }

    override fun initEvent() {
    }

    override fun initData() {
    }


}
