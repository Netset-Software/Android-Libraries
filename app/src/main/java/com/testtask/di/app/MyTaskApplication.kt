package com.testtask.di.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.testtask.di.koin.appModule
import com.testtask.di.koin.homeActivityModule
import com.testtask.di.koin.repoModule
import com.testtask.di.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MyTaskApplication: MultiDexApplication(), Application.ActivityLifecycleCallbacks {

    var mCurrencyActivity: Activity? = null
    var isBackground: Boolean = true

    companion object {
        lateinit var mApplicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mApplicationContext = this@MyTaskApplication
        startKoin {
            androidLogger()
            androidContext(this@MyTaskApplication)
            modules(getModule())
        }
        registerActivityLifecycleCallbacks(this@MyTaskApplication)


    }

    /*** function to get all di modules array***/
    private fun getModule(): Iterable<Module> {
        return listOf(appModule, viewModelModule, repoModule,homeActivityModule)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        isBackground = false
        mCurrencyActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        isBackground = true
        mCurrencyActivity = activity

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}