package com.example.countryinfo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.countryinfo.CountryResponse
import com.example.countryinfo.CountryService
import com.example.countryinfo.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryInfoViewModel : ViewModel() {
    private val _country = MutableStateFlow<CountryResponse?>(null)
    val country: StateFlow<CountryResponse?> get() = _country

    private val countryService = RetrofitClient.getRetrofitInstance().create(CountryService::class.java)

    fun fetchRandomCountry() {
        val call = countryService.getRandomCountry()
        call.enqueue(object : Callback<CountryResponse> {
            override fun onResponse(call: Call<CountryResponse>, response: Response<CountryResponse>) {
                if (response.isSuccessful) {
                    _country.update { response.body() }
                    Log.d("CountryInfoViewModel", "Random country fetched: ${response.body()}")
                } else {
                    Log.e("API Error", "Error code: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CountryResponse>, t: Throwable) {
                Log.e("API Failure", "Error: ${t.message}")
            }
        })
    }

    fun fetchCountryByName(name: String) {
        val call = countryService.searchCountry(name)
        call.enqueue(object : Callback<CountryResponse> {
            override fun onResponse(call: Call<CountryResponse>, response: Response<CountryResponse>) {
                if (response.isSuccessful) {
                    _country.update { response.body() }
                    Log.d("CountryInfoViewModel", "Country fetched by name: ${response.body()}")
                } else {
                    _country.update {
                        null
                    }
                    Log.e("API Error", "Error code: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CountryResponse>, t: Throwable) {
                Log.e("API Failure", "Error: ${t.message}")
            }
        })
    }
}
