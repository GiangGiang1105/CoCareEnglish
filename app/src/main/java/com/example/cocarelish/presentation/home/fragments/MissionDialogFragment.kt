package com.example.cocarelish.presentation.home.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.Event
import com.example.cocarelish.databinding.FragmentSpeakSupportWritingBinding
import com.example.cocarelish.databinding.MissionDialogBinding
import com.example.cocarelish.presentation.essay.viewmodels.WritingEssayViewModel
import com.example.cocarelish.presentation.home.adapters.MissionAdapter
import com.example.cocarelish.presentation.home.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MissionDialogFragment : DialogFragment() {

    private lateinit var binding: MissionDialogBinding

    val viewModel: HomeViewModel by activityViewModels()
    private val missionAdapter: MissionAdapter = MissionAdapter(viewModel)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.mission_dialog, null,false)
        binding.apply {
            lifecycleOwner = this@MissionDialogFragment
            missionRecyclerView.adapter = missionAdapter
        }

        handleTask()
        return AlertDialog.Builder(requireContext()).apply {
            setView(binding.root)
        }.create()
    }

    private fun handleTask() {
        viewModel.listMission.observe(viewLifecycleOwner){
            missionAdapter.submitList(it)
        }
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