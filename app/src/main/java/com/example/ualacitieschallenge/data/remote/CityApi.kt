package com.example.ualacitieschallenge.data.remote

import com.example.ualacitieschallenge.data.model.City
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface CityApi {
    @GET
    suspend fun getCities(@Url url: String): Response<List<City>>
}