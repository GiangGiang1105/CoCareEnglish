package com.example.cocarelish.presentation.home.fragments

import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentEssayBinding
import com.example.cocarelish.presentation.home.viewmodels.EssayViewModel

class EssayFragment :CommonFragment<FragmentEssayBinding, EssayViewModel>(){
    override val viewModel: EssayViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_essay
}