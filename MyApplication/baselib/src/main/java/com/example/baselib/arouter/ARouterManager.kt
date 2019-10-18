package com.example.baselib.arouter

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselib.BuildConfig

class ARouterManager private constructor(){

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = ARouterManager()
    }

    fun init(context: Application){
        if (BuildConfig.DEBUG){// 这两行必须写在init之前，否则这些配置在init过程
            ARouter.openLog()
            ARouter.openDebug()//使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
            ARouter.printStackTrace() // 打印日志的时候打印线程堆栈
        }
        ARouter.init(context)
    }

    fun inject(context: Context){
        ARouter.getInstance().inject(context)
    }

    fun destroy(){
        ARouter.getInstance().destroy()
    }

    fun navigation(url:String){
        ARouter.getInstance().build(url).navigation()
    }

    fun getPostcard(url:String): Postcard{
        return ARouter.getInstance().build(url)
    }
}