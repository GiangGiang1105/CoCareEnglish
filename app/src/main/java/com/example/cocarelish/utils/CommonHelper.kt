package com.example.cocarelish.utils

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.View
import android.widget.TextView
import android.widget.Toast

object CommonHelper {
    fun makeToast(context: Context?, resID:Int, duration: Int): Toast=
        Toast.makeText(context, resID, duration).also { createStyleToast(it) }

    fun makeToast(context: Context?, text:String, duration: Int): Toast=
        Toast.makeText(context, text, duration).also { createStyleToast(it) }

    private fun createStyleToast(toast: Toast) {
        val toastViewBackground = toast.view?.background
        val colorBackground = Color.rgb(35, 40, 40)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            toastViewBackground?.colorFilter =
                BlendModeColorFilter(colorBackground, BlendMode.SRC_IN)
        } else {
            @Suppress("DEPRECATION")
            toastViewBackground?.setColorFilter(colorBackground, PorterDuff.Mode.SRC_IN)
        }
        val toastMessage = toast.view?.findViewById<View>(android.R.id.message) as? TextView
        toastMessage?.setTextColor(Color.WHITE)
    }
}