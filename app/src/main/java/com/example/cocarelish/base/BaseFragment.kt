package com.example.cocarelish.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.cocarelish.utils.CommonHelper

abstract class BaseFragment<T : ViewDataBinding, VM : ViewModel> :
    Fragment() {

    private var _binding: T? = null
    val binding: T get() = _binding!!

    private lateinit var controller: NavController
    abstract val viewModel: VM

    @get:LayoutRes
    abstract val layoutID: Int


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("TAG", "onCreateView: $layoutID")
        _binding = DataBindingUtil.inflate(inflater, layoutID, container, false)
        controller = findNavController()

        handleTasks()
        return binding.apply { lifecycleOwner = viewLifecycleOwner }.root
    }

    open fun navigateToDestination(actionID: Int, bundle: Bundle?) {
        bundle?.let {
            findNavController().navigate(actionID, it)
        } ?: findNavController().navigate(actionID)
    }

    open fun onBackFragment() {
        findNavController().popBackStack()
    }

    private var toast: Toast? = null
    open fun showToast(content: String, duration: Int) {
        toast?.cancel()
        toast = CommonHelper.makeToast(context, content, duration)
        toast?.show()
    }

    abstract fun handleTasks()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}