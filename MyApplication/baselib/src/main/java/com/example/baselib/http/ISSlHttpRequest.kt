package com.example.baselib.http

import javax.net.ssl.SSLSocketFactory

interface ISSlHttpRequest {

    val sslSocketFactory: SSLSocketFactory
}
