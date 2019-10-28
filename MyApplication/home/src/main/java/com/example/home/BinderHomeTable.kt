package com.example.home

import com.example.baselib.i.OnBinderItemClickListener
import com.example.baselib.ui.binder.BaseItemViewBinder
import com.example.baselib.ui.binder.BaseViewHolder
import com.example.home.databinding.BinderHomeTableBinding

class BinderHomeTable(var listener: OnBinderItemClickListener<HomeTableVO>) :
    BaseItemViewBinder<HomeTableVO, BaseViewHolder<BinderHomeTableBinding>, BinderHomeTableBinding>() {

    override fun bind(holder: BaseViewHolder<BinderHomeTableBinding>, item: HomeTableVO) {
        holder.viewDataBinding.table = item
        holder.viewDataBinding.root.setOnClickListener {
            listener.onItemClick(holder.adapterPosition, item)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.binder_home_table
    }
}