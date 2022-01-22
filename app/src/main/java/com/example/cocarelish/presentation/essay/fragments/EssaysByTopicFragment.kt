package com.example.cocarelish.presentation.essay.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentEssaysByTopicBinding
import com.example.cocarelish.presentation.essay.viewmodels.EssayViewModel
import com.example.cocarelish.utils.listTemplate.MenuAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EssaysByTopicFragment :
    CommonFragment<FragmentEssaysByTopicBinding, EssayViewModel>() {

    private val idTopic by lazy { arguments?.getInt(ARG_ID_TOPIC) }
    private val nameTopic by lazy { arguments?.getString(ARG_NAME_TOPIC) }

    override val viewModel: EssayViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_essays_by_topic

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuAdapter = MenuAdapter(viewModel)
        idTopic?.let {
            Log.d(TAG, "onViewCreated: ")
            viewModel.getAllTestByTopic(it)

        }

        binding.apply {
            recyclerView.adapter = menuAdapter
            action = viewModel
            title = nameTopic

        }
        viewModel.listData.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: $it")
            menuAdapter.submit(it)
        }
    }

    override fun onDestroy() {
        viewModel.addTopicSource()
        super.onDestroy()
    }

    companion object {
        const val ARG_ID_TOPIC = "IdTopic"
        const val ARG_NAME_TOPIC = "NameTopic"
    }
}