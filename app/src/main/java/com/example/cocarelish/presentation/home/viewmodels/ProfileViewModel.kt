package com.example.cocarelish.presentation.home.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.essay.remote.dto.User
import com.example.cocarelish.domain.auth.usecase.UserUseCase
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.listTemplate.ItemListModel
import com.example.cocarelish.utils.listTemplate.ItemListType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    application: Application
) : CommonViewModel(application) {

    private val _userInformation: MutableLiveData<User> = MutableLiveData()
    val userInformation: LiveData<User>
        get() = _userInformation

    fun getUserInformation(user_id: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                userUseCase.execute(user_id).onStart {
                    Log.d(TAG, "getUserInformation: onStart")
                    showLoadingDialog(true)
                }.catch { exception ->
                    showLoadingDialog(false)
                    Log.d(TAG, "getUserInformation: error with exception: $exception")
                }.collect { baseResult ->
                    Log.d(TAG, "getUserInformation: true")
                    showLoadingDialog(false)
                    Log.d(TAG, "getUserInformation: success with data = $baseResult")
                    when (baseResult) {
                        is Resource.Success -> {
                            _userInformation.postValue(baseResult.value.users[0])
                        }
                    }
                }
            }
        }
    }
}