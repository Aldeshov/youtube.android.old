package com.example.youtube.service.repositories

import com.example.youtube.service.models.VideoContent

interface ContentRepository {
    fun getVideoContents(onResult: (isSuccess: Boolean, response: ArrayList<VideoContent>?) -> Unit)
}