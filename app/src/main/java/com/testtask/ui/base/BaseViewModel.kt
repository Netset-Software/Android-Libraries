package com.testtask.ui.base


import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.testtask.model.remote.ReflectionUtil
import com.testtask.model.repo.AppRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*


open class BaseViewModel() : ViewModel(), KoinComponent {

    /**
     *  ApplicationRepository is injected here to access application level
     *  functions & preference
     *
     */
    val appRepo: AppRepository by inject()
    val reflection: ReflectionUtil by inject()



}

