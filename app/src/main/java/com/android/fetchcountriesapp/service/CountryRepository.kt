package com.android.fetchcountriesapp.service

import com.android.fetchcountriesapp.model.Country

class CountryRepository(
    private val fetchCountriesService: FetchCountriesService = RetrofitInstance.fetchCountriesService
) {

    suspend fun getCountries(): List<Country> {
        return fetchCountriesService.getCountriesList()
    }
}