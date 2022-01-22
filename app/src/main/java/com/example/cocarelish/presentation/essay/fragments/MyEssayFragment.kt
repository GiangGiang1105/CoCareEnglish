package com.example.cocarelish.presentation.essay.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentMyEssayBinding
import com.example.cocarelish.presentation.essay.viewmodels.MyEssayViewModel
import com.example.cocarelish.utils.CoCareLishPrefence
import com.example.cocarelish.utils.listTemplate.MenuAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyEssayFragment : CommonFragment<FragmentMyEssayBinding, MyEssayViewModel>() {
    override val viewModel: MyEssayViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_my_essay

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            CoCareLishPrefence(it).apply {
                init()
                viewModel.getAllEssayOfUser(getIdUser())
            }
        }
        val menuAdapter = MenuAdapter(viewModel)
        viewModel.listData.observe(viewLifecycleOwner, {
            menuAdapter.submit(it)
        })
        binding.apply {
            action = viewModel
            imageResource = R.drawable.ic_back
            myEssayRecyclerView.adapter = menuAdapter
        }
    }
}