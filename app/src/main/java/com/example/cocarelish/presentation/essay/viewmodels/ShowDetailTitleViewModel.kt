package com.example.cocarelish.presentation.essay.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.utils.base.CommonCollapseEssayTitle

class ShowDetailTitleViewModel(application: Application): CommonViewModel(application), CommonCollapseEssayTitle{
    override var isCollapse = MutableLiveData(false)
        private set


}