package com.master8.shana.ui.ext

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<List<T>>.changeItem(oldItem: T, newItem: T) {
    this.value.changeItem(oldItem, newItem)?.let {
        this.value = it
    }
}