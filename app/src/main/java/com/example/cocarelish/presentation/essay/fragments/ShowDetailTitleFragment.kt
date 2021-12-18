package com.example.cocarelish.presentation.essay.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentShowDetailTitleBinding
import com.example.cocarelish.presentation.essay.viewmodels.ShowDetailTitleViewModel

class ShowDetailTitleFragment : CommonFragment<FragmentShowDetailTitleBinding, ShowDetailTitleViewModel>() {
    override val viewModel: ShowDetailTitleViewModel by viewModels()
    override val layoutID: Int
    get() = R.layout.fragment_show_detail_title

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            title = "abc"
        }
    }
}