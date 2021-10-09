package com.mchew.atrestaurants.core

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import timber.log.Timber

class ImageManager(
    context: Context
) {

    private var picasso = Picasso.Builder(context)
        .listener { _, uri, exception ->
            Timber.e(exception, "Failed to load image from $uri")
        }
        .build()

    fun loadImage(url: String, target: ImageView) {
        picasso.load(url)
            .fit()
            .into(target)
    }
}