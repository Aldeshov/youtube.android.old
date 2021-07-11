package com.example.youtube.models.additional

import android.os.Parcel
import android.os.Parcelable

data class Copyright(var is_private: Boolean,
                     var is_adult_content: Boolean,
                     var is_kids_content: Boolean,
                     var song_copyrights: ArrayList<SongCopyright> = ArrayList<SongCopyright>(),
                     var game_copyrights: ArrayList<GameCopyright> = ArrayList<GameCopyright>()): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        arrayListOf<SongCopyright>().apply {
            parcel.readArrayList(SongCopyright::class.java.classLoader)
        },
        arrayListOf<GameCopyright>().apply {
            parcel.readArrayList(GameCopyright::class.java.classLoader)
        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (is_private) 1 else 0)
        parcel.writeByte(if (is_adult_content) 1 else 0)
        parcel.writeByte(if (is_kids_content) 1 else 0)
        parcel.writeList(song_copyrights)
        parcel.writeList(game_copyrights)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Copyright> {
        override fun createFromParcel(parcel: Parcel): Copyright {
            return Copyright(parcel)
        }

        override fun newArray(size: Int): Array<Copyright?> {
            return arrayOfNulls(size)
        }
    }

}