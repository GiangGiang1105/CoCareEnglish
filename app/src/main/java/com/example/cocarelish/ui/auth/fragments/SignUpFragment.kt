package com.example.cocarelish.ui.auth.fragments

import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseFragment
import com.example.cocarelish.databinding.FragmentSignUpBinding
import com.example.cocarelish.ui.auth.viewmodels.SignUpViewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding,SignUpViewModel>(){

    override val viewModel: SignUpViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_sign_up
}