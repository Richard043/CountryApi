package com.example.countryinfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountryService {
    @GET("/search")
    Call<CountryResponse> searchCountry(@Query("country") String country);

    @GET("/random")
    Call<CountryResponse> getRandomCountry();
}
