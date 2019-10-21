package com.example.baselib.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserVO(val name: String,val age:Int) : Parcelable