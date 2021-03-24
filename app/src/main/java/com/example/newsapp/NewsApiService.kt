package com.example.newsapp

import retrofit2.Call
import retrofit2.http.GET

interface NewsApiService {
    companion object {
        private const val API_KEY = "2bdd0fa2d9154a938b396932747faf09"
    }

    @GET("top-headlines?apikey=$API_KEY")
    fun getTopHeadlines(): Call<NewsList>

}