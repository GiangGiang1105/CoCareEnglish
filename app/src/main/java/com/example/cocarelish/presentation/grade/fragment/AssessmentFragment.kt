package com.example.cocarelish.presentation.grade.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentAssessmentBinding
import com.example.cocarelish.presentation.grade.viewmodel.GradeAndJudgeViewModel

class AssessmentFragment : CommonFragment<FragmentAssessmentBinding, GradeAndJudgeViewModel>() {

    override val viewModel: GradeAndJudgeViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_assessment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
        }
    }
}