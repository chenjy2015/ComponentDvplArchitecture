package com.example.baselib.service

import com.alibaba.android.arouter.facade.template.IProvider
import com.example.baselib.bean.UserVO

interface LoginService : IProvider {
    fun intentLogin(user: UserVO)
}