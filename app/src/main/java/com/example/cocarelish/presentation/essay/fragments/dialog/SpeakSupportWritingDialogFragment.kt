package com.example.cocarelish.presentation.essay.fragments.dialog

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
import com.example.cocarelish.R
import com.example.cocarelish.base.Event
import com.example.cocarelish.databinding.FragmentSpeakSupportWritingBinding
import com.example.cocarelish.presentation.essay.viewmodels.WritingEssayViewModel
import java.util.*


class SpeakSupportWritingDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentSpeakSupportWritingBinding
    private lateinit var speedRecognizer: SpeechRecognizer
    private lateinit var speechRecognizerIntent: Intent
    val viewModel: WritingEssayViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_speak_support_writing, null,false)
        speedRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
        binding.apply {
            action = viewModel
            lifecycleOwner = this@SpeakSupportWritingDialogFragment
        }
        handleTask()
        return AlertDialog.Builder(requireContext()).apply {
            setView(binding.root)
        }.create()
    }

    private fun handleTask() {

        speedRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                Log.d("TAGG", "onReadyForSpeech: dang nghe")
            }

            override fun onBeginningOfSpeech() {
//                viewModel.isRecording.value = true
            }

            override fun onRmsChanged(p0: Float) {
            }

            override fun onBufferReceived(p0: ByteArray?) {
            }

            override fun onEndOfSpeech() {
                viewModel.imgRecordStateClick()
            }

            override fun onError(p0: Int) {
                var message:String? = null
                when (p0) {
                    SpeechRecognizer.ERROR_AUDIO -> message = "Audio recording error"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> message = "Insufficient permissions"
                    SpeechRecognizer.ERROR_NETWORK -> message = "Network error"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> message = "Network timeout"
                    SpeechRecognizer.ERROR_NO_MATCH, SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> message = "we can't hear you please try again"
                    SpeechRecognizer.ERROR_SERVER -> message = "Error from server"
                }
                message?.let{Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()}
            }

            override fun onResults(p0: Bundle?) {
                Log.d("TAGG", "onResults: tra ket qua")
                val data = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                var text = data?.get(0)
                text = text?.let{
                    it.replaceRange(0,1, it.first().uppercase())}
                binding.edtContent.setText(text)
            }

            override fun onPartialResults(p0: Bundle?) {
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
            }

        })

        speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speak something")
        }

        binding.apply {
            btnCopy.setOnClickListener {
                val clipboard: ClipboardManager =
                    getSystemService(requireContext(),ClipboardManager::class.java) as ClipboardManager
                val clip = ClipData.newPlainText("simple text", edtContent.text)
                clipboard.setPrimaryClip(clip)
                clipboard.addPrimaryClipChangedListener {
                    Toast.makeText(context, "Saved your content successfully", Toast.LENGTH_SHORT).show()
                }
            }

            btnDelete.setOnClickListener {
                edtContent.setText("")
            }

            btnBack.setOnClickListener {
                dialog?.dismiss()
            }
            btnAccept.setOnClickListener {
                viewModel.recordText.postValue(Event(edtContent.text.toString()))
                dialog?.dismiss()
            }
        }

        viewModel.isRecording.observe(this){
            Log.d("TAGG", "handleTask: $it")
            if(it){
                speedRecognizer.startListening(speechRecognizerIntent)
            }else{
                speedRecognizer.stopListening()
            }
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