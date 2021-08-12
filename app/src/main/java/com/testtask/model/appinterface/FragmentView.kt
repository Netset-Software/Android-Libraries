package com.testtask.model.appinterface

import androidx.fragment.app.Fragment
import com.testtask.model.ToolbarConfig

interface FragmentView {

    fun getCurrentFragment(): Fragment?

    fun showToolbar(): Boolean?

    fun configureToolbar(): ToolbarConfig?
}