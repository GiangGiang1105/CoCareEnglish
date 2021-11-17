package com.example.cocarelish.ui.home.fragments

import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseFragment
import com.example.cocarelish.databinding.FragmentEssayBinding
import com.example.cocarelish.ui.viewmodels.EssayViewModel

class EssayFragment :BaseFragment<FragmentEssayBinding, EssayViewModel>(){
    override val viewModel: EssayViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_essay
    override fun handleTasks() {
        // TODO
    }
}