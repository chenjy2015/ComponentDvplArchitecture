package com.example.baselib.bean

import android.os.Parcel
import android.os.Parcelable

class UserVO() : Parcelable {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var name: String = ""
    var age: Int = 0

    constructor(name: String, age: Int) : this() {
        this.name = name
        this.age = age
    }


    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        age = parcel.readInt()
    }

    companion object CREATOR : Parcelable.Creator<UserVO> {
        override fun createFromParcel(parcel: Parcel): UserVO {
            return UserVO(parcel)
        }

        override fun newArray(size: Int): Array<UserVO?> {
            return arrayOfNulls(size)
        }
    }
}