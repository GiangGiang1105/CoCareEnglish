package com.example.cocarelish.presentation.auth.viewmodels

import android.app.Application
import android.service.quicksettings.Tile
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonEvent
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.domain.auth.usecase.LoginUseCase
import com.example.cocarelish.utils.CoCareLishPrefence
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.Title
import com.example.cocarelish.utils.Title.HOME
import com.example.cocarelish.utils.Title.LOGIN_SUCCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: LoginUseCase,
    application: Application
) : CommonViewModel(application) {

    val userName = MutableLiveData<String>("")
    val passWord = MutableLiveData<String>("")
    private val myApplication = application

    override fun onNavigate(itemTitle: Int) {
        viewModelScope.launch {
            when (itemTitle) {
                Title.AUTH_SIGN_UP -> evenSender.send(CommonEvent.OnNavigation(R.id.action_loginFragment_to_signUpFragment))
                Title.HOME -> evenSender.send(CommonEvent.OnNavigation(R.id.action_loginFragment_to_homeFragment))
            }
        }
    }

    fun login() {

        val loginRequest = LoginRequest(userName.value ?: "", passWord.value ?: "")

        viewModelScope.launch {
            authUseCase.execute(loginRequest).onStart {
                showLoadingDialog(true)
            }.catch { exeption ->
                showLoadingDialog(false)
                Log.d(TAG, "Login Extension Error: ${exeption.message}")
            }.collect { baseResult ->
                showLoading(false)
                when (baseResult) {
                    is Resource.Success -> {
                        showToast(LOGIN_SUCCESS)
                        showLoadingDialog(false)
                        Log.e(TAG, "login: with id ${baseResult.value.user_info.id}", )
                        CoCareLishPrefence(myApplication.baseContext).apply {
                            init()
                            putIdUser(baseResult.value.user_info.id)
                        }
                        onNavigate(HOME)
                    }
                }
            }
        }
    }
}