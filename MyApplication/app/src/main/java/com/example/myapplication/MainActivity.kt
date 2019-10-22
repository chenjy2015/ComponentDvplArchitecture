package com.example.myapplication

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.drakeet.multitype.MultiTypeAdapter
import com.example.baselib.activity.BaseUIActivity
import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.bean.UserVO
import com.example.baselib.impl.OnBinderItemClickListener
import com.example.baselib.service.LoginService
import com.example.baselib.utils.IntentManager
import com.example.baselib.viewmodel.LoginViewModel
import com.example.myapplication.databinding.ActivityMainBinding
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration

/**
 *
 * @Author :  chenjiayou
 * @Dscription :  app 主界面
 * @Create by : 2019/10/21
 *
 */
@Route(path = ARouterConstants.router_path.ACTIVITY_URL_MAIN)
class MainActivity : BaseUIActivity<LoginViewModel, ActivityMainBinding>() {

    @Autowired(name = ARouterConstants.router_service.SERVICE_LOGIN)
    lateinit var lgService: LoginService
    lateinit var adapter: MultiTypeAdapter
    var menus = arrayOf("pass value to demo", "go to login")

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        adapter = MultiTypeAdapter()
        adapter.register(String::class.java, MainBinder(object :OnBinderItemClickListener<String>{
            override fun onItemClick(position: Int, t: String) {
                when(position){
                    0 -> IntentManager.intentDemoActivity("this is from main call")
                    1 -> lgService.intentLogin(UserVO("刘先生", 28))
                }
            }
        }))
        adapter.items = menus.asList()
        getDataBinding().recycler.adapter = adapter
        getDataBinding().recycler.adapter?.notifyDataSetChanged()
        getDataBinding().recycler.layoutManager = LinearLayoutManager(this@MainActivity)
        getDataBinding().recycler.addItemDecoration(HorizontalDividerItemDecoration.Builder(this@MainActivity).build())
    }

    override fun initEvent() {

    }

    override fun initData() {

    }

}
