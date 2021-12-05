package com.example.cocarelish.presentation.essay.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentLevelBinding
import com.example.cocarelish.presentation.essay.viewmodels.LevelViewModel
import com.example.cocarelish.utils.listTemplate.MenuAdapter

class LevelFragment : CommonFragment<FragmentLevelBinding, LevelViewModel>() {
    override val viewModel: LevelViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_level

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuAdapter = MenuAdapter(viewModel)
        binding.apply {
            menuAdapter.submitList(viewModel.listMenuItem)
            recyclerView.adapter = menuAdapter
        }
    }

}