package com.example.home

import com.example.baselib.ui.fragment.BaseUIFragment
import com.example.baselib.viewmodel.LoginViewModel
import com.example.home.databinding.FragmentSettingBinding
import com.example.home.databinding.FragmentWorkbenchBinding

class SettingFragment : BaseUIFragment<LoginViewModel, FragmentSettingBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun initView() {
    }

    override fun init() {
        setSwipeBackEnable(false)
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

}