package com.android.fetchcountriesapp.service

import com.android.fetchcountriesapp.model.Country
import retrofit2.http.GET

interface FetchCountriesService {
    @GET("countries.json")
    suspend fun getCountriesList(): List<Country>
}