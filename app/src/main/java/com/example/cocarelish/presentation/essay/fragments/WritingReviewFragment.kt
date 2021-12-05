package com.example.cocarelish.presentation.essay.fragments

import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentWritingReviewBinding
import com.example.cocarelish.presentation.essay.viewmodels.WritingReviewViewModel

class WritingReviewFragment() : CommonFragment<FragmentWritingReviewBinding, WritingReviewViewModel>(){
    override val viewModel: WritingReviewViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_writing_review
}