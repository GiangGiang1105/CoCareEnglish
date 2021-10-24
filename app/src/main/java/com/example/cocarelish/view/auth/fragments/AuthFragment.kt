package com.example.cocarelish.view.auth.fragments

import com.example.cocarelish.R
import com.example.cocarelish.base.BaseFragment
import com.example.cocarelish.databinding.FragmentAuthBinding

class AuthFragment : BaseFragment<FragmentAuthBinding>(R.layout.fragment_auth) {
    override fun handleTasks() {
        binding.switchAuthLogin.setOnClickListener {
            val actionSwitchAuthLogin = AuthFragmentDirections.actionAuthFragmentToLoginFragment()
            controller.navigate(actionSwitchAuthLogin)
        }

        binding.switchAuthSignUp.setOnClickListener {
            val actionSwitchAuthSignUp = AuthFragmentDirections.actionAuthFragmentToSignUpFragment()
            controller.navigate(actionSwitchAuthSignUp)
        }
    }

}