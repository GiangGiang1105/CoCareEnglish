package com.example.cocarelish.presentation.order.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.utils.base.CommonCollapseEssayTitle

class WritingEssayViewModel(application: Application) : CommonViewModel(application),
    CommonCollapseEssayTitle {
    override val isCollapse = MutableLiveData(true)
    val isRecording = MutableLiveData(false)
    val recordText = MutableLiveData("")

    fun imgRecordStateClick(){
        Log.d("TAGG", "imgRecordStateClick: ")
        isRecording.value = isRecording.value?.not()
    }

}