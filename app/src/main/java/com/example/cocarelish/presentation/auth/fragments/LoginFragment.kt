package com.example.cocarelish.presentation.auth.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseFragment
import com.example.cocarelish.databinding.FragmentLoginBinding
import com.example.cocarelish.presentation.auth.viewmodels.LoginViewModel
import com.example.cocarelish.utils.validate.ValidateExtend.validate
import com.example.cocarelish.utils.validate.isValidEmail
import com.example.cocarelish.utils.validate.isValidPassword
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
        }

        handleTask()
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
