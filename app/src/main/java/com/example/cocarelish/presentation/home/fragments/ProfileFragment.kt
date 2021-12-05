package com.example.cocarelish.presentation.home.fragments


import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentProfileBinding
import com.example.cocarelish.presentation.home.viewmodels.ProfileViewModel

class ProfileFragment : CommonFragment<FragmentProfileBinding,ProfileViewModel>(){
    override val viewModel: ProfileViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_profile
}