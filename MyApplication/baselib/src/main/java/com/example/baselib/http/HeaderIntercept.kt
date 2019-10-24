package com.example.baselib.http

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 *
 * @Author :  chenjiayou
 * @Dscription :  网络请求头 拦截器可灵活配置请求头参数
 * @Create by : 2019/10/23
 *
 */
class HeaderIntercept : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        //设置具体的header内容
        builder.header("apiKey", "81bf9da930c7f9825a3c3383f1d8d766")
        val requestBuilder = builder.method(originalRequest.method(), originalRequest.body())
        val request = requestBuilder.build()
        return chain.proceed(request)
    }


}