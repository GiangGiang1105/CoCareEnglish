package com.example.cocarelish.presentation.order.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.utils.base.CommonCollapseEssayTitle

class WritingEssayViewModel(application: Application): CommonViewModel(application), CommonCollapseEssayTitle {
    override val isCollapse = MutableLiveData(true)
}