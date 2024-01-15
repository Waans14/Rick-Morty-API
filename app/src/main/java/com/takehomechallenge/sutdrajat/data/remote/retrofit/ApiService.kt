package com.takehomechallenge.sutdrajat.data.remote.retrofit

import com.takehomechallenge.sutdrajat.data.remote.response.KarakterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character/")
    fun getKarakters(
        @Query("page")
        page: Int
    ): Call<KarakterResponse>

    @GET("character/")
    fun searchKarakters(
        @Query("page")
        page: Int,
        @Query("name")
        query: String
    ): Call<KarakterResponse>

}