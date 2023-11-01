package com.android.fetchcountriesapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.fetchcountriesapp.domain.FetchCountriesUseCase
import com.android.fetchcountriesapp.model.Country
import com.android.fetchcountriesapp.service.CountryRepository
import kotlinx.coroutines.launch

class FetchCountriesViewModel(
    private val repository: CountryRepository = CountryRepository()
) : ViewModel() {


    private val _countriesList = MutableLiveData<List<Country>>()
    val countriesList: LiveData<List<Country>> = _countriesList
    private val _isNetworkOperationInProgress = MutableLiveData<Boolean>()
    val isNetworkOperationInProgress: LiveData<Boolean> = _isNetworkOperationInProgress

    fun fetchRewards() {
        if(_countriesList.value.isNullOrEmpty()) {
            _isNetworkOperationInProgress.value = true
            viewModelScope.launch {
                try {
                    val networkReturnedCountriesList = FetchCountriesUseCase(repository).getCountries()
                    _countriesList.value = networkReturnedCountriesList
                    _isNetworkOperationInProgress.value = false

                } catch (e: Exception) {
                    Log.e("FetchCountries", "Failure to fetch rewards: ${e.printStackTrace()}")
                    _countriesList.value = emptyList()
                    _isNetworkOperationInProgress.value = false
                }
            }
        }
        else {
            _isNetworkOperationInProgress.value = false
        }
    }
}