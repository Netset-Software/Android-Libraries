package com.testtask.di.koin

import com.testtask.ui.base.BaseViewModel
import com.testtask.ui.home.HomeViewModel
import com.testtask.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel { BaseViewModel() }
    viewModel { MainViewModel(get(), androidContext()) }
    viewModel { HomeViewModel(get(), androidContext()) }
}