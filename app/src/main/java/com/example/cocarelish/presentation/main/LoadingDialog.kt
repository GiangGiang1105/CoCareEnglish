package com.example.cocarelish.presentation.main

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.cocarelish.R
import java.lang.IllegalStateException

class LoadingDialog(): DialogFragment() {
  /*  private var dialog: AlertDialog? = null*/

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        //set view
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.item_loading, null)
        //** set dialog */
//        val builder = AlertDialog.Builder(activity)
//        builder.setView(dialogView)
//        builder.setCancelable(false)
//        dialog = builder.create()

        val window = dialog?.window
        val wlp = window?.attributes
        wlp?.let {
            it.gravity = Gravity.CENTER
            it.width = WindowManager.LayoutParams.MATCH_PARENT
            it.height = WindowManager.LayoutParams.WRAP_CONTENT
            it.windowAnimations = R.style.dialogAnimation
        }
        val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 90)
        window?.attributes = wlp
        window?.setBackgroundDrawable(inset)
//        dialog?.show()
        return AlertDialog.Builder(requireContext()).setView(dialogView).setCancelable(false).create()
    }

    fun startLoading()
    {

    }

    fun dismissDialog()
    {
        dialog?.dismiss()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            Log.d("ABSDIALOGFRAG", "Exception", e)
        }
    }

//    fun getInstance
}