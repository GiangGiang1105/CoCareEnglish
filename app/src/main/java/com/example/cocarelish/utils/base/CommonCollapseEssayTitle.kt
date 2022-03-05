package com.example.cocarelish.utils.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cocarelish.data.essay.remote.dto.Test

interface CommonCollapseEssayTitle {
    val isCollapse: MutableLiveData<Boolean>
    val detailEssay: LiveData<Test>

    fun changeStateCollapseView(){
        isCollapse.value = isCollapse.value?.not()
        Log.d("TAG", "changeStateCollapseView: ${isCollapse.value}")
    }
}