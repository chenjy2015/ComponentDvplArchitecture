package com.example.baselib.i

import com.example.baselib.http.HttpThrowable

interface Action2<T : Any, E : HttpThrowable> {
    fun invoke(t: T, e: E)
}