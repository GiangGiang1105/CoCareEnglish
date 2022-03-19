package com.example.cocarelish.presentation.essay.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentWritingEssayBinding
import com.example.cocarelish.presentation.essay.fragments.dialog.ImageFullScreenDialogFragment
import com.example.cocarelish.presentation.essay.fragments.dialog.SpeakSupportWritingDialogFragment
import com.example.cocarelish.presentation.essay.viewmodels.WritingEssayViewModel
import com.github.onecode369.wysiwyg.WYSIWYG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WritingEssayFragment : CommonFragment<FragmentWritingEssayBinding, WritingEssayViewModel>() {
    companion object {
        private const val RQ_SPEECH_REC = 102
        const val ARG_ID_ESSAY = "IdEssay"
        const val ARG_LEVEL_NAME = "LevelName"
        const val ARG_TOPIC_NAME = "TopicName"
    }

    override val viewModel: WritingEssayViewModel by activityViewModels()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupListener()
        initView()
        binding.apply {
            action = viewModel
            Log.e(TAG, "onViewCreated: writing essay fragment $idEssay")
            idEssay?.let {
                viewModel.getDetailEssay(it)
            }
            btnComplete.setOnClickListener {
                viewModel.navigationWritingEssayFragmentToPaymentFragment(binding.editor.html)
            }
            btnViewFullScreen.setOnClickListener {
                ImageFullScreenDialogFragment().show(childFragmentManager, "fullscreen")
            }
        }
        viewModel.recordText.observe(viewLifecycleOwner) {
            val a = it.getContentIfNotHandled()
            if (a.isNullOrEmpty()) return@observe
            binding.editor.apply {
               html = if(html.isNullOrEmpty()) a else html + a
            }
        }
        levelName?.let {
            Log.d(TAG, "onViewCreated:level name  $it")
            viewModel.setLevelName(it)
        }
        topicName?.let {
            Log.d(TAG, "onViewCreated: topic name $it")
            viewModel.setTopicName(it)
        }
        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    SpeakSupportWritingDialogFragment().show(childFragmentManager, "tag")
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
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    SpeakSupportWritingDialogFragment().show(childFragmentManager, "tag")
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
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                SpeakSupportWritingDialogFragment().show(childFragmentManager, "tag")
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.RECORD_AUDIO
                )
            }
        }
    }

    private fun setupListener() {
        binding.imgSpeak.setOnClickListener {
            checkPermissionAudioRecord()
            Log.d("TAGG", "setupListener: ")
        }
    }

    private fun initView() {
        val wysiwygEditor = binding.editor
        wysiwygEditor.setOnTextChangeListener(object : WYSIWYG.OnTextChangeListener {
            override fun onTextChange(text: String?) {
                val words = text.toString().trim()
                val regex = "(<br>)+$"
                val numberOfInputWords =
                    words.replace(regex.toRegex(), "").split("(\\s|<br>)+".toRegex()).size
                binding.wordCount.text = "Word Count: $numberOfInputWords"
            }
        })
        wysiwygEditor.setEditorHeight(200)
        wysiwygEditor.setEditorFontSize(16)
        wysiwygEditor.setPadding(10, 10, 10, 10)
        wysiwygEditor.setPlaceholder("Insert your notes here...")
        binding.actionUndo.setOnClickListener {
            Log.e("TAG", "onCreate: wqwewq")
            wysiwygEditor.undo()
        }

        binding.actionRedo.setOnClickListener { wysiwygEditor.redo() }

        binding.actionBold.setOnClickListener { wysiwygEditor.setBold() }

        binding.actionItalic.setOnClickListener { wysiwygEditor.setItalic() }

        binding.actionSubscript.setOnClickListener { wysiwygEditor.setSubscript() }
//
        binding.actionSuperscript.setOnClickListener {

            wysiwygEditor.setSuperscript()
        }

        binding.actionStrikethrough.setOnClickListener { wysiwygEditor.setStrikeThrough() }

        binding.actionUnderline.setOnClickListener { wysiwygEditor.setUnderline() }

        binding.actionHeading1.setOnClickListener {
            wysiwygEditor.setHeading(
                1
            )
        }

        binding.actionHeading2.setOnClickListener {
            wysiwygEditor.setHeading(
                2
            )
        }

        binding.actionHeading3.setOnClickListener {
            wysiwygEditor.setHeading(
                3
            )
        }

        binding.actionHeading4.setOnClickListener {
            wysiwygEditor.setHeading(
                4
            )
        }

        binding.actionHeading5.setOnClickListener {
            wysiwygEditor.setHeading(
                5
            )
        }

        binding.actionHeading6.setOnClickListener {
            wysiwygEditor.setHeading(
                6
            )
        }

        binding.actionTxtColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                wysiwygEditor.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

        binding.actionIndent.setOnClickListener { wysiwygEditor.setIndent() }

        binding.actionOutdent.setOnClickListener { wysiwygEditor.setOutdent() }

        binding.actionAlignLeft.setOnClickListener { wysiwygEditor.setAlignLeft() }

        binding.actionAlignCenter.setOnClickListener { wysiwygEditor.setAlignCenter() }

        binding.actionAlignRight.setOnClickListener { wysiwygEditor.setAlignRight() }

        binding.actionAlignJustify.setOnClickListener { wysiwygEditor.setAlignJustifyFull() }

        binding.actionBlockquote.setOnClickListener { wysiwygEditor.setBlockquote() }

        binding.actionInsertBullets.setOnClickListener { wysiwygEditor.setBullets() }

        binding.actionInsertNumbers.setOnClickListener { wysiwygEditor.setNumbers() }

        binding.actionInsertImage.setOnClickListener {
            wysiwygEditor.insertImage(
                "https://i.postimg.cc/JzL891Fm/maxresdefault.jpg",
                "Night Sky"
            )
        }

        binding.actionInsertCheckbox.setOnClickListener { wysiwygEditor.insertTodo() }

        var visible = false

        binding.preview.setOnClickListener {
            if (!visible) {
                wysiwygEditor.setInputEnabled(false)
                binding.preview.setImageResource(R.drawable.visibility_off)
            } else {
                wysiwygEditor.setInputEnabled(true)
                binding.preview.setImageResource(R.drawable.visibility)
            }
            visible = !visible
        }
    }
}