package com.example.baselib.http

/**
 *
 * @Author :  chenjiayou
 * @Dscription : 自定义异常
 * @Create by : 2019/10/23
 *
 */
class HttpThrowable(var errorType: Int,  var msg: String, var throwable: Throwable) : Exception() {

    companion object {
        /**
         * 解析错误
         */
        const val PARSE_ERROR = 1001
        /**
         * 连接错误
         */
        const val CONNECT_ERROR = 1002
        /**
         * DNS解析失败（无网络）
         */
        const val NO_NET_ERROR = 1003
        /**
         * 连接超时错误
         */
        const val TIME_OUT_ERROR = 1004
        /**
         * 网络（协议）错误
         */
        const val HTTP_ERROR = 1005
        /**
         * 证书错误
         */
        const val SSL_ERROR = 1006
        /**
         * 未知错误
         */
        const val UNKNOWN = 1000
    }

    override fun toString(): String {
        return "HttpThrowable{" +
                "errorType=" + errorType +
                ", message='" + message + '\''.toString() +
                ", throwable=" + throwable +
                '}'.toString()
    }
}