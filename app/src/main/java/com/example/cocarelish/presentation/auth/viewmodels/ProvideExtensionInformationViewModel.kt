package com.example.cocarelish.presentation.auth.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonEvent
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.authentication.remote.dto.UserInfo
import com.example.cocarelish.domain.auth.usecase.LoginUseCase
import com.example.cocarelish.utils.Consts
import com.example.cocarelish.utils.MyPreference
import com.example.cocarelish.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProvideExtensionInformationViewModel @Inject constructor(
    private val myPreference: MyPreference,
    private val loginUseCase: LoginUseCase,
    application: Application
) :
    CommonViewModel(application) {

    private val _gender = MutableLiveData<Int>()
    val userName = MutableLiveData(DEFAULT_STRING)
    val email = MutableLiveData(DEFAULT_STRING)
    val gender: LiveData<Int> = _gender
    val phone = MutableLiveData(DEFAULT_STRING)
    val address = MutableLiveData(DEFAULT_STRING)

    init {
        getUserInformation()
    }

    fun setGender(value: String) {
        if (value.equals(FEMALE))
            _gender.postValue(FEMALE_VALUE)
        else _gender.postValue(MALE_VALUE)
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
                            userName.postValue(it.name)
                            email.postValue(it.email)
                            _gender.postValue(it.gender)
                            address.postValue(it.address)
                            phone.postValue(it.phone_number)
                        }
                    }
                }

            }
        }
    }


    fun providerExtensionInformation(isEditUser: Boolean) {
        viewModelScope.launch {
            UserInfo().apply {
                myPreference.getUserID().also {
                    id = it
                }
                userName.value?.let {
                    name = it
                }
                this@ProvideExtensionInformationViewModel.email.value?.let {
                    email = it
                }
                this@ProvideExtensionInformationViewModel.gender.value?.let {
                    gender = it
                }
                phone.value?.let {
                    phone_number = it
                }
                this@ProvideExtensionInformationViewModel.address.value?.let {
                    address = it
                }
                loginUseCase.setUserInformation(this).collect {
                    if (it && isEditUser) {
                        evenSender.send(CommonEvent.OnNavigation(R.id.action_editUserFragment_to_profileFragment))
                    } else if (it) {
                        evenSender.send(CommonEvent.OnNavigation(R.id.action_provideExtensionInformationFragment_to_homeFragment))
                    }
                }
            }
        }
    }

    companion object {
        const val DEFAULT_STRING = ""
        const val FEMALE = "Female"
        const val FEMALE_VALUE = 1
        const val MALE_VALUE = 0
    }
}