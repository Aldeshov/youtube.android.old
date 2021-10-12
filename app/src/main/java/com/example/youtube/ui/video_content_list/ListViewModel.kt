package com.example.youtube.ui.video_content_list

import androidx.lifecycle.MutableLiveData
import com.example.youtube.service.base.BaseViewModel
import com.example.youtube.service.models.LiveDataInfo
import com.example.youtube.service.models.VideoContent
import com.example.youtube.service.repositories.ContentRepository

class ListViewModel(private val contentRepository: ContentRepository) : BaseViewModel() {
    val list = MutableLiveData<ArrayList<VideoContent>>()

    fun fetchContentList() {
        status.value = LiveDataInfo.LOADING
        contentRepository.getVideoContents { isSuccess, response ->
            if (isSuccess) {
                if (response != null && response.isNotEmpty()) {
                    list.value = response
                    status.value = LiveDataInfo.LOADED_SUCCESSFUL
                }
                else
                    status.value = LiveDataInfo.LOADED_EMPTY
            } else {
                status.value = LiveDataInfo.ERROR
            }
        }
    }
}