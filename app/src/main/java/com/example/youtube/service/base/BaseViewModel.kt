package com.example.youtube.service.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtube.service.models.LiveDataStatus

open class BaseViewModel : ViewModel() {
    val status = MutableLiveData<LiveDataStatus>().apply { value = LiveDataStatus.NOTHING }
    val message = MutableLiveData<String>()
}