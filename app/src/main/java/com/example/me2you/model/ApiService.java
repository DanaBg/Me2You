package com.example.me2you.model;

import com.example.me2you.SpinnerOption;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("countries/cities")
    Call<CitySearchResult> getCities(@Body CitiesRequest citiesRequest);
}
