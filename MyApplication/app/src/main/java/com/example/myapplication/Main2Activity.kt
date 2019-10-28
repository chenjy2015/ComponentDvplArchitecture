package com.example.myapplication

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.drakeet.multitype.MultiTypeAdapter
import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.bean.LunarCalendarVO
import com.example.baselib.bean.UserVO
import com.example.baselib.http.HttpRequest
import com.example.baselib.http.HttpThrowable
import com.example.baselib.http.IHttpRequestCall
import com.example.baselib.i.OnBinderItemClickListener
import com.example.baselib.i.LoginService
import com.example.baselib.ui.activity.BaseUIActivity
import com.example.baselib.utils.IntentManager
import com.example.baselib.viewmodel.LoginViewModel
import com.example.myapplication.databinding.ActivityMain2Binding
import com.example.myapplication.databinding.ActivityMainBinding
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration

/**
 *
 * @Author :  chenjiayou
 * @Dscription :  app 主界面
 * @Create by : 2019/10/21
 *
 */
@Route(path = ARouterConstants.router_path_activity.ACTIVITY_PATH_MAIN)
class Main2Activity : BaseUIActivity<LoginViewModel, ActivityMain2Binding>() {

    @Autowired(name = ARouterConstants.router_path_service.SERVICE_LOGIN)
    lateinit var lgService: LoginService
    lateinit var adapter: MultiTypeAdapter
    var menus = arrayOf("pass value to demo", "go to login")

    override fun getLayoutId(): Int {
        return R.layout.activity_main2
    }

    override fun init() {
        adapter = MultiTypeAdapter()
        adapter.register(String::class.java, MainBinder(object :
            OnBinderItemClickListener<String> {
            override fun onItemClick(position: Int, t: String) {
                when (position) {
                    0 -> IntentManager.intentDemoActivity("this is from main call")
                    1 -> lgService.intentLogin(UserVO("刘先生", 28))
//                    1 -> request()
                }
            }
        }))
        adapter.items = menus.asList()
        getDataBinding().recycler.adapter = adapter
        getDataBinding().recycler.adapter?.notifyDataSetChanged()
        getDataBinding().recycler.layoutManager = LinearLayoutManager(this@Main2Activity)
        getDataBinding().recycler.addItemDecoration(HorizontalDividerItemDecoration.Builder(this@Main2Activity).build())
    }

    override fun initEvent() {
    }

    fun request(){
//        HttpRequest.getBook(10023, object : IHttpRequestCall<UserVO> {
//            override fun success(t: UserVO) {
//                LogUtils.d(HttpRequest.TAG, "success : bookInfo -->  $t")
//            }
//
//            override fun <E : HttpThrowable> error(e: E) {
//                LogUtils.d(HttpRequest.TAG, "failed : $e")
//            }
//        })

        HttpRequest.getLunarCalendar("2019-10-24", object : IHttpRequestCall<LunarCalendarVO> {
            override fun success(t: LunarCalendarVO) {
                LogUtils.d(HttpRequest.TAG, "success : bookInfo -->  $t")
            }

            override fun <E : HttpThrowable> error(e: E) {
                LogUtils.d(HttpRequest.TAG, "failed : $e")
            }
        })
    }

    override fun initData() {

    }



}
