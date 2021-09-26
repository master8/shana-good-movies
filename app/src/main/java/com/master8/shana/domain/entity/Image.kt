package com.master8.shana.domain.entity

import android.net.Uri
import com.google.firebase.storage.StorageReference

sealed class Image {
    abstract val reference: Any
    abstract val blurHash: String?
}

data class UriImage(
    override val reference: Uri
) : Image() {
    override val blurHash: String? = null

    override fun toString(): String {
        return reference.toString()
    }
}

data class StorageReferenceImage(
    override val reference: StorageReference,
    override val blurHash: String? = null
) : Image() {
    override fun toString(): String {
        return reference.path
    }
}