package com.android.fetchcountriesapp.viewmodel

import com.android.fetchcountriesapp.domain.FetchCountriesUseCase
import com.android.fetchcountriesapp.model.Country
import com.android.fetchcountriesapp.model.Currency
import com.android.fetchcountriesapp.model.Language
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.unmockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description


class FetchCountriesViewModelTest {

    private val viewModel = FetchCountriesViewModel()

    @get:Rule
    val mockkRule = MockKRule(this)


    @MockK
    lateinit var fetchCountriesUseCaseMock: FetchCountriesUseCase


    private val fakeResults = listOf(
        Country(
            name = "fakeCountry",
            capital = "fakeCapital",
            region = "fakeRegion",
            code = "fakeCode",
            currency = Currency(null, null, name = "USD", null),
            flag = "en",
            language = Language("en", "english")

        )
    )

    @Before
    fun setUp() {
        coEvery { fetchCountriesUseCaseMock.getCountries() } returns fakeResults
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        unmockkObject(fetchCountriesUseCaseMock)
        Dispatchers.resetMain()
    }

    @Test
    fun `test get countries returns results`() = runTest {
        val countries = async { fetchCountriesUseCaseMock.getCountries() }.await()
        assert(countries.size == 1)
    }
}