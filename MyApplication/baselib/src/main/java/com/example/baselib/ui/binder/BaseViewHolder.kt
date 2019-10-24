package com.example.baselib.ui.binder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


class BaseViewHolder<DataBinding : ViewDataBinding>(var viewDataBinding: DataBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root)