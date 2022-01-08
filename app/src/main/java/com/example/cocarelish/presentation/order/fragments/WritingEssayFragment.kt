package com.example.cocarelish.presentation.order.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentWritingEssayBinding
import com.example.cocarelish.presentation.essay.fragments.dialog.SpeakSupportWritingDialogFragment
import com.example.cocarelish.presentation.order.viewmodels.WritingEssayViewModel
import com.github.onecode369.wysiwyg.WYSIWYG

class WritingEssayFragment : CommonFragment<FragmentWritingEssayBinding, WritingEssayViewModel>() {
    companion object {
        private const val RQ_SPEECH_REC = 102
    }
    override val viewModel: WritingEssayViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_writing_essay

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        binding.apply {
            action = viewModel
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
        handleTask()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        return super.onCreateView(inflater, container, savedInstanceState)
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

    private fun setupListener() {
        binding.imgSpeak.setOnClickListener {
            checkPermissionAudioRecord()
            Log.d("TAGG", "setupListener: ")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        super.onCreate(savedInstanceState)
    }

    private fun handleTask() {
        initView()
        initListener()
    }

    private fun initListener() {

        binding.editor.setOnTextChangeListener(object : WYSIWYG.OnTextChangeListener {
            override fun onTextChange(text: String?) {
                binding.btnComplete.isEnabled = text?.trim() != ""
            }
        })
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {

        }
    }

    private fun initView() {

        val wysiwygEditor = binding.editor
        wysiwygEditor.setEditorHeight(200)
        wysiwygEditor.setEditorFontSize(16)
        wysiwygEditor.setPadding(10, 10, 10, 10)
        wysiwygEditor.setPlaceholder("Insert your notes here...")

//        val mPreview = preview as TextView
//        wysiwygEditor.setOnTextChangeListener(OnTextChangeListener { text -> mPreview.setText(text) })

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

//        binding.actionInsertLink.setOnClickListener{
//            wysiwygEditor.insertLink(
//                "https://github.com/onecode369",
//                "One Code"
//            )
//        }

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
