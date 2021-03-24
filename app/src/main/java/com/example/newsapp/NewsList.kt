package com.example.newsapp

import com.google.gson.annotations.SerializedName

data class NewsList(
    @SerializedName("articles")
    val items: List<News>
)