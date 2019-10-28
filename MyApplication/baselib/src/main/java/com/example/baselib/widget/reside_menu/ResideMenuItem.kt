package com.example.baselib.widget.reside_menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.baselib.R


class ResideMenuItem : LinearLayout {

    /** menu item  icon   */
    private var iv_icon: ImageView? = null
    /** menu item  title  */
    private var tv_title: TextView? = null


    constructor (context: Context) : super(context) {
        initViews(context)
    }

    constructor (context: Context, icon: Int, title: Int) : super(context) {
        initViews(context)
        iv_icon!!.setImageResource(icon)
        tv_title!!.setText(title)
    }

    constructor (context: Context, icon: Int, title: String) : super(context) {
        initViews(context)
        iv_icon!!.setImageResource(icon)
        tv_title!!.text = title
    }

    private fun initViews(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.residemenu_item, this)
        iv_icon = findViewById(R.id.iv_icon)
        tv_title = findViewById(R.id.tv_title)
    }

    /**
     * set the icon color;
     *
     * @param icon
     */
    fun setIcon(icon: Int) {
        iv_icon!!.setImageResource(icon)
    }

    /**
     * set the title with resource
     * ;
     * @param title
     */
    fun setTitle(title: Int) {
        tv_title!!.setText(title)
    }

    /**
     * set the title with string;
     *
     * @param title
     */
    fun setTitle(title: String) {
        tv_title!!.text = title
    }
}