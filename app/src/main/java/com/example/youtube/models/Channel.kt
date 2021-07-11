package com.example.youtube.models

import android.os.Parcel
import android.os.Parcelable

data class Channel(var code: String,
                   var name: String,
                   var avatar: String,
                   var is_verified: Boolean,
                   var description: String,
                   var created_date: String,
                   var subscribers: Int): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(name)
        parcel.writeString(avatar)
        parcel.writeByte(if (is_verified) 1 else 0)
        parcel.writeString(description)
        parcel.writeString(created_date)
        parcel.writeInt(subscribers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Channel> {
        override fun createFromParcel(parcel: Parcel): Channel {
            return Channel(parcel)
        }

        override fun newArray(size: Int): Array<Channel?> {
            return arrayOfNulls(size)
        }
    }

}