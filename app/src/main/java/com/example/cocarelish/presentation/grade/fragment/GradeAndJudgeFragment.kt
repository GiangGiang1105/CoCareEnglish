package com.example.cocarelish.presentation.grade.fragment

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentGradeAndJudgeBinding
import com.example.cocarelish.presentation.grade.adapter.GradeAdapter
import com.example.cocarelish.presentation.grade.viewmodel.GradeAndJudgeViewModel
import com.example.cocarelish.utils.Consts.PAGE_0
import com.example.cocarelish.utils.Consts.PAGE_1
import com.example.cocarelish.utils.Consts.PAGE_2
import com.example.cocarelish.utils.Title
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GradeAndJudgeFragment :
    CommonFragment<FragmentGradeAndJudgeBinding, GradeAndJudgeViewModel>() {
    override val viewModel: GradeAndJudgeViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_grade_and_judge

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
            yourContent.movementMethod = ScrollingMovementMethod()
            viewPager.adapter = activity?.let { GradeAdapter(it) }
        }
        handleTabWithViewPager()
    }

    private fun handleTabWithViewPager() {
        binding.apply {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when(position){
                    PAGE_0 -> tab.text = getString(Title.TITLE_ASSESSMENT)
                    PAGE_1 -> tab.text = getString(Title.TITLE_SCORE)
                    PAGE_2 -> tab.text = getString(Title.TITLE_FIX_ESSAY)
                }
            }.attach()
        }
    }
}