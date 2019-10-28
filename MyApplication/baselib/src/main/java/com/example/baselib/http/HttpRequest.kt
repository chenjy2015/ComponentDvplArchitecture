package com.example.baselib.http

import android.annotation.SuppressLint
import com.example.baselib.bean.LunarCalendarVO
import com.example.baselib.bean.UserVO
import com.example.baselib.i.Action1
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

@SuppressLint("CheckResult")
class HttpRequest {

    companion object {

        const val TAG: String = "HttpRequest"

        private val service: IHttpService = HttpRequestManager.instance.create(IHttpService::class.java)


        /**
         * TODO:模拟网络请求获取一本书信息
         *
         */
        fun getBook(bookId: Int, call: IHttpRequestCall<UserVO>) {
            service.getBook(bookId)
                .map { rsp: Response<ResponseBody> ->
                    HttpResponseParser().getBody(rsp)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { rsp: ResponseBodyBean?, e: Throwable? ->
                    if (rsp == null) {
                        call.error(HttpThrowable(HttpThrowable.PARSE_ERROR, "数据解析异常", e!!))
                        return@subscribe
                    }
                    HttpResponseParser().parse(rsp, e!!, object : Action1<UserVO> {
                        override fun invoke(t: UserVO) {
                            call.success(t)
                        }

                    }, object : Action1<HttpThrowable> {
                        override fun invoke(t: HttpThrowable) {
                            call.error(t)
                        }
                    })
                }
        }

        /**
         * TODO:模拟网络请求获取当前农历信息
         *
         */
        fun getLunarCalendar(dateTime: String, call: IHttpRequestCall<LunarCalendarVO>) {
            service.getLunarCalendar(dateTime)
                .map { rsp: Response<ResponseBody> ->
                    HttpResponseParser().getBody(rsp)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { rsp: ResponseBodyBean?, e: Throwable? ->
                    if (rsp == null) {
                        call.error(HttpThrowable(HttpThrowable.PARSE_ERROR, "数据解析异常", e!!))
                        return@subscribe
                    }
                    HttpResponseParser().parse(rsp, e!!, object : Action1<LunarCalendarVO> {
                        override fun invoke(t: LunarCalendarVO) {
                            call.success(t)
                        }

                    }, object : Action1<HttpThrowable> {
                        override fun invoke(t: HttpThrowable) {
                            call.error(t)
                        }
                    })
                }
        }
    }
}


