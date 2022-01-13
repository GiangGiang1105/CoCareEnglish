package com.example.cocarelish.base

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.cocarelish.presentation.main.LoadingDialog
import com.example.cocarelish.utils.base.CommonHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class CommonFragment<T : ViewDataBinding, VM : CommonViewModel> :
    Fragment() {
    protected val TAG by lazy { this::class.simpleName }

    private var _binding: T? = null
    val binding: T get() = _binding!!

    private var jobEventSender: Job? = null
    private lateinit var controller: NavController
    abstract val viewModel: VM

    @get:LayoutRes
    abstract val layoutID: Int

//    private val loadingDialog : LoadingDialog by lazy{ LoadingDialog(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("TAG", "onCreateView: $layoutID")
        _binding = DataBindingUtil.inflate(inflater, layoutID, container, false)
        controller = findNavController()

        return binding.apply { lifecycleOwner = viewLifecycleOwner }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobEventSender = lifecycleScope.launch {
            Log.d(TAG, "onViewCreated: Receive Item from Eventsender")
            viewModel.eventReceiver.collectLatest {
                when (it) {
                    is CommonEvent.OnNavigation -> navigateToDestination(it.destination, it.bundle)
                    CommonEvent.OnCloseApp -> activity?.finish()
                    CommonEvent.OnBackScreen -> onBackFragment()
                    is CommonEvent.OnOpenAnotherApp -> openAnotherApp(it.packageName)
                    is CommonEvent.OnShowToast -> showToast(it.content, it.type)
                    is CommonEvent.OnShowLoadingDialog -> showLoadingDialog(it.isShowing)
                }
            }
        }
    }

    open fun navigateToDestination(actionID: Int, bundle: Bundle?) {
        Log.d(TAG, "navigateToDestination: Navigating to $actionID with bundle $bundle")
        bundle?.let {
            findNavController().navigate(actionID, it)
        } ?: findNavController().navigate(actionID)
    }

    open fun onBackFragment() {
        findNavController().popBackStack()
    }

    open fun openAnotherApp(packageName: String) {
        val launchIntent = context?.packageManager?.getLaunchIntentForPackage(packageName)
        startActivity(launchIntent)
    }

    fun onClearViewModelInScopeActivity() {
        activity?.viewModelStore?.clear()
    }

    private var toast: Toast? = null
    open fun showToast(content: String, duration: Int) {
        toast?.cancel()
        toast = CommonHelper.makeToast(context, content, duration)
        toast?.show()
    }

    open fun showLoadingDialog(isShowing: Boolean){
        if(isShowing){
//            LoadingDialog.show.startLoading()
            LoadingDialog().show(childFragmentManager,"aa")
        }else{
//            loadingDialog.dismissDialog()
            childFragmentManager.findFragmentByTag("aa")?.let {
                (it as DialogFragment).dismiss()
            }
//            LoadingDialog().dismissDialog()

        }
    }

    override fun onDestroyView() {
        _binding = null
        jobEventSender?.cancel()
        super.onDestroyView()
    }
}