package com.example.cocarelish.presentation.grade.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.essay.remote.dto.Test
import com.example.cocarelish.utils.base.CommonCollapseEssayTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GradeAndJudgeViewModel @Inject constructor(application: Application) :
    CommonViewModel(application), CommonCollapseEssayTitle {

    override val isCollapse: MutableLiveData<Boolean>
        get() = MutableLiveData(true)
    override val detailEssay: LiveData<Test>
        get() = MutableLiveData(Test())

    override fun changeStateCollapseView() {
        super.changeStateCollapseView()
        Log.d("TAGG", "changeStateCollapseView: ")
    }
}