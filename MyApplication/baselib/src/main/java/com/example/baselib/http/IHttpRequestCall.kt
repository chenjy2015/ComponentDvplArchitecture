package com.example.baselib.http

interface IHttpRequestCall<T : Any> {

    fun success(t: T)

    fun <E : HttpThrowable> error(e: E)
}