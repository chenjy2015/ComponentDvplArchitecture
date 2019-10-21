package com.example.baselib.viewmodel

import android.util.Log
import com.example.myapplication.BaseViewModel

class LoginViewModel : BaseViewModel() {
    override fun destroy() {
        Log.d("LoginViewModel","destroy")
    }
}