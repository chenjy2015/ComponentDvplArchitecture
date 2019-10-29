package com.example.baselib.viewmodel

import android.app.Activity

abstract class BaseViewModel {
    abstract fun destroy()
    abstract fun bind(act: Activity)
}