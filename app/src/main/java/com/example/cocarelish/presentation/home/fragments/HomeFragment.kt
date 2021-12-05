package com.example.cocarelish.presentation.home.fragments

import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentHomeBinding
import com.example.cocarelish.presentation.home.viewmodels.HomeViewModel

class HomeFragment : CommonFragment<FragmentHomeBinding, HomeViewModel>(){

    override val viewModel: HomeViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_home
}