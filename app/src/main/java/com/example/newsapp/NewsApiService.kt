package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    companion object {
        private const val API_KEY = "2bdd0fa2d9154a938b396932747faf09"
    }

    @GET("top-headlines?apiKey=$API_KEY")
    fun getTopHeadlines(@Query("country") country: String): Call<NewsList>
    

}