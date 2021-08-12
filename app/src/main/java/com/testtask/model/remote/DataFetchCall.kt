package com.testtask.model.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.testtask.utils.Connectivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

abstract class DataFetchCall<ResultType>(private val responseLiveData: MutableLiveData<ApiResponse<ResultType>>, private val ctx: Context) {

    abstract suspend fun createCallAsync(): Response<ResultType>

    fun execute() {
        if (Connectivity.isConnected(ctx)) {
            responseLiveData.postValue(ApiResponse.loading())
            callNetworkData()
        } else {
            responseLiveData.postValue(
                ApiResponse.error(
                    ApiResponse.ApiError(
                        1001,
                        "Please check your internet connectivity"
                    )

                )
            )
        }
    }

    private fun callNetworkData() {
        GlobalScope.launch {
            try {
                val request = createCallAsync()
                if (request.isSuccessful) {
                    responseLiveData.postValue(ApiResponse.success(request.body()!!))
                } else {
                    responseLiveData.postValue(
                        ApiResponse.error(
                            ApiResponse.ApiError(
                                request.code(),
                                request.message(),
                                JSONObject(request.errorBody()!!.charStream().readText()).toString()
                            )

                        )
                    )
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                responseLiveData.postValue(
                    ApiResponse.error(
                        ApiResponse.ApiError(
                            500,
                            exception.message.toString()
                        )

                    )
                )
            }
        }
    }



}