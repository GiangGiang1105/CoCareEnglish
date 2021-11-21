package com.example.cocarelish.ui.home.fragments


import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseFragment
import com.example.cocarelish.databinding.FragmentProfileBinding
import com.example.cocarelish.ui.viewmodels.ProfileViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding,ProfileViewModel>(){
    override val viewModel: ProfileViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_profile
}