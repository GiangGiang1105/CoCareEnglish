package com.example.cocarelish.ui.auth.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonEvent
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.domain.auth.usecase.AuthenticatioUsecase
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.Title
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authUsecase: AuthenticatioUsecase): CommonViewModel() {

    val userName = MutableLiveData<String>("admin@email.com")
    val passWord = MutableLiveData<String>("12345678")

    override fun onNavigate(itemTitle: Int) {
       viewModelScope.launch {
           when(itemTitle){
               Title.AUTH_SIGN_UP -> evenSender.send(CommonEvent.OnNavigation(R.id.action_loginFragment_to_signUpFragment))
           }
       }
    }

    fun login(){
        Log.d(TAG, "login: click")
        val loginRequest = LoginRequest(userName.value!!, passWord.value!!)

        viewModelScope.launch {
            authUsecase.execute(loginRequest).onStart {
                // loading
            }.catch {
                exeption -> //hideLoading
            }.collect {
                baseResult ->
                when(baseResult){
                    is Resource.Success -> Log.d(TAG, "login: nào")
                }
            }
        }

    }
}