package com.testtask.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import java.io.File

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}
