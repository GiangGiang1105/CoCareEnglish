package com.example.cocarelish.presentation.grade.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentGradeAndJudgeBinding
import com.example.cocarelish.presentation.essay.fragments.dialog.ImageFullScreenDialogFragment
import com.example.cocarelish.presentation.grade.adapter.GradeAdapter
import com.example.cocarelish.presentation.grade.viewmodel.GradeAndJudgeViewModel
import com.example.cocarelish.utils.Consts.PAGE_0
import com.example.cocarelish.utils.Consts.PAGE_1
import com.example.cocarelish.utils.Consts.PAGE_2
import com.example.cocarelish.utils.MyPreference
import com.example.cocarelish.utils.Title
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GradeAndJudgeFragment :
    CommonFragment<FragmentGradeAndJudgeBinding, GradeAndJudgeViewModel>() {
    override val viewModel: GradeAndJudgeViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_grade_and_judge

    private val orderId by lazy { arguments?.getString(ARGUMENT_ODER_ID) ?: "" }

    @Inject
    lateinit var myPreference: MyPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
            viewPager.adapter = activity?.let { GradeAdapter(it) }
            btnViewFullScreen.setOnClickListener {
                Log.d(TAG, "onViewCreated:btnViewFullScreen ")
                ImageFullScreenDialogFragment().show(childFragmentManager, "fullscreen")
            }
            yourContent.isEnabled = false
            isCancel = myPreference.getIsCancelEssay()
        }
        viewModel.displayOrder.observe(viewLifecycleOwner) {
            binding.yourContent.html = it.content
        }
        viewModel.setOrderID(orderId)
        handleTabWithViewPager()
    }

    private fun handleTabWithViewPager() {
        binding.apply {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    PAGE_0 -> tab.text = getString(Title.TITLE_ASSESSMENT)
                    PAGE_1 -> tab.text = getString(Title.TITLE_SCORE)
                    PAGE_2 -> tab.text = getString(Title.TITLE_FIX_ESSAY)
                }
            }.attach()
        }
    }

    companion object {
        const val ARGUMENT_ODER_ID = "order_id"
        const val ARGUMENT_IS_CANCEL = "is_cancel"
    }
}