package com.example.baselib.utils

import com.example.baselib.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType
/**
 *
 * @Author :  chenjiayou
 * @Dscription :  根据反射提取当前类泛型viewmodel对象
 * @Create by : 2019/10/21
 *
 */
class PUtils {
    companion object {
        fun <T> create(`object`: Any): T? {
            val clz = `object`.javaClass
            val type = clz.genericSuperclass

            val pt = type as ParameterizedType
            var modelClass: Class<*>? = null

            try {
                for (t in pt.getActualTypeArguments()) {
                    if (BaseViewModel::class.java.isAssignableFrom(t as Class<*>)) {
                        modelClass = t
                        break
                    }
                }
                return modelClass!!.newInstance() as T
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }
            return null
        }
    }
}