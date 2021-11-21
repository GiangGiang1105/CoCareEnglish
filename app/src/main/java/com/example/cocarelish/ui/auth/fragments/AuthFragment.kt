package com.example.cocarelish.ui.auth.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseFragment
import com.example.cocarelish.databinding.FragmentAuthBinding
import com.example.cocarelish.ui.auth.viewmodels.AuthViewModel

class AuthFragment : BaseFragment<FragmentAuthBinding,AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
        }
    }
}