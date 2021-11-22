package com.example.cocarelish.ui.auth.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonEvent
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.domain.auth.usecase.AuthenticatioUsecase
import com.example.cocarelish.utils.Title
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authenticatioUsecase: AuthenticatioUsecase) : CommonViewModel() {
    override fun onNavigate(itemTitle: Int) {
        Log.d(TAG, "onNavigate: ViewModel Navigate Onclick")
        viewModelScope.launch {
            when(itemTitle){
                Title.AUTH_SIGN_UP -> evenSender.send(CommonEvent.OnNavigation(R.id.action_authFragment_to_signUpFragment))
                Title.AUTH_LOGIN -> evenSender.send(CommonEvent.OnNavigation(R.id.action_authFragment_to_loginFragment))
            }
        }
    }
}