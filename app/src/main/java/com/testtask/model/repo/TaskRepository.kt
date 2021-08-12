package com.testtask.model.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.testtask.model.local.preference.PreferenceHelper
import com.testtask.model.pojo.HomeResponse
import com.testtask.model.remote.ApiResponse
import com.testtask.model.remote.ApiServices
import com.testtask.model.remote.DataFetchCall
import org.koin.core.KoinComponent
import retrofit2.Response


class TaskRepository(
    private val apiServices: ApiServices,
    private val preferences: PreferenceHelper,
    val androidContext: Context,
) : KoinComponent {

    fun getComments(
            commentLiveData: MutableLiveData<ApiResponse<HomeResponse>>
    ) {
        object : DataFetchCall<HomeResponse>(commentLiveData, androidContext) {
            override suspend fun createCallAsync(): Response<HomeResponse> {
                return apiServices.getComments()
            }
        }.execute()
    }

}