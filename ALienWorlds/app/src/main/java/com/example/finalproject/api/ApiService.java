package com.example.finalproject.api;

import com.example.finalproject.model.Planet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiService {
    String RAPID_API_KEY = "ee2a58a1b8msh0c4d6fe69a66fc4p11df17jsn1925cebddb0f";
    String RAPID_API_HOST = "planets-info-by-newbapi.p.rapidapi.com";

    @Headers({
            "X-RapidAPI-Key: " + RAPID_API_KEY,
            "X-RapidAPI-Host: " + RAPID_API_HOST
    })
    @GET("planets/")
    Call<List<Planet>> getPlanets();
}
