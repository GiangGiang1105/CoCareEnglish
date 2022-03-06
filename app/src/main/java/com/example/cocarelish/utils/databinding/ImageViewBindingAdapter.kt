package com.example.cocarelish.utils.databinding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.cocarelish.R
import com.squareup.picasso.Picasso

@BindingAdapter("app:url")
fun ImageView.loadImage(url: String?) {
    if(url.isNullOrEmpty()) return
    Log.e("ImageView", "loadImage: $url")
    Picasso.get().isLoggingEnabled = true
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.image_placeholder)
        .into(this)
}