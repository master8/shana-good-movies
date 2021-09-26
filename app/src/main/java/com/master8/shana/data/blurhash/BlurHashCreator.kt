package com.master8.shana.data.blurhash

import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BlurHashCreator(
    private val context: Context
) {

    suspend fun create(image: Uri): String {
        val bitmap = withContext(Dispatchers.IO) {
            Glide.with(context)
                .asBitmap()
                .load(image)
                .submit()
                .get()
        }

        return withContext(Dispatchers.Default) {
            BlurHash.encode(bitmap, 3, 5)
        }
    }
}