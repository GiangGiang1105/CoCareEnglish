package com.example.cocarelish.presentation.main

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.*
import com.example.cocarelish.databinding.UserInformationBinding
import com.example.cocarelish.presentation.home.viewmodels.ProfileViewModel
import com.example.cocarelish.utils.CoCareLishPreference
import com.example.cocarelish.utils.MyPreference
import com.example.cocarelish.utils.Title
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_payment.*
import javax.inject.Inject

@AndroidEntryPoint
class UserInformationDialog : DialogFragment() {

    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var binding: UserInformationBinding

    @Inject
    lateinit var myPreference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserInformationBinding.inflate(inflater,container, false)
        binding.apply {
            lifecycleOwner = this@UserInformationDialog.viewLifecycleOwner
            action = this@UserInformationDialog.viewModel
            title = Title.TITLE_PROFILE
            btnBack.setOnClickListener {
                dismissDialog()
            }
        }
        return binding.root
    }

    private fun dismissDialog() {
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