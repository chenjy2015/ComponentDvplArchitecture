package com.example.baselib.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.baselib.viewmodel.BaseViewModel
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject


/**
 * Create by Melorin on 2018/11/20
 */
abstract class BaseFragment<VM : BaseViewModel> : Fragment(), LifecycleProvider<FragmentEvent> {


    private lateinit var viewModel:VM
    private var lifeSubject: BehaviorSubject<FragmentEvent> = BehaviorSubject.create()
    private var mCompositeDisposable: CompositeDisposable? = null
    private companion object {
        const val LOG_TAG = "BaseActivity"
    }

    override fun lifecycle(): Observable<FragmentEvent> {
        return lifeSubject.hide()
    }

    override fun <T> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifeSubject, event)
    }

    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindFragment(lifeSubject)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifeSubject.onNext(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeSubject.onNext(FragmentEvent.CREATE)
    }

    override fun onStart() {
        super.onStart()
        lifeSubject.onNext(FragmentEvent.START)
    }


    override fun onResume() {
        super.onResume()
        lifeSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        super.onPause()
        lifeSubject.onNext(FragmentEvent.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifeSubject.onNext(FragmentEvent.STOP)
    }

    override fun onDestroy() {
        lifeSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
        clearDisposable()
    }

    /**
     * 添加订阅
     */
    fun addDisposable(mDisposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(mDisposable)
    }

    /**
     * 取消所有订阅
     */
    fun clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
        }
    }
}