package com.master8.shana.data.source.firebase.database

import android.net.Uri
import com.google.firebase.storage.StorageReference

interface FirebaseStorageDataSource {

    suspend fun uploadImage(image: Uri): StorageReference
}