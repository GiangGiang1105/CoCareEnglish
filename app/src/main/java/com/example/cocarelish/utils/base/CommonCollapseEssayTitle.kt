package com.example.cocarelish.utils.base

import androidx.lifecycle.MutableLiveData

interface CommonCollapseEssayTitle {
    val isCollapse: MutableLiveData<Boolean>
    fun changeStateCollapseView(){
        isCollapse.value = isCollapse.value?.not()
    }
}