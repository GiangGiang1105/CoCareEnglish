package com.example.cocarelish.utils.databinding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun setVisibility(view: View, isGone: Boolean){
    view.visibility = if(isGone) View.GONE else View.VISIBLE
}
