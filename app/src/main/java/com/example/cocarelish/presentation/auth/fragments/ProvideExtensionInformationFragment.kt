package com.example.cocarelish.presentation.auth.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentProvideExtensionInformationBinding
import com.example.cocarelish.presentation.auth.viewmodels.ProvideExtensionInformationViewModel
import kotlinx.android.synthetic.main.fragment_provide_extension_information.*


class ProvideExtensionInformationFragment :
    CommonFragment<FragmentProvideExtensionInformationBinding, ProvideExtensionInformationViewModel>() {
    override val viewModel: ProvideExtensionInformationViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_provide_extension_information

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender,
            R.layout.item_gender
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            gender.adapter = adapter
        }

        val listener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        (parent.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.color_95FFFFFF
                            )
                        )
                    } else {
                        (parent.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        gender.onItemSelectedListener = listener

    }
}