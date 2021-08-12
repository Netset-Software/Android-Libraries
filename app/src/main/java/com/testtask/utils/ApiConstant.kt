package com.testtask.utils

import com.testtask.BuildConfig

object ApiConstant {

    private const val BASE_URL = BuildConfig.BASE_URL
    const val API_TIME_OUT: Long = 10000
    fun getBaseURL(): String {
        return BASE_URL
    }
}