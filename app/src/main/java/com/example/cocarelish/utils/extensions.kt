package com.example.cocarelish.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout

@BindingAdapter("app:title")
fun CollapsingToolbarLayout.setTitle(titleId: Int){
    this.title = context.getString(titleId)
}

@BindingAdapter("app:url")
fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}