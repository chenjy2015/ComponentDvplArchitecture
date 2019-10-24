package com.example.baselib.http

/**
 *
 * @Author :  chenjiayou
 * @Dscription : 网络公共数据
 * @Create by : 2019/10/23
 *
 */
class HttpConstant {
    companion object {
        //证书中的公钥
        const val PUB_KEY =
            "3082010a0282010100d52ff5dd432b3a05113ec1a7065fa5a80308810e4e181cf14f7598c8d553cccb7d5111fdcdb55f6ee84fc92cd594adc1245a9c4cd41cbe407a919c5b4d4a37a012f8834df8cfe947c490464602fc05c18960374198336ba1c2e56d2e984bdfb8683610520e417a1a9a5053a10457355cf45878612f04bb134e3d670cf96c6e598fd0c693308fe3d084a0a91692bbd9722f05852f507d910b782db4ab13a92a7df814ee4304dccdad1b766bb671b6f8de578b7f27e76a2000d8d9e6b429d4fef8ffaa4e8037e167a2ce48752f1435f08923ed7e2dafef52ff30fef9ab66fdb556a82b257443ba30a93fda7a0af20418aa0b45403a2f829ea6e4b8ddbb9987f1bf0203010001"

//        const val baseUrl = "https://api.douban.com/v2/"
        const val baseUrl = "https://www.sojson.com/open/api/lunar/"
    }

    object HttpResponseCode {
        /**
         * 服务器正常响应
         */
        const val REQUEST_SUCCESS = 200
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
}