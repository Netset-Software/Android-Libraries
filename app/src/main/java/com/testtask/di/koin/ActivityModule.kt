package com.testtask.di.koin

import com.testtask.ui.main.MainActivity
import org.koin.core.qualifier.named
import org.koin.dsl.module


var homeActivityModule = module {
    scope(named<MainActivity>()) {
        scoped {

        }
    }
}

