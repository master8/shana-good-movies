package com.master8.shana.ui

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("srcCompat")
fun setImage(imageView: ImageView, image: Uri?) {
    image?.let {
        Glide.with(imageView)
            .load(it)
            .into(imageView)
    }
}