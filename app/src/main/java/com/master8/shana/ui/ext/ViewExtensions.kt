package com.master8.shana.ui.ext

import android.view.View

fun View.inverseVisibility() {
    this.visibility = when (this.visibility) {
        View.VISIBLE -> View.GONE
        else -> View.VISIBLE
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}