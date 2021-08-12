package com.testtask.model.remote

import com.testtask.model.pojo.HomeResponse
import retrofit2.Response
import retrofit2.http.GET


interface ApiServices {

    @GET("comments")
    suspend fun getComments(): Response<HomeResponse>


}