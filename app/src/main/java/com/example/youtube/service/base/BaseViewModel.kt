package com.example.youtube.service.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtube.service.models.LiveDataInfo

open class BaseViewModel : ViewModel() {
    val status = MutableLiveData<LiveDataInfo>().apply { value = LiveDataInfo.NOTHING }
    val message = MutableLiveData<String>()
}