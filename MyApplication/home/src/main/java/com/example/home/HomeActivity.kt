package com.example.home

import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.example.baselib.arouter.ARouterConstants
import com.example.baselib.i.OnBinderItemClickListener
import com.example.baselib.ui.activity.BaseUIActivity
import com.example.baselib.viewmodel.LoginViewModel
import com.example.baselib.widget.reside_menu.ResideMenu
import com.example.baselib.widget.reside_menu.ResideMenuItem
import com.example.home.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_enterprise_console_title.*
import kotlinx.android.synthetic.main.item_enterprise_console_title.view.*
import kotlinx.android.synthetic.main.toolbar_home.*

/**
 *
 * @Author :  chenjiayou
 * @Dscription : app 首页 出版模型：v1.0
 * @Create by : 2019/10/25
 *
 */
@Route(path = ARouterConstants.router_path_activity.ACTIVITY_PATH_HOME)
class HomeActivity : BaseUIActivity<LoginViewModel, ActivityHomeBinding>(), ResideMenu.OnMenuListener,
    View.OnClickListener, OnBinderItemClickListener<HomeTableVO> {


    private lateinit var resideMenu: ResideMenu
    private lateinit var itemHome: ResideMenuItem

    override fun openMenu() {
        LogUtils.d("Menu is opened!")
    }

    override fun closeMenu() {
        LogUtils.d("Menu is close!")
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun init() {
        // attach to current activity;
        resideMenu = ResideMenu(this)
        resideMenu.setUse3D(false)
        resideMenu.setBackground(R.drawable.menu_background)
        resideMenu.attachToActivity(this)
        resideMenu.setMenuListener(this)
        resideMenu.setShadowVisible(true)
        //valid scale factor is between 0.0f and 1.0f. left menu'width is 150dip.
        resideMenu.setScaleValue(0.8f)
        resideMenu.isClickable = false

        // create menu items;
        itemHome = ResideMenuItem(this, R.drawable.icon_home, "Home")
        itemHome.setOnClickListener(this)
        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT)

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT)

    }

    override fun initEvent() {
        iv_menu.setOnClickListener(this)
        iv_avatar.setOnClickListener(this)
        ic_alarm.setOnClickListener(this)
        iv_app.setOnClickListener(this)
        iv_news.setOnClickListener(this)
        ll_console_name.setOnClickListener(this)
        ll_console_name.setOnClickListener(this)
        iv_close.setOnClickListener(this)

        homeTable.setOnItemClickListener(this)
    }

    override fun initData() {

    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return resideMenu.dispatchTouchEvent(ev)
    }

    override fun onItemClick(position: Int, t: HomeTableVO) {
        LogUtils.d("", "position : " + position + " title: " + t.title)
    }

    override fun onClick(v: View?) {
        when {
            v === itemHome -> changeFragment(WorkbenchFragment())
            v === iv_menu -> resideMenu.openMenu(ResideMenu.DIRECTION_LEFT)
        }
        LogUtils.d("v : " + v.toString())
        resideMenu.closeMenu()
    }


    private fun changeFragment(targetFragment: Fragment) {
        resideMenu.clearIgnoredViewList()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, targetFragment, "fragment")
            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

}