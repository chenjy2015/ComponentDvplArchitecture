package com.example.home.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.drakeet.multitype.MultiTypeAdapter
import com.example.baselib.i.OnBinderItemClickListener
import com.example.home.BinderHomeTable
import com.example.home.HomeTableVO
import com.example.home.R

/**
 *
 * @Author :  chenjiayou
 * @Dscription : 自定义首页界面切换卡
 * @Create by : 2019/10/28
 *
 */
class HomeTableView : FrameLayout {

    private var tables = arrayListOf(
        HomeTableVO(R.drawable.ic_console, "控制台"),
        HomeTableVO(R.drawable.ic_console, "我的组织"),
        HomeTableVO(R.drawable.ic_console, "企业项目"),
        HomeTableVO(R.drawable.ic_console, "企业服务"),
        HomeTableVO(R.drawable.ic_console, "企业合作")
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)

    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributes,
        defStyleAttr
    )

    private var recyclerTable: RecyclerView = RecyclerView(context)
    private var adapter: MultiTypeAdapter = MultiTypeAdapter()
    private var listener: OnBinderItemClickListener<HomeTableVO>? = null

    init {
        initAdapter()
        addView(recyclerTable)
    }

    private fun initAdapter() {
        adapter = MultiTypeAdapter()

        adapter.register(
            HomeTableVO::class.java,
            BinderHomeTable(listener = object : OnBinderItemClickListener<HomeTableVO> {
                override fun onItemClick(position: Int, t: HomeTableVO) {
                    if (listener == null) {
                        LogUtils.d("", "position : " + position + " title: " + t.title)
                    } else {
                        listener!!.onItemClick(position, t)
                    }
                }
            })
        )
        adapter.items = tables
        adapter.notifyDataSetChanged()
        recyclerTable.adapter = adapter
        var layoutManager = GridLayoutManager(context, SizeUtils.dp2px(1.0f))
        layoutManager.spanCount = 5
        recyclerTable.layoutManager = layoutManager
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        layoutParams.height = LayoutParams.WRAP_CONTENT
        layoutParams.width = LayoutParams.MATCH_PARENT
    }
    fun setOnItemClickListener(l: OnBinderItemClickListener<HomeTableVO>) {
        this.listener = l
    }

    fun getRecylerView(): RecyclerView {
        return recyclerTable
    }

    fun getAdapter(): MultiTypeAdapter {
        return adapter
    }

}