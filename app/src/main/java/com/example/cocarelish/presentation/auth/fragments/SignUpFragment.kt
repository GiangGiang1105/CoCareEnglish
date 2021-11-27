package com.example.cocarelish.presentation.auth.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.utils.validate.ValidateExtend.validate
import com.example.cocarelish.base.BaseFragment
import com.example.cocarelish.databinding.FragmentSignUpBinding
import com.example.cocarelish.presentation.auth.viewmodels.SignUpViewModel
import com.example.cocarelish.utils.validate.ValidateExtend.validateSamePassword
import com.example.cocarelish.utils.validate.isValidEmail
import com.example.cocarelish.utils.validate.isValidPassword
import com.example.cocarelish.utils.validate.isValidateName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {

    override val viewModel: SignUpViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_sign_up

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
        }

        handleTask()
    }

    private fun handleTask() {
        binding.apply {
            signUpButton.setOnClickListener {
                Log.d(TAG, "handleTask: onClick")
                if (txtUsername.validate(
                        getString(R.string.error_validate_username),
                        ::isValidateName
                    ) && txtEmail.validate(
                        getString(R.string.error_validate_email),
                        ::isValidEmail
                    ) && txtPassword.validate(
                        getString(R.string.error_validate_password),
                        ::isValidPassword
                    ) &&
                    txtRePassword.validateSamePassword("loi", txtPassword.text.toString())
                ) {

                    Log.d(TAG, "handleTask: true with password : ${txtPassword.text} && repassword : ${txtRePassword.text}")
                    viewModel.register()
                }
                else Log.d(TAG, "handleTask: false")
            }
        }
    }

//đi ngủ sáng dậy sớm
}