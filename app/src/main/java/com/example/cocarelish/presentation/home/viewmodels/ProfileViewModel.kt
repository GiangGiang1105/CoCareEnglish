package com.example.cocarelish.presentation.home.viewmodels

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
import com.example.cocarelish.presentation.main.UserInformationDialog
import com.example.cocarelish.utils.MyPreference
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.Title
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

    val isNavigation = MutableLiveData(false)

    private var showUserInformationDialog: ShowUserInformationDialog? = null

    init {
        getUserInformation()
    }

    fun setShowUserInformationDialog(showUserInformationDialog: ShowUserInformationDialog?) {
        this.showUserInformationDialog = showUserInformationDialog
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

    override fun onNavigate(itemTitle: Int) {
        super.onNavigate(itemTitle)
        Log.d(TAG, "onNavigate: $itemTitle")
        viewModelScope.launch {
            when (itemTitle) {
                Title.EDIT_ACCOUNT -> {
                    evenSender.send(CommonEvent.OnNavigation(R.id.action_profileFragment_to_editUserFragment))
                }
                Title.MY_ACCOUNT -> {
                    getUserInformation()
                    showUserInformationDialog?.showDialog(true)
                }
                Title.MY_FAVOURITE -> {

                }
                Title.TITLE_MY_ESSAY -> {
                    evenSender.send(CommonEvent.OnNavigation(R.id.action_profileFragment_to_myEssayFragment))
                    isNavigation.postValue(true)
                }
                Title.MY_LOGOUT -> {
                    evenSender.send(CommonEvent.OnNavigation(R.id.action_profileFragment_to_loginFragment))
                }
            }
        }
    }

    interface ShowUserInformationDialog {
        fun showDialog(isShow: Boolean)
    }
}