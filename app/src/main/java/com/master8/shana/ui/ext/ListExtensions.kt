package com.master8.shana.ui.ext

fun <T> List<T>?.changeItem(oldItem: T, newItem: T): List<T>? {
    val index = this?.indexOf(oldItem) ?: return this
    if (index == -1) {
        return this
    }

    return this.toMutableList().also { it[index] = newItem }
}