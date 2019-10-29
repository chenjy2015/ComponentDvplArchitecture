package com.example.baselib.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baselib.arouter.ARouterManager
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import android.os.Build
import androidx.annotation.CallSuper
import android.content.pm.ActivityInfo
import android.app.Activity
import android.content.res.Resources
import android.content.res.TypedArray
import com.blankj.utilcode.util.LogUtils
import com.example.baselib.ActivityCollector
import com.example.baselib.constants.SettingConst
import com.example.baselib.utils.PUtils
import com.example.baselib.viewmodel.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

/**
 *
 * @Author :  chenjiayou
 * @Dscription :  activity父类 集成共享数据和方法
 * @Create by : 2019/10/22
 *
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), LifecycleProvider<ActivityEvent> {

    private lateinit var viewModel:VM

    private companion object {
        var lifeSubject: BehaviorSubject<ActivityEvent> = BehaviorSubject.create()
        var mCompositeDisposable: CompositeDisposable? = null
        var mIsForeground: Boolean = false
        const val LOG_TAG = "BaseActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //Android 8.0 系统一个限制 如果是透明背景则不能固定方向否则报错
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            val result = fixOrientation()
            LogUtils.i(LOG_TAG, "onCreate fixOrientation when Oreo, result = $result")
        }
        super.onCreate(savedInstanceState)
        viewModel = PUtils.create<VM>(this@BaseActivity)!!
        ARouterManager.instance.inject(this@BaseActivity)
    }

    fun getViewModel():VM{
        return viewModel
    }


    override fun lifecycle(): Observable<ActivityEvent> {
        return lifeSubject.hide()
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(lifeSubject)
    }

    override fun <T : Any?> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifeSubject, event)
    }

    @SuppressLint("PrivateApi")
    private fun isTranslucentOrFloating(): Boolean {
        var isTranslucentOrFloating = false
        try {
            val styleableRes =
                Class.forName("com.android.internal.R\$styleable").getField("Window").get(null) as IntArray
            val ta = obtainStyledAttributes(styleableRes)
            val m = ActivityInfo::class.java.getMethod("isTranslucentOrFloating", TypedArray::class.java)
            m.isAccessible = true
            isTranslucentOrFloating = m.invoke(null, ta) as Boolean
            m.isAccessible = false
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return isTranslucentOrFloating
    }

    private fun fixOrientation(): Boolean {
        try {
            val field = Activity::class.java.getDeclaredField("mActivityInfo")
            field.isAccessible = true
            val o = field.get(this) as ActivityInfo
            o.screenOrientation = -1
            field.isAccessible = false
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            LogUtils.i(LOG_TAG, "avoid calling setRequestedOrientation when Oreo.")
            return
        }
        super.setRequestedOrientation(requestedOrientation)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifeSubject.onNext(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifeSubject.onNext(ActivityEvent.RESUME)
        mIsForeground = true
    }

    @CallSuper
    override fun onPause() {
        lifeSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
        mIsForeground = false
    }

    @CallSuper
    override fun onStop() {
        lifeSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        lifeSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
        cleanDisposable()
        ActivityCollector.removeActivity(this)
    }

    /**
     * 添加订阅
     */
    fun addDisposable(mDisposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(mDisposable)
    }

    /**
     * 取消所有订阅
     */
    fun cleanDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable?.clear()
        }
    }

    private var fontScale = -1f //动态设置放大倍数
    private val MULTIPLE = 1.0f //默认放大倍数
    private var languageType: String? = null

    fun setTextSizeModel(fontScale: Float) {
        this.fontScale = fontScale
    }

    fun setLanguageType(languageType: String) {
        this.languageType = languageType
    }

    override fun getResources(): Resources? {

        val resources = super.getResources()
        if (resources != null) {
            if (fontScale < 0) {
                fontScale = 1 * MULTIPLE
            }
            val dm = resources.displayMetrics
            val config = resources.configuration
            // 应用字号设置
            config.fontScale = fontScale
            // 应用用户选择语言
            if (languageType == null) {
                config.locale = Locale.getDefault()
            } else {
                config.locale =
                    if (languageType == SettingConst.LanguageType.Chinese) Locale.CHINESE else Locale.ENGLISH
            }
            resources.updateConfiguration(config, dm)
        }
        return resources
    }
}