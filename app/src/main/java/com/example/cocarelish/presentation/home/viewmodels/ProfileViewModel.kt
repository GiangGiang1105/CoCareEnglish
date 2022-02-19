package com.example.cocarelish.presentation.home.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.authentication.remote.dto.UserInfo
import com.example.cocarelish.domain.auth.usecase.LoginUseCase
import com.example.cocarelish.utils.MyPreference
import com.example.cocarelish.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    application: Application,
    val myPreference: MyPreference
) : CommonViewModel(application) {

    private val _userInformation: MutableLiveData<UserInfo> = MutableLiveData(UserInfo())
    val userInformation: LiveData<UserInfo>
        get() = _userInformation

    init {
        getUserInformation()
    }

    private fun getUserInformation() {
        viewModelScope.launch {
            viewModelScope.launch {
                Log.d(TAG, "getUserInformation: ${myPreference.getUserID()}")
                loginUseCase.getUserInformation(myPreference.getUserID()).onStart {
                    showLoadingDialog(true)
                }.collect { data ->
                    showLoadingDialog(false)
                    if (data is Resource.Success) {
                        data.value?.let {
                            Log.d(TAG, "getUserInformation: userData -- $it")
                            _userInformation.value = it
                        }
                    }
                }

            }
        }
    }
}