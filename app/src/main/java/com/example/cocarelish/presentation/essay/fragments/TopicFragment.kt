package com.example.cocarelish.presentation.essay.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentTopicBinding
import com.example.cocarelish.presentation.essay.viewmodels.EssayViewModel
import com.example.cocarelish.utils.Title
import com.example.cocarelish.utils.listTemplate.MenuAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopicFragment : CommonFragment<FragmentTopicBinding, EssayViewModel>() {

    override val viewModel: EssayViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_topic
    private val idTopic by lazy { arguments?.getInt(ARG_ID_TOPIC) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuAdapter = MenuAdapter(viewModel)
        idTopic?.let {
            Log.d(TAG, "onViewCreated: ")
            viewModel.getAllTopics(it)
        }
        binding.apply {
            recyclerView.adapter = menuAdapter
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            action = viewModel
            title = Title.IELTS_WRITING_TASK_1

        }
        menuAdapter.setOnClickListener {
            Log.e(TAG, "onViewCreated:${it.id} ")
        }
        viewModel.listData.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: $it")
            menuAdapter.submit(it)
        }
    }

    companion object {
        const val ARG_ID_TOPIC = "IdTopic"
    }
}