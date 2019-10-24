package com.example.baselib.http

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface IHttpService {

    /**
     * 农历查询API接口
     * TODO; 测试地址 https://www.sojson.com/api/lunar.html
     */
    @GET("json.shtml")
    fun getLunarCalendar(@Query("date") date: String): Single<Response<ResponseBody>>


    @GET("book/{id}")
    fun getBook(@Path("id") id: Int): Single<Response<ResponseBody>>

    @FormUrlEncoded
    @POST("book/info/")
    fun getInfo(@Field("key") key: String, @Field("value") value: String): Single<Response<ResponseBody>>

    @FormUrlEncoded
    @POST("book/info/")
    fun getInfo(@FieldMap map: Map<String, String>): Single<Response<ResponseBody>>

    @Multipart
    @POST("book/upload/")
    fun upLoadTextAndFile(@Part("textKey") textBody: RequestBody, @Part("fileKey") fileBody: RequestBody): Single<Response<ResponseBody>>

    @Multipart
    @POST("book/upload/")
    fun upLoadTextAndFile(@PartMap textBody: Map<String, RequestBody>, @PartMap fileBody: Map<String, RequestBody>): Single<Response<ResponseBody>>

    @Multipart
    @POST("book/upload/")
    fun upLoadTextAndFile(@Body multipartBody: MultipartBody): Single<Response<ResponseBody>>
}