package com.example.cocarelish.presentation.essay.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentLevelBinding
import com.example.cocarelish.presentation.essay.viewmodels.EssayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelFragment : CommonFragment<FragmentLevelBinding, EssayViewModel>() {
    override val viewModel: EssayViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_level

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
            imageResource = R.drawable.ic_back
        }
    }
}