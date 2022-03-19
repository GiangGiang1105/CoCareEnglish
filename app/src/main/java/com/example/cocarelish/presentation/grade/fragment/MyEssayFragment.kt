package com.example.cocarelish.presentation.grade.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentMyEssayBinding
import com.example.cocarelish.presentation.grade.viewmodel.MyEssayViewModel
import com.example.cocarelish.utils.MyPreference
import com.example.cocarelish.utils.listTemplate.MenuAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyEssayFragment : CommonFragment<FragmentMyEssayBinding, MyEssayViewModel>() {
    override val viewModel: MyEssayViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_my_essay

    @Inject
    lateinit var myPreference: MyPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuAdapter = MenuAdapter(viewModel)
        viewModel.listData.observe(viewLifecycleOwner) {
            menuAdapter.submit(it)
        }
        binding.apply {
            action = viewModel
            imageResource = R.drawable.ic_back
            myEssayRecyclerView.adapter = menuAdapter
        }

    }
}