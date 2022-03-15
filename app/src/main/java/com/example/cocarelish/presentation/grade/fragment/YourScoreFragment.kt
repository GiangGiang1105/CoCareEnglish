package com.example.cocarelish.presentation.grade.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentYourScoreBinding
import com.example.cocarelish.presentation.grade.viewmodel.GradeAndJudgeViewModel

class YourScoreFragment : CommonFragment<FragmentYourScoreBinding, GradeAndJudgeViewModel>() {

    override val viewModel: GradeAndJudgeViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_your_score

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
        }
    }
    companion object {

        fun newInstance() = YourScoreFragment()
    }
}