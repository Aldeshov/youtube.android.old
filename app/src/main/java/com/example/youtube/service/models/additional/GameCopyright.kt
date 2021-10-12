package com.example.youtube.service.models.additional

import android.os.Parcel
import android.os.Parcelable

data class GameCopyright(var key: Int,
                         var is_allowable: Boolean,
                         var accept_monetization: Boolean,
                         var tags: ArrayList<String> = ArrayList<String>(),
                         var name: String,
                         var description: String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        arrayListOf<String>().apply {
            parcel.readArrayList(String::class.java.classLoader)
        },
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(key)
        parcel.writeByte(if (is_allowable) 1 else 0)
        parcel.writeByte(if (accept_monetization) 1 else 0)
        parcel.writeList(tags)
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameCopyright> {
        override fun createFromParcel(parcel: Parcel): GameCopyright {
            return GameCopyright(parcel)
        }

        override fun newArray(size: Int): Array<GameCopyright?> {
            return arrayOfNulls(size)
        }
    }
}