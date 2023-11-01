package com.android.fetchcountriesapp.domain

import com.android.fetchcountriesapp.model.Country
import com.android.fetchcountriesapp.service.CountryRepository

class  FetchCountriesUseCase constructor(
    private val countryRepository: CountryRepository
){

    /**
     * Get countries use case.
     */
    suspend fun getCountries() : List<Country> = countryRepository.getCountries()
}