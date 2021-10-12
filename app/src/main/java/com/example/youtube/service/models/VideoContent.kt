package com.example.youtube.service.models

import android.os.Parcel
import android.os.Parcelable
import com.example.youtube.service.models.additional.Copyright

data class VideoContent(var code: String,
                        var title: String,
                        var created_on: String,
                        var video: String,
                        var views: String,
                        var likes: Int,
                        var dislikes: Int,
                        var preview: String,
                        var type: Int,
                        var description: String,
                        var on_channel: Channel,
                        var copyrights: Copyright
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(Channel::class.java.classLoader)!!,
        parcel.readParcelable(Copyright::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(title)
        parcel.writeString(created_on)
        parcel.writeString(video)
        parcel.writeString(views)
        parcel.writeInt(likes)
        parcel.writeInt(dislikes)
        parcel.writeString(preview)
        parcel.writeInt(type)
        parcel.writeString(description)
        parcel.writeParcelable(on_channel, flags)
        parcel.writeParcelable(copyrights, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoContent> {
        override fun createFromParcel(parcel: Parcel): VideoContent {
            return VideoContent(parcel)
        }

        override fun newArray(size: Int): Array<VideoContent?> {
            return arrayOfNulls(size)
        }
    }
}