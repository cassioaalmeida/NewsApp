package com.example.newsapp.data.remote

import com.example.newsapp.data.model.NewsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRDS {
    companion object {
        private const val API_KEY = "2bdd0fa2d9154a938b396932747faf09"
    }

    @GET("top-headlines?apiKey=$API_KEY")
    fun getTopHeadlines(@Query("country") country: String): Call<NewsList>

    @GET("everything?$API_KEY")
    fun getEverything(@Query("q") query: String): Call<NewsList>
}