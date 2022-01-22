package com.example.cocarelish.presentation.home.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentHomeBinding
import com.example.cocarelish.presentation.home.adapters.MissionAdapter
import com.example.cocarelish.presentation.home.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : CommonFragment<FragmentHomeBinding, HomeViewModel>(){

    override val viewModel: HomeViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_home
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val missionAdapter = MissionAdapter(viewModel)
        binding.apply {
            subHomeWritting.missionRecyclerView.apply {
                adapter = missionAdapter
                layoutManager = LinearLayoutManager(this@HomeFragment.requireContext(), LinearLayoutManager.VERTICAL,false)
            }
        }

        lifecycleScope.launch {
            viewModel.getAllMission().observe(viewLifecycleOwner,{
                Log.d(TAG, "onViewCreated: $it")
                missionAdapter.submitList(it)
            })
        }
    }
}