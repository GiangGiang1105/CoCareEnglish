package com.example.cocarelish.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    var eventLoading = MutableLiveData<Event<Boolean>>()
        private set
    var eventError = MutableLiveData<Event<Boolean>>()
        private set

    fun showLoading(value: Boolean){
        eventLoading.value = Event(value)
    }

    fun showError(errorString: Boolean){
        eventError.value = Event(errorString)
    }
}