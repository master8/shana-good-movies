package com.master8.shana.data.source.firebase.database

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.net.URL
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseStorageDataSourceImpl : FirebaseStorageDataSource {

    private val postersStorage by lazy { Firebase.storage.reference.child(PATH_POSTERS) }

    override suspend fun uploadImage(image: Uri): StorageReference = suspendCoroutine { continuation ->
        val imageReference = buildPosterReference()
        imageReference.putStream(URL(image.toString()).openStream())
            .addOnSuccessListener {
                continuation.resume(imageReference)
            }
            .addOnFailureListener {
                continuation.resumeWithException(RuntimeException("Query was cancelled! ${it.message}"))
            }
    }

    private fun buildPosterReference(): StorageReference {
        val internalId = UUID.randomUUID()
        return postersStorage.child("$internalId.jpg")
    }

    private companion object {
        const val PATH_POSTERS = "posters"
    }
}