package com.android.fetchcountriesapp.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.fetchcountriesapp.R
import com.android.fetchcountriesapp.databinding.FragmentCountriesBinding
import com.android.fetchcountriesapp.viewmodel.FetchCountriesViewModel


class CountriesListFragment : Fragment() {
    /**
     * FragmentCountriesBinding
     */
    private lateinit var binding: FragmentCountriesBinding

    /**
     * CountriesAdapter
     */
    private lateinit var countriesAdapter: CountriesAdapter

    /**
     * CountriesViewModel
     */
    private val viewModel: FetchCountriesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_countries, container, false)
        viewModel.fetchRewards()
        viewModel.countriesList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                countriesAdapter.submitList(it)
            } else if (it.isEmpty()) {
                Toast.makeText(
                    this.context,
                    "There is potentially an error fetching your results. Please check your internet connection",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        viewModel.isNetworkOperationInProgress.observe(viewLifecycleOwner) {
            toggleProgressBar(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        countriesAdapter = CountriesAdapter()

        with(binding) {
            countriesList.adapter = countriesAdapter
            swipeRefresh.setOnRefreshListener {
                viewModel.fetchRewards()
            }
        }
    }

    /**
     * Toggle progress bar
     */
    private fun toggleProgressBar(show: Boolean) {
        binding.swipeRefresh.isRefreshing = show
    }
}