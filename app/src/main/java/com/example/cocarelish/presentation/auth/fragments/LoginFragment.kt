package com.example.cocarelish.presentation.auth.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentLoginBinding
import com.example.cocarelish.presentation.auth.viewmodels.LoginViewModel
import com.example.cocarelish.utils.Title
import com.example.cocarelish.utils.validate.ValidateExtend.validate
import com.example.cocarelish.utils.validate.isValidEmail
import com.example.cocarelish.utils.validate.isValidPassword
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : CommonFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_login
    private lateinit var callbackManager: CallbackManager

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFacebookLogin()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            action = viewModel
            loginByFacebookButton.setOnClickListener {
                LoginManager.getInstance().logInWithReadPermissions(
                    this@LoginFragment,
                    callbackManager,
                    arrayListOf("email", "public_profile")
                )
            }
        }

        handleTask()
    }

    private fun initFacebookLogin() {
        FacebookSdk.sdkInitialize(requireContext())
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    showToast(Title.LOGIN_CANCEL)
                }

                override fun onError(error: FacebookException) {
                    showToast(error.message ?: getString(Title.LOGIN_ERROR))
                }

                override fun onSuccess(result: LoginResult) {
                    viewModel.handleFaceBookAccessToken(result.accessToken)
                }
            })
    }

    private fun handleTask() {
        binding.loginButton.setOnClickListener {
            Log.d(TAG, "handleTask: click")
            if (binding.txtUsernameOrEmail.validate(
                    getString(R.string.error_validate_email),
                    ::isValidEmail
                ) && binding.txtPassword.validate(
                    getString(R.string.error_validate_password),
                    ::isValidPassword
                )
            )
                viewModel.login()
        }
    }
}
