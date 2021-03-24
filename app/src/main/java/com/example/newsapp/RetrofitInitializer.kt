package com.example.newsapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun createNewsService() = retrofit.create(NewsApiService::class.java)
    }
}