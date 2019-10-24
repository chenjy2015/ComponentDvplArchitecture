package com.example.baselib.i

interface OnBinderItemClickListener<T> {
    fun onItemClick(position: Int, t: T)
}