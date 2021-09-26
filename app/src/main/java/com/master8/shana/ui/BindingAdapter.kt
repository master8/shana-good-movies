package com.master8.shana.ui

import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.master8.shana.data.blurhash.BlurHashDecoder
import com.master8.shana.domain.entity.Image

@BindingAdapter("srcCompat")
fun setImage(imageView: ImageView, image: Image?) {
    image?.let {
        val placeholder = image.blurHash?.let { BlurHashDecoder.decode(it, 9, 16) }
            ?.toDrawable(imageView.resources)

        Glide.with(imageView)
            .load(image.reference)
            .placeholder(placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    } ?: imageView.setImageDrawable(null)
}