package com.example.cocarelish.presentation.essay.viewmodels.dialog

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.utils.MyPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageFullScreenViewModel @Inject constructor(
    myPreference: MyPreference,
    application: Application
    ): CommonViewModel(application){
    val imageLink = MutableLiveData(myPreference.getImageLink())
}