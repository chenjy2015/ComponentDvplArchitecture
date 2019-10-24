package com.example.baselib.http

import com.example.baselib.bean.LunarCalendarVO

class ResponseBodyBean(
    var `data`: LunarCalendarVO,
    var message: String,
    var status: Int
)
