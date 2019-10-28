package com.example.home

import com.example.baselib.ui.fragment.BaseUIFragment
import com.example.baselib.viewmodel.LoginViewModel
import com.example.home.databinding.FragmentWorkbenchBinding

class WorkbenchFragment : BaseUIFragment<LoginViewModel, FragmentWorkbenchBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_workbench
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