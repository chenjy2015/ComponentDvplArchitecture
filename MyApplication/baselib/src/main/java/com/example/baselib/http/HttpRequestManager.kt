package com.example.baselib.http


import android.annotation.SuppressLint
import android.content.Context
import com.example.baselib.bean.UserVO

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.io.File
import java.util.concurrent.TimeUnit

import javax.net.ssl.SSLSocketFactory

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HttpRequestManager private constructor() : ISSlHttpRequest {

    private var mOkHttpClient: OkHttpClient? = null

    private var mRetrofit: Retrofit? = null

    private var mContext: Context? = null

    //创建一个不验证证书链的证书信任管理器。 客户端并为对ssl证书的有效性进行校验
    override val sslSocketFactory: SSLSocketFactory
        @Throws(Exception::class)
        get() {
            return SSLSocketFactoryUtils.getSSLSocketFactory()
        }

    private object Singleton {
        @SuppressLint("StaticFieldLeak")
        val INSTANCE = HttpRequestManager()
    }

    fun init(context: Context): HttpRequestManager {
        this.mContext = context
        initOKHttp()
        initRetrofit()
        return this
    }

    private fun initOKHttp() {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(15, TimeUnit.SECONDS)
        initIntercept(okHttpClientBuilder)
        mOkHttpClient = okHttpClientBuilder.build()
    }

    private fun initIntercept(okHttpClientBuilder: OkHttpClient.Builder) {
        //缓存地址
        val cacheFile = File(mContext!!.externalCacheDir, "HttpCache")
        //大小50Mb
        val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())
        //设置缓存方式、时长、地址
        val cacheIntercept = CacheIntercept()
        okHttpClientBuilder.addInterceptor(cacheIntercept)
        okHttpClientBuilder.addNetworkInterceptor(cacheIntercept)
        okHttpClientBuilder.cache(cache)

        try {
            //TODO 需要用到ssl时再解开注释
//            okHttpClientBuilder.sslSocketFactory(sslSocketFactory)
//                .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //init header
        val headerIntercept = HeaderIntercept()
        okHttpClientBuilder.addInterceptor(headerIntercept)
        //init logger
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
    }


    private fun initRetrofit() {
        mRetrofit = Retrofit.Builder()
            .baseUrl(HttpConstant.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(mOkHttpClient!!)
            .build()
    }

    private fun buildGson(): Gson {
        return GsonBuilder().serializeNulls()
            .registerTypeAdapter(Int::class.javaPrimitiveType, GsonIntegerDefaultAdapter()).create()

    }

    fun <T> create(cls: Class<T>): T {
        return mRetrofit!!.create(cls)
    }

    companion object {
        val instance: HttpRequestManager
            get() = Singleton.INSTANCE
    }

}
