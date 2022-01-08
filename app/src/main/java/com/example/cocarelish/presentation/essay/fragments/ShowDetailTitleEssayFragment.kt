package com.example.cocarelish.presentation.essay.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentShowDetailEssayTitleBinding
import com.example.cocarelish.presentation.essay.viewmodels.EssayViewModel
import com.example.cocarelish.presentation.essay.viewmodels.ShowDetailTitleViewModel

class ShowDetailTitleEssayFragment :
    CommonFragment<FragmentShowDetailEssayTitleBinding, EssayViewModel>() {

    override val viewModel: EssayViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_show_detail_essay_title
    private val idEssay by lazy {
        arguments?.getInt(ARG_ID_ESSAY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idEssay?.let { viewModel.getDetailTest(it) }
        binding.apply {
            action = viewModel
            viewModel.levelName.observe(viewLifecycleOwner) {
                title = it
            }
        }
    }

    companion object {
        const val ARG_ID_ESSAY = "IdEssay"
    }
}