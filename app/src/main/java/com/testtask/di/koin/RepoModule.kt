package com.testtask.di.koin

import com.testtask.model.repo.AppRepository
import com.testtask.model.repo.TaskRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repoModule = module {

    /**
     * Provide ContactRepository class Singleton object
     * you can use it any KoinComponent class  below is declaration
     * private val globalRepository: ContactRepository by inject()
     **/

    single { TaskRepository(get(), get(), androidContext()) }
    single { AppRepository(get()) }

}