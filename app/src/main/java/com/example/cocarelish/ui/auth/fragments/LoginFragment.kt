package com.example.cocarelish.ui.auth.fragments

import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseFragment
import com.example.cocarelish.databinding.FragmentLoginBinding
import com.example.cocarelish.ui.auth.viewmodels.LoginViewModel
import com.example.cocarelish.ui.home.HomeActivity

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_login

    override fun handleTasks() {
        TODO("Not yet implemented")
    }
}