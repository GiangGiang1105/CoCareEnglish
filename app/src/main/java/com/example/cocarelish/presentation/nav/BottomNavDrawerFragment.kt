//package com.example.cocarelish.presentation.nav
//
//import android.app.usage.UsageEvents.Event.NONE
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import androidx.activity.OnBackPressedCallback
//import androidx.fragment.app.viewModels
//import com.example.cocarelish.R
//import com.example.cocarelish.base.CommonFragment
//import com.example.cocarelish.databinding.FragmentBottomNavDrawerBinding
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//
//class BottomNavDrawerFragment:CommonFragment<FragmentBottomNavDrawerBinding, BottomNavDrawerViewModel>() {
//    private val closeDrawerOnBackPressed = object : OnBackPressedCallback(false) {
//        override fun handleOnBackPressed() {
//            close()
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        requireActivity().onBackPressedDispatcher.addCallback(this, closeDrawerOnBackPressed)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding.foregroundContainer.setOnApplyWindowInsetsListener { view, windowInsets ->
//            // Record the window's top inset so it can be applied when the bottom sheet is slide up
//            // to meet the top edge of the screen.
//            view.setTag(
//                R.id.tag_system_window_inset_top,
//                windowInsets.systemWindowInsetTop
//            )
//            windowInsets
//        }
//        return binding.root
//    }
//
//    private val behavior: BottomSheetBehavior<FrameLayout> by lazy(NONE) {
//        BottomSheetBehavior.from(binding.backgroundContainer)
//    }
//
//    fun open() {
//        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//    }
//
//    fun close() {
//        behavior.state = BottomSheetBehavior.STATE_HIDDEN
//    }
//
//    override val viewModel: BottomNavDrawerViewModel by viewModels()
//    override val layoutID: Int
//        get() = R.layout.fragment_bottom_nav_drawer
//}