package com.example.cocarelish.presentation.main

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.*
import com.example.cocarelish.databinding.UserInformationBinding
import com.example.cocarelish.presentation.home.viewmodels.ProfileViewModel
import com.example.cocarelish.utils.CoCareLishPrefence
import com.example.cocarelish.utils.Title
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInformationDialog : DialogFragment() {

    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var binding: UserInformationBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            context?.let {
                CoCareLishPrefence(it).apply {
                    init()
                    viewModel.getUserInformation(getIdUser())
                }
            }
            binding = UserInformationBinding.inflate(inflater)
            builder.setView(binding.root)
            binding.action = viewModel
            binding.title = Title.TITLE_PROFILE
            binding.btnBack.setOnClickListener {
                dismissDialog()
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun dismissDialog()
    {
        dialog?.dismiss()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: java.lang.IllegalStateException) {
            Log.d("ABSDIALOGFRAG", "Exception", e)
        }
    }
}