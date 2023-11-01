package com.android.fetchcountriesapp.view

import androidx.recyclerview.widget.RecyclerView
import com.android.fetchcountriesapp.model.Country
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.android.fetchcountriesapp.R
import com.android.fetchcountriesapp.databinding.CountriesListItemBinding

class CountriesAdapter : ListAdapter<Country, CountriesAdapter.CountriesViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(
                oldItem: Country,
                newItem: Country
            ): Boolean = oldItem.name == newItem.name


            override fun areContentsTheSame(
                oldItem: Country,
                newItem: Country
            ): Boolean =
                oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val binding: CountriesListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.countries_list_item,
            parent, false
        )
        return CountriesViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val country = getItem(position)
        if (country != null) {
            holder.bind(country)
            holder.bindRegion(country.name + ", "+ country.region)
        }
    }

    inner class CountriesViewHolder(private val binding: CountriesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            with(binding) {
                this.country = country
            }
        }
        fun bindRegion(countryRegion: String){
            with(binding) {
                this.countryRegion = countryRegion
            }
        }
    }
}