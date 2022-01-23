package com.example.cocarelish.presentation.auth.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonEvent
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.authentication.remote.dto.RegisterRequest
import com.example.cocarelish.domain.auth.usecase.RegisterUseCase
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.Title
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    application: Application
) :
    CommonViewModel(application) {

    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val rePassword = MutableLiveData<String>()

    override fun onNavigate(itemTitle: Int) {
        viewModelScope.launch {
            when (itemTitle) {
                Title.AUTH_LOGIN -> evenSender.send(CommonEvent.OnNavigation(R.id.action_signUpFragment_to_loginFragment))
            }
        }
    }

    fun register() {
        Log.d(TAG, "register is click ")
        val registerRequest = RegisterRequest(userName.value!!, email.value!!, password.value!!)
        viewModelScope.launch {
            registerUseCase.execute(registerRequest).onStart {
            }.catch { exception ->
            }.collect { baseResult ->
                when (baseResult) {
                    is Resource.Success -> {
                        viewModelScope.launch {
                            evenSender.send(CommonEvent.OnShowToast("Register Successfully!"))
                        }
                        onNavigate(Title.AUTH_LOGIN)
                    }

                    is Resource.Failure -> {
                        Log.d(TAG, "register: ")
                        viewModelScope.launch {
                            evenSender.send(CommonEvent.OnShowToast("Register Successfully!"))
                        }
                        onNavigate(Title.AUTH_LOGIN)
                    }
                }
            }
        }
    }
}