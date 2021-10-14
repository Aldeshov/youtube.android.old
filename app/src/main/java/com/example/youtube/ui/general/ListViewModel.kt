package com.example.youtube.ui.general

import androidx.lifecycle.MutableLiveData
import com.example.youtube.service.base.BaseViewModel
import com.example.youtube.service.models.LiveDataStatus
import com.example.youtube.service.models.api.content.VideoContent
import com.example.youtube.service.repositories.ContentRepository

class ListViewModel(private val contentRepository: ContentRepository) : BaseViewModel() {
    val list = MutableLiveData<ArrayList<VideoContent>>()

    fun fetchContentList() {
        status.value = LiveDataStatus.LOADING
        contentRepository.getVideoContents { isSuccess, response ->
            if (isSuccess) {
                if (response != null && response.isNotEmpty()) {
                    list.value = response
                    status.value = LiveDataStatus.LOADED_SUCCESSFUL
                }
                else
                    status.value = LiveDataStatus.LOADED_EMPTY
            } else {
                status.value = LiveDataStatus.ERROR
            }
        }
    }
}