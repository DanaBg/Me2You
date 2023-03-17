package com.example.me2you.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityModel {
    final public static CityModel instance = new CityModel();

    final String BASE_URL = "https://countriesnow.space/api/v0.1/";
    Retrofit retrofit;
    ApiService cityApi;

    private CityModel(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        cityApi = retrofit.create(ApiService.class);
    }

    public LiveData<List<String>> getCities(){
        MutableLiveData<List<String>> data = new MutableLiveData<>();
        Call<CitySearchResult> call = cityApi.getCities(new CitiesRequest("Israel"));
        call.enqueue(new Callback<CitySearchResult>() {
            @Override
            public void onResponse(Call<CitySearchResult> call, Response<CitySearchResult> response) {
                if (response.isSuccessful()){
                    CitySearchResult res = response.body();
                    data.setValue(res.getData());
                }else{
                    Log.d("TAG","----- getCities response error");
                }
            }

            @Override
            public void onFailure(Call<CitySearchResult> call, Throwable t) {
                Log.d("TAG","----- getCities fail");
            }
        });
        return data;
    }
}
