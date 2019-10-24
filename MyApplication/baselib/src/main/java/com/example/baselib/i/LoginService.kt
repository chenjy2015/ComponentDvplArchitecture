package com.example.baselib.i

import com.alibaba.android.arouter.facade.template.IProvider
import com.example.baselib.bean.UserVO

interface LoginService : IProvider {
    fun intentLogin(user: UserVO)
}