package com.example.cocarelish.presentation.essay.fragments

import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentWritingEssayBinding
import com.example.cocarelish.presentation.order.viewmodels.WritingEssayViewModel

class WritingEssayFragment: CommonFragment<FragmentWritingEssayBinding,WritingEssayViewModel>() {
    override val viewModel: WritingEssayViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_writing_essay
}