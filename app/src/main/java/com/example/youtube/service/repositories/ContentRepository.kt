package com.example.youtube.service.repositories

import com.example.youtube.service.models.api.content.VideoContent

interface ContentRepository {
    fun getVideoContents(
        onResult: (isSuccess: Boolean, response: ArrayList<VideoContent>?) -> Unit
    )
}