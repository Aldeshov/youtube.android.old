package com.example.youtube.models.additional

import android.os.Parcel
import android.os.Parcelable

data class SongCopyright(var key: Int,
                         var is_allowable: Boolean,
                         var accept_monetization: Boolean,
                         var tags: ArrayList<String> = ArrayList<String>(),
                         var song: String,
                         var artist: String,
                         var album: String,
                         var licensed_to: String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        arrayListOf<String>().apply {
            parcel.readArrayList(String::class.java.classLoader)
        },
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(key)
        parcel.writeByte(if (is_allowable) 1 else 0)
        parcel.writeByte(if (accept_monetization) 1 else 0)
        parcel.writeList(tags)
        parcel.writeString(song)
        parcel.writeString(artist)
        parcel.writeString(album)
        parcel.writeString(licensed_to)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongCopyright> {
        override fun createFromParcel(parcel: Parcel): SongCopyright {
            return SongCopyright(parcel)
        }

        override fun newArray(size: Int): Array<SongCopyright?> {
            return arrayOfNulls(size)
        }
    }
}