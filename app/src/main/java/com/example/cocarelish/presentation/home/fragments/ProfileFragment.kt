package com.example.cocarelish.presentation.home.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.cocarelish.R
import com.example.cocarelish.base.CommonFragment
import com.example.cocarelish.databinding.FragmentProfileBinding
import com.example.cocarelish.presentation.home.viewmodels.ProfileViewModel
import com.example.cocarelish.presentation.main.UserInformationDialog
import com.example.cocarelish.utils.MyPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : CommonFragment<FragmentProfileBinding, ProfileViewModel>(),
    ProfileViewModel.ShowUserInformationDialog {
    override val viewModel: ProfileViewModel by activityViewModels()
    override val layoutID: Int
        get() = R.layout.fragment_profile

    @Inject
    lateinit var myPreference: MyPreference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            setShowUserInformationDialog(this@ProfileFragment)
            isNavigation.observe(viewLifecycleOwner) {
                if (it){
                    navigateToSelectedTabIndex(1)
                    myPreference.saveNavigationToMyEssay(true)
                }
                viewModel.isNavigation.postValue(false)
            }
        }
        binding.apply {
            action = viewModel
            /*  btnShow.setOnClickListener {
                  UserInformationDialog().show(childFragmentManager, "userInformation")
              }*/
        }
    }

    override fun showDialog(isShow: Boolean) {
        if (isShow) {
            UserInformationDialog().show(childFragmentManager, "userInformation")
        }
    }
}