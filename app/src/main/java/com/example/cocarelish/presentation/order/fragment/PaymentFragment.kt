package com.example.cocarelish.presentation.order.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentPaymentBinding
import com.example.cocarelish.presentation.essay.viewmodels.WritingEssayViewModel
import com.example.cocarelish.presentation.order.adapter.DeadlineAdapter
import com.example.cocarelish.utils.Title

class PaymentFragment : CommonFragment<FragmentPaymentBinding, WritingEssayViewModel>() {
    override val viewModel: WritingEssayViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_payment

    private val deadlineAdapter: DeadlineAdapter = DeadlineAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            listDeadlineEssay.observe(viewLifecycleOwner, {
                deadlineAdapter.setData(it)
            })
            getAllDeadline()
        }
        binding.apply {
            title = Title.TITLE_SUBMIT
            action = viewModel
            deadlineRecyclerView.apply {
                adapter = deadlineAdapter
                layoutManager = GridLayoutManager(context, 2)
            }

        }
        deadlineAdapter.setOnClickListener {
            viewModel.setDeadlineEssay(it)
        }
    }

}