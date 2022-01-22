package com.example.cocarelish.presentation.home.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentProfileBinding
import com.example.cocarelish.presentation.home.viewmodels.ProfileViewModel
import com.example.cocarelish.presentation.main.UserInformationDialog
import com.example.cocarelish.utils.CoCareLishPrefence
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : CommonFragment<FragmentProfileBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            CoCareLishPrefence(it).apply {
                init()
                viewModel.getUserInformation(getIdUser())
            }
        }
        binding.apply {
            action = viewModel
            btnShow.setOnClickListener {
                UserInformationDialog().show(childFragmentManager, "userInformation")
            }
        }
    }
}