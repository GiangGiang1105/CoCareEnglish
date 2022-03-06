package com.example.cocarelish.presentation.essay.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentShowDetailEssayTitleBinding
import com.example.cocarelish.presentation.essay.fragments.dialog.ImageFullScreenDialogFragment
import com.example.cocarelish.presentation.essay.viewmodels.EssayViewModel
import kotlinx.android.synthetic.main.fragment_show_detail_essay_title.*

class ShowDetailTitleEssayFragment :
    CommonFragment<FragmentShowDetailEssayTitleBinding, EssayViewModel>(){

    override val viewModel: EssayViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_show_detail_essay_title
    private val idEssay by lazy {
        arguments?.getInt(ARG_ID_ESSAY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
            title = EssayViewModel.mapType[viewModel.currentTypeID.value]
            btnViewFullScreen.setOnClickListener {
                Log.d(TAG, "onViewCreated:btnViewFullScreen ")
                ImageFullScreenDialogFragment().show(childFragmentManager, "fullscreen")
            }
        }

//        setupOnClickListener()
    }

//    private fun setupOnClickListener() {
//        binding.apply {
//            btnSmallText.setOnClickListener(this@ShowDetailTitleEssayFragment)
//            btnMediumText.setOnClickListener(this@ShowDetailTitleEssayFragment)
//            btnLargeText.setOnClickListener(this@ShowDetailTitleEssayFragment)
//        }
//    }


    companion object {
        const val ARG_ID_ESSAY = "IdEssay"
    }

//    override fun onClick(p0: View?) {
//        when(p0){
//            binding.btnSmallText ->{
//            includeTitleContent.texts
//            }
//        }
//    }
//
//    private fun setTextSizeForScreen(size: Int){
//        binding.apply {
//
//        }
//    }

}