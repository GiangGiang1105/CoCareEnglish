package com.example.cocarelish.presentation.essay.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentWritingEssayBinding
import com.example.cocarelish.presentation.essay.fragments.dialog.SpeakSupportWritingDialogFragment
import com.example.cocarelish.presentation.essay.viewmodels.WritingEssayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WritingEssayFragment : CommonFragment<FragmentWritingEssayBinding, WritingEssayViewModel>() {
    companion object {
        private const val RQ_SPEECH_REC = 102
        const val ARG_ID_ESSAY = "IdEssay"
        const val ARG_LEVEL_NAME = "LevelName"
        const val ARG_TOPIC_NAME = "TopicName"
    }

    override val viewModel: WritingEssayViewModel by viewModels()
    override val layoutID: Int
        get() = R.layout.fragment_writing_essay

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val idEssay by lazy {
        arguments?.getInt(ARG_ID_ESSAY)
    }
    private val levelName by lazy {
        arguments?.getString(ARG_LEVEL_NAME)
    }
    private val topicName by lazy {
        arguments?.getString(ARG_TOPIC_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setupListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
            Log.e(TAG, "onViewCreated: writing essay fragment ${idEssay}", )
            idEssay?.let { viewModel.getDetailTest(it) }
        }
        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    SpeakSupportWritingDialogFragment().show(childFragmentManager,"tag")
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            RQ_SPEECH_REC -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    SpeakSupportWritingDialogFragment().show(childFragmentManager,"tag")
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun checkPermissionAudioRecord() {
        when{
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                SpeakSupportWritingDialogFragment().show(childFragmentManager,"tag")
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.RECORD_AUDIO)
            }
        }
    }

  /*  private fun setupListener() {
        binding.imgSpeak.setOnClickListener {
            checkPermissionAudioRecord()
            Log.d("TAGG", "setupListener: ")
        }
    }*/
}