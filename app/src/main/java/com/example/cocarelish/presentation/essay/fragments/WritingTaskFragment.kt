package com.example.cocarelish.presentation.essay.fragments

import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.BaseFragment
import com.example.cocarelish.databinding.FragmentWrittingTaskBinding
import com.example.cocarelish.presentation.essay.viewmodels.WritingTaskViewModel

class WritingTaskFragment :
    BaseFragment<FragmentWrittingTaskBinding, WritingTaskViewModel>() {
    override val viewModel: WritingTaskViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_writting_task
}