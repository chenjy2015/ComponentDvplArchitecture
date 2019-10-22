package com.example.baselib.impl

interface OnBinderItemClickListener<T> {
    fun onItemClick(position: Int, t: T)
}