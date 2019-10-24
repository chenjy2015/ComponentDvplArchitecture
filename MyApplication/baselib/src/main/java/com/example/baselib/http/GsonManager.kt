package com.example.baselib.http

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.text.DateFormat

class GsonManager {
    companion object {
        private val g: Gson = GsonBuilder()
            .registerTypeAdapter(GsonIntegerDefaultAdapter::class.java, GsonIntegerDefaultAdapter())
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setDateFormat(DateFormat.LONG)
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写
            .setPrettyPrinting()
            .setVersion(1.0)
            .create()

        fun create(): Gson {
            return g
        }
    }
}