package com.example.baselib


import android.annotation.TargetApi
import android.app.Activity
import android.os.Build

import java.util.HashMap
import java.util.LinkedHashMap

/**
 * Author: Created by chenjy on 2019/4/9.
 * Description:管理所有的栈中的Activity
 */
object ActivityCollector {

    /**
     * 存放activity的列表
     */
    private val activities: HashMap<Class<*>, Activity> = LinkedHashMap()


    /**
     * 添加Activity
     *
     * @param activity
     */
    fun addActivity(activity: Activity, clz: Class<*>) {
        activities[clz] = activity
    }

    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun <T : Activity> isActivityExist(clz: Class<T>): Boolean {
        val res: Boolean
        val activity = getActivity(clz)
        res = if (activity == null) {
            false
        } else {
            !(activity.isFinishing || activity.isDestroyed)
        }
        return res
    }

    /**
     * 获得指定activity实例
     *
     * @param clazz Activity 的类对象
     * @return
     */
    private fun <T : Activity> getActivity(clazz: Class<T>): Activity? {
        return activities[clazz]
    }

    /**
     * 移除activity,代替finish
     *
     * @param activity
     */
    fun removeActivity(activity: Activity) {
        if (activities.containsValue(activity)) {
            activities.remove(activity.javaClass)
        }
    }

    /**
     * 移除所有的Activity
     */
    fun removeAllActivity() {
        if (activities.size > 0) {
            val sets = activities.entries
            for ((_, value) in sets) {
                if (!value.isFinishing) {
                    value.finish()
                }
            }
        }
        activities.clear()
    }
}