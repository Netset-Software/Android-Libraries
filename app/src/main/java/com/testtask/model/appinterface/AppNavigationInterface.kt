package com.testtask.model.appinterface

import android.os.Bundle
import androidx.fragment.app.Fragment

interface AppNavigationInterface {

    fun replaceFragment(fragment: Fragment, addToStack: Boolean, showAnimation: Boolean)
    fun setArguments(fragment: Fragment, bundle: Bundle):Fragment
    fun showToast(message: String)
    fun showLoader()
    fun hideLoader()

}