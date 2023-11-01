package com.android.fetchcountriesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.android.fetchcountriesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = setContentView(this, R.layout.activity_main)
            supportActionBar?.title = getString(R.string.app_name)
        }
}
