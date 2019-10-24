package com.example.baselib.http

import android.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

object ThrowableHandler {

    fun handleThrowable(throwable: Throwable): HttpThrowable {
        return if (throwable is HttpException) {
            HttpThrowable(HttpThrowable.HTTP_ERROR, "网络(协议)异常", throwable)
        } else if (throwable is JsonParseException || throwable is JSONException || throwable is ParseException) {
            HttpThrowable(HttpThrowable.PARSE_ERROR, "数据解析异常", throwable)
        } else if (throwable is UnknownHostException) {
            HttpThrowable(HttpThrowable.NO_NET_ERROR, "网络连接失败，请稍后重试", throwable)
        } else if (throwable is SocketTimeoutException) {
            HttpThrowable(HttpThrowable.TIME_OUT_ERROR, "连接超时", throwable)
        } else if (throwable is ConnectException) {
            HttpThrowable(HttpThrowable.CONNECT_ERROR, "连接异常", throwable)
        } else if (throwable is javax.net.ssl.SSLHandshakeException) {
            HttpThrowable(HttpThrowable.SSL_ERROR, "证书验证失败", throwable)
        } else {
            HttpThrowable(HttpThrowable.UNKNOWN, throwable.message.toString(), throwable)
        }
    }


}
