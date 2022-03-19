package com.example.cocarelish.presentation.order.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentInvoiceDialogBinding
import com.example.cocarelish.presentation.essay.viewmodels.WritingEssayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvoicePaymentDialog : CommonFragment<FragmentInvoiceDialogBinding,WritingEssayViewModel >() {

    override val viewModel: WritingEssayViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            action = viewModel
            btnBack.setOnClickListener { 
                onBackFragment()
            }
        }
    }

    override val layoutID: Int
        get() = R.layout.fragment_invoice_dialog
}