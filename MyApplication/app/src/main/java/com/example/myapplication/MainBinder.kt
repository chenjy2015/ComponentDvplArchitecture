package com.example.myapplication

import com.example.baselib.binder.BaseItemViewBinder
import com.example.baselib.binder.BaseViewHolder
import com.example.baselib.impl.OnBinderItemClickListener
import com.example.myapplication.databinding.BinderMainBinding

class MainBinder(onItemClick: OnBinderItemClickListener<String>) :
    BaseItemViewBinder<String, BaseViewHolder<BinderMainBinding>, BinderMainBinding>() {

    var listener = onItemClick

    override fun getLayoutId(): Int {
        return R.layout.binder_main
    }

    override fun bind(holder: BaseViewHolder<BinderMainBinding>, item: String) {
        holder.viewDataBinding.menu = item
        holder.viewDataBinding.title.setOnClickListener {
            listener?.onItemClick(holder.adapterPosition, item)
        }
    }
}