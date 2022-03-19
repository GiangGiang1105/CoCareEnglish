package com.example.cocarelish.presentation.auth.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonEvent
import com.example.cocarelish.base.CommonViewModel
import com.example.cocarelish.data.authentication.remote.dto.LoginRequest
import com.example.cocarelish.data.authentication.remote.dto.UserInfo
import com.example.cocarelish.domain.auth.usecase.LoginUseCase
import com.example.cocarelish.utils.MyPreference
import com.example.cocarelish.utils.Resource
import com.example.cocarelish.utils.Title
import com.example.cocarelish.utils.Title.LOGIN_SUCCESS
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: LoginUseCase,
    private val myPreference: MyPreference,
    private val auth: FirebaseAuth,
    application: Application
) : CommonViewModel(application) {

    var firebaseUser: FirebaseUser? = null
    private var userID: String? = null

    val userName = MutableLiveData(DEFAULT_STRING)
    val passWord = MutableLiveData(DEFAULT_STRING)

    override fun onNavigate(itemTitle: Int) {
        viewModelScope.launch {
            when (itemTitle) {
                Title.AUTH_SIGN_UP -> evenSender.send(CommonEvent.OnNavigation(R.id.action_loginFragment_to_signUpFragment))
                Title.HOME_FRAGMENT -> evenSender.send(CommonEvent.OnNavigation(R.id.action_loginFragment_to_homeFragment))
                Title.TITLE_PROVIDE_EXTENSION_INFORMATION -> evenSender.send(
                    CommonEvent.OnNavigation(
                        R.id.action_loginFragment_to_provideExtensionInformationFragment
                    )
                )
            }
        }
    }

    fun login() {
        val loginRequest = LoginRequest(userName.value ?: "", passWord.value ?: "")

        auth.signInWithEmailAndPassword(loginRequest.email, loginRequest.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    firebaseUser = auth.currentUser

                    userID = firebaseUser?.uid

                    userID?.let { userID ->
                        Log.d(TAG, "handleFaceBookAccessToken: userID -- $userID")
                        myPreference.saveUserID(userID)
                        coroutine.launch {
                            val a = authUseCase.getUserInformation(userID)
                            Log.d(TAG, "handleFaceBookAccessToken: user information -- $a")

                            // fake data
                            val userInfor = UserInfo(
                                address = "Quảng Bình",
                                name = "Nam",
                                id = userID
                            )
                            val b = authUseCase.setUserInformation(userInfor)
                            Log.d(TAG, "handleFaceBookAccessToken: $b")
                        }
                    }
                    // TODO: Do something when login successfully
                    onNavigate(Title.HOME_FRAGMENT)
                    showToast(LOGIN_SUCCESS)
                } else {
                    // If sign in fails, display a message to the user.
                    showToast(Title.LOGIN_ERROR)
                }
            }
    }

    var coroutine = CoroutineScope(Dispatchers.IO)

    fun handleFaceBookAccessToken(token: AccessToken) {
        viewModelScope.launch {
            val credential = FacebookAuthProvider.getCredential(token.token)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    firebaseUser = auth.currentUser

                    userID = firebaseUser?.uid

                    userID?.let { userID ->
                        Log.d(TAG, "handleFaceBookAccessToken: userID -- $userID")
                        myPreference.saveUserID(userID)
                        coroutine.launch {
                            val a = authUseCase.getUserInformation(userID)
                            Log.d(TAG, "handleFaceBookAccessToken: user information -- $a")

                            // fake data
                            val userInfor = UserInfo(
                                address = "Quảng Bình",
                                name = "Nam",
                                id = userID
                            )
                            val b = authUseCase.setUserInformation(userInfor)
                            Log.d(TAG, "handleFaceBookAccessToken: $b")
                        }
                    }
                    // TODO: Do something when login successfully
                    onNavigate(Title.TITLE_PROVIDE_EXTENSION_INFORMATION)
                    showToast(LOGIN_SUCCESS)
                } else {
                    // If sign in fails, display a message to the user.
                    showToast(Title.LOGIN_ERROR)
                }
            }
        }
    }


    private fun getUserInformation() {

    }

    companion object {
        const val DEFAULT_STRING = ""
    }
}