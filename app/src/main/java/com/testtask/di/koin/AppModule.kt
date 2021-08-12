package com.testtask.di.koin

import android.content.Context
import com.testtask.model.remote.ApiServices
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.testtask.di.app.MyTaskApplication
import com.testtask.model.local.preference.PreferenceConstants
import com.testtask.model.local.preference.PreferenceHelper
import com.testtask.model.remote.ReflectionUtil
import com.testtask.utils.ApiConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

val appModule = module {

    /** PROVIDE GSON SINGLETON */
    single<Gson> {
        val builder =
                GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        builder.setLenient().create()
    }
    /** Provide Preference Helper singleton Object
     * private val preferenceHelper : PreferenceHelper by inject() */

    single {
        PreferenceHelper(
                (androidApplication() as MyTaskApplication).getSharedPreferences(
                        PreferenceConstants.PREFERENCE_NAME,
                        Context.MODE_PRIVATE
                )
        )
    }

    /** PROVIDE RETROFIT SINGLETON */
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(loggingInterceptor)
        httpClient.connectTimeout(ApiConstant.API_TIME_OUT, TimeUnit.SECONDS)
        httpClient.readTimeout(ApiConstant.API_TIME_OUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(ApiConstant.API_TIME_OUT, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder().build()
            chain.proceed(request)
        }
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")


                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }
        })
        val okHttpClient = httpClient.build()

        Retrofit.Builder()
                .baseUrl(ApiConstant.getBaseURL())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(get() as Gson))
                .build()

    }

    /*** Provide API Service Singleton*/
    single {
        (get<Retrofit>()).create<ApiServices>(ApiServices::class.java)
    }
    single { ReflectionUtil(get()) }


}