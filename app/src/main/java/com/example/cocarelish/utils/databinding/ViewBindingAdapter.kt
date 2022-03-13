package com.example.cocarelish.utils.databinding

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.example.cocarelish.R
import com.example.cocarelish.utils.Consts
import com.example.cocarelish.utils.Status
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.coroutineContext


@BindingAdapter("android:visibility")
fun setVisibility(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
}

@BindingAdapter(" android:layout_height ")
fun Button.setLayoutHeight(isVisible: Boolean) {
    val layoutParams: ViewGroup.LayoutParams = layoutParams
    if (isVisible) layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
    else layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    this.layoutParams = layoutParams
}

@BindingAdapter(value = ["position", "type_topic"], requireAll = false)
fun TextView.setTextTopic(position: Int, typeTopic: String) {
    val textType = Consts.HASH_TAG + position + Consts.COLON + Consts.SPACE + typeTopic
    this.text = textType
}


//@BindingAdapter(value = ["status_id", "score"])
//fun TextView.setTextStatus(status_id: Int, score: String?) {
//    Log.e("TextView", "setTextStatus: $status_id with $score")
//    if (status_id == Status.STATUS_1) {
//        this.text = Consts.SCORE + score
//    } else if (status_id == Status.STATUS_2) {
//        this.text = Consts.SENT
//    }
//}

//@BindingAdapter("data_image")
//fun AppCompatImageView.setImage(data_image: String?) {
//    if (data_image != null)
//        this.setImageBitmap(data_image.convertBase64ToBitmap())
//}
//
//
//@BindingAdapter("data_image")
//fun SubsamplingScaleImageView.setImage(data_image: String?){
//    if (data_image != null)
//        setImage(ImageSource.bitmap(data_image.convertBase64ToBitmap()))
//}

@BindingAdapter("url")
fun SubsamplingScaleImageView.setImageUrl(urlPath: String){
    Log.d("ScaleImageView", "setImageUrl: $urlPath ")

    if(urlPath == "") return
    Picasso.get().isLoggingEnabled = true
    Picasso.get()
        .load(urlPath)
        .placeholder(R.drawable.image_placeholder)
        .into(object :Target{
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let { ImageSource.bitmap(it) }?.let { this@setImageUrl.setImage(it) }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

        })

}