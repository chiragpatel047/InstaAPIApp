package com.chirag047.InstaSave.api

import com.chirag047.InstaSave.Common.API_KEY
import com.chirag047.InstaSave.models.InstaModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface InstaApi {

    @GET("/index")
    suspend fun getVideo(
        @Query("url") url: String,
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") apiHost: String
    ): Response<InstaModel>

}