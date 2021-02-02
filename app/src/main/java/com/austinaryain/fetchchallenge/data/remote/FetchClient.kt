package com.austinaryain.fetchchallenge.data.remote

import com.austinaryain.fetchchallenge.data.models.FetchItem
import retrofit2.http.GET

interface FetchClient {
    @GET("hiring.json")
    suspend fun getData(): List<FetchItem>
}
