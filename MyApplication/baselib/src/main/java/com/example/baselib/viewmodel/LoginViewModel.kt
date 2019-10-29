package com.example.baselib.viewmodel

import android.app.Activity
import android.util.Log

class LoginViewModel : BaseViewModel() {
    lateinit var act:Activity
    override fun bind(act: Activity) {
        this.act = act
    }

    override fun destroy() {
        Log.d("LoginViewModel","destroy")
    }
}