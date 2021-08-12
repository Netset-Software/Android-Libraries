package com.testtask.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.testtask.R
import kotlinx.coroutines.*


object DialogUtil {


    fun setLoadingDialog(activity: AppCompatActivity): Dialog {
        val dialog = Dialog(activity)
        dialog.show()
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setContentView(R.layout.loader)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun showExitWarning(layout: ViewGroup, activity: AppCompatActivity) {
        val snack = Snackbar.make(
            layout,
            "To exit, Tap back button again.",
            Snackbar.LENGTH_SHORT
        )
        snack.setTextColor(ContextCompat.getColor(activity, R.color.white))
        snack.setActionTextColor(ContextCompat.getColor(activity, R.color.white))
        snack.setAction("EXIT NOW") {
            activity.finish()
        }
        val snackBarView = snack.view
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                activity,
                R.color.black
            )
        )
        snack.show()
    }
}