package com.example.cocarelish.presentation.grade.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentFixEssayBinding
import com.example.cocarelish.presentation.grade.adapter.ItemAdapter
import com.example.cocarelish.presentation.grade.viewmodel.GradeAndJudgeViewModel

class FixEssayFragment : CommonFragment<FragmentFixEssayBinding, GradeAndJudgeViewModel>() {

    override val viewModel: GradeAndJudgeViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_fix_essay
    private val sentenceAdapter = ItemAdapter()

    var length = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
            recyclerView2.apply {
                adapter = sentenceAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            btnNext.setOnClickListener {
                var position =
                    (recyclerView2.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (position < length - 1) {
                    position += 1
                } else {
                    position = 0
                }
                viewModel.getOrderSentence(position)
                recyclerView2.smoothScrollToPosition(position)
                setContent(position + 1)
            }
            btnBack.setOnClickListener {
                var position =
                    (recyclerView2.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (position > 0) {
                    position -= 1
                } else {
                    position = length - 1
                }
                viewModel.getOrderSentence(position)
                recyclerView2.smoothScrollToPosition(position)
                setContent(position + 1)
            }
        }

        viewModel.listSentenceReview.observe(viewLifecycleOwner) {
            length = it.count()
            setContent(1)
            sentenceAdapter.setData(it)
        }
    }

    private fun setContent(position: Int) {
        binding.content.text = "$position/$length"
    }
}
