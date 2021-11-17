package com.example.cocarelish.ui.auth.fragments

import com.example.cocarelish.R
import com.example.cocarelish.base.BaseFragment
import com.example.cocarelish.databinding.FragmentLoginBinding
import com.example.cocarelish.ui.home.HomeActivity

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun handleTasks() {
        changeScreen()
    }

    private fun changeScreen() {
        binding.apply {
            loginButton.setOnClickListener {
                HomeActivity.start(requireContext())
            }
            signUpButton.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
                controller.navigate(action)
            }
            forgotPasswordButton.setOnClickListener {}
            loginByFacebookButton.setOnClickListener { }
            loginByGoogleButton.setOnClickListener { }
        }
    }

}