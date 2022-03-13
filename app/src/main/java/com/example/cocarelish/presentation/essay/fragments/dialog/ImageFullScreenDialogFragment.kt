package com.example.cocarelish.presentation.essay.fragments.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.databinding.DialogImageViewFullScreenBinding
import com.example.cocarelish.presentation.essay.viewmodels.EssayViewModel
import com.example.cocarelish.presentation.essay.viewmodels.dialog.ImageFullScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageFullScreenDialogFragment : DialogFragment() {
    private lateinit var binding: DialogImageViewFullScreenBinding
    val viewModel: ImageFullScreenViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_image_view_full_screen,
            null,
            false
        )
        binding.apply {
            action = viewModel
            lifecycleOwner = this@ImageFullScreenDialogFragment
        }

        return AlertDialog.Builder(requireContext()).apply {
            setView(binding.root)
        }.create()
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val wlp = window?.attributes
        wlp?.let {
            it.gravity = Gravity.CENTER
            it.width = WindowManager.LayoutParams.MATCH_PARENT
            it.height = WindowManager.LayoutParams.WRAP_CONTENT
            it.windowAnimations = R.style.dialogAnimation
        }
        window?.attributes = wlp
    }
}