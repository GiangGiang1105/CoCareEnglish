package com.example.cocarelish.utils.databinding

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide.with

import com.example.cocarelish.R
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

@BindingAdapter("app:url")
fun ImageView.loadImage(url: String) {
    Log.e("TAGG", "loadImage: $url")
    Picasso.get().isLoggingEnabled = true
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.image_placeholder)
        .fit()
        .into(this)
}

@BindingAdapter("app:data_bitmap")
fun ImageView.showBitmap(dataBitmap: String?){
    if (dataBitmap!= null )
    this.setImageBitmap(dataBitmap.convertBase64ToBitmap())
}

fun String.convertBase64ToBitmap(): Bitmap {
    val byteString = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byteString,0, byteString.size)
}

fun Bitmap.encodeImage(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}