package com.example.data.http

import com.example.data.http.model.CompletionMapData
import com.example.data.http.model.CreatePreSignURLRequest
import com.example.data.http.model.PreSignURLResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ExampleApiService {
    @POST("create-presign-url")
    suspend fun preSignURL(
        @Body bodyRequest: CreatePreSignURLRequest,
    ): PreSignURLResponse

    @GET("get-completion-map-data")
    suspend fun getCompletionMapData(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("modelId") modelId: String,
    ): CompletionMapData
}
