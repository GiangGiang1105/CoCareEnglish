package com.example.cocarelish.presentation.essay.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentGeneralEssayBinding
import com.example.cocarelish.presentation.essay.viewmodels.GeneralEssayViewModel
import com.example.cocarelish.utils.Title
import com.example.cocarelish.utils.listTemplate.MenuAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralEssayFragment : CommonFragment<FragmentGeneralEssayBinding, GeneralEssayViewModel>() {
    override val viewModel: GeneralEssayViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_general_essay

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuAdapter = MenuAdapter(viewModel)
        menuAdapter.submit(viewModel.listData)
        binding.apply {
            action = viewModel
            imageResource = R.drawable.ic_back
            generalEssayRecyclerView.apply {
                adapter = menuAdapter
            }
        }
    }
}