package com.android.demo.aidl

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import java.util.Objects

open class MyData : Parcelable {
    var data1: Int = 0
    var data2: Int = 0

    constructor()

    protected constructor(parcel: Parcel) {
        readFromParcel(parcel)
    }

    override fun describeContents(): Int {
        return 0
    }

    /** 将数据写入到Parcel  */
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(data1)
        dest.writeInt(data2)
    }

    /** 从Parcel中读取数据  */
    private fun readFromParcel(parcel: Parcel) {
        data1 = parcel.readInt()
        data2 = parcel.readInt()
    }

    override fun toString(): String {
        return "${System.identityHashCode(this)} data1 = $data1, data2=$data2"
    }

    companion object {
        @JvmField
        val CREATOR: Creator<MyData> = object : Creator<MyData> {
            override fun createFromParcel(parcel: Parcel): MyData {
                return MyData(parcel)
            }

            override fun newArray(size: Int): Array<MyData?> {
                return arrayOfNulls(size)
            }
        }
    }
}