package com.example.baselib.http

import com.blankj.utilcode.util.LogUtils
import com.example.baselib.i.Action1
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import retrofit2.Response

/**
 *
 * @Author :  chenjiayou
 * @Dscription : 网络结果解析器
 * @Create by : 2019/10/23
 *
 */
class HttpResponseParser {

    companion object {

        val goon: Gson = GsonManager.create()

//        inline fun <reified T : Any> parse(r: Response<ResponseBody>, throwable:Throwable, success: Action1<T>, error: Action1<HttpThrowable>) {
//            var errorMsg = ""
//            val gson = GsonManager.create()
//            val body: ResponseBody = r.body()!!
//            val response: ResponseBodyBean = gson.fromJson<ResponseBodyBean>(body.string(), ResponseBodyBean::class.java)
//            if (response.ret == HttpConstant.HttpResponseCode.REQUEST_SUCCESS && body.string().isNotEmpty()) {
//                try {
//                    success.invoke(gson.fromJson<T>(body.string(), T::class.java))
//                } catch (e: JsonParseException) {
//                    errorMsg = if (e.message.isNullOrEmpty()) "json parse exception" else e.message.toString()
//                    error.invoke(ThrowableHandler.handleThrowable(e))
//
//                } catch (e2: JsonIOException) {
//                    errorMsg = if (e2.message.isNullOrEmpty()) "json io exception" else e2.message.toString()
//                    error.invoke(ThrowableHandler.handleThrowable(e2))
//
//                } catch (e3: JsonSyntaxException) {
//                    errorMsg = if (e3.message.isNullOrEmpty()) "json syntax exception" else e3.message.toString()
//                    error.invoke(ThrowableHandler.handleThrowable(e3))
//
//                } catch (e: Exception) {
//                    errorMsg = if (e.message.isNullOrEmpty()) "json syntax exception" else e.message.toString()
//                    error.invoke(ThrowableHandler.handleThrowable(e))
//                } finally {
//                    error.invoke(HttpThrowable(response.errCode, errorMsg, JsonParseException("json parse exception")))
//                }
//            }else{
//                error.invoke(ThrowableHandler.handleThrowable(throwable))
//            }
//        }
    }

    fun getBody(r: Response<ResponseBody>): ResponseBodyBean? {
        val code = r.code()
        val body = r.body()
        val errorBody = r.errorBody()
        val response: ResponseBodyBean? = null
        //首先需要判断是否请求成功
        if (code == HttpConstant.HttpResponseCode.REQUEST_SUCCESS) {
            try {
                if (body != null) {
                    goon.fromJson(body.string(), ResponseBodyBean::class.java)
                } else {
                    goon.fromJson(errorBody?.string(), ResponseBodyBean::class.java)
                }
            } catch (e: JsonParseException) {
                LogUtils.e(e)
            } catch (e2: java.lang.Exception) {
                LogUtils.e(e2)
            }
        }
        return response
    }

    inline fun <reified T : Any> parse(
        response: ResponseBodyBean?,
        throwable: Throwable,
        success: Action1<T>,
        error: Action1<HttpThrowable>
    ) {
        var errorMsg = ""
        if (response != null && response.status == HttpConstant.HttpResponseCode.REQUEST_SUCCESS) {
            try {
                success.invoke(goon.fromJson<T>(response.data.toString(), T::class.java))
            } catch (e: JsonParseException) {
                errorMsg = if (e.message.isNullOrEmpty()) "json parse exception" else e.message.toString()
                error.invoke(ThrowableHandler.handleThrowable(e))

            } catch (e2: JsonIOException) {
                errorMsg = if (e2.message.isNullOrEmpty()) "json io exception" else e2.message.toString()
                error.invoke(ThrowableHandler.handleThrowable(e2))

            } catch (e3: JsonSyntaxException) {
                errorMsg = if (e3.message.isNullOrEmpty()) "json syntax exception" else e3.message.toString()
                error.invoke(ThrowableHandler.handleThrowable(e3))

            } catch (e: Exception) {
                errorMsg = if (e.message.isNullOrEmpty()) "json syntax exception" else e.message.toString()
                error.invoke(ThrowableHandler.handleThrowable(e))
            } finally {
                error.invoke(HttpThrowable(response.status, errorMsg, JsonParseException("json parse exception")))
            }
        } else {
            error.invoke(ThrowableHandler.handleThrowable(throwable))
        }
    }
}