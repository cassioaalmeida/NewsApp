package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class NewsList(
    @SerializedName("articles")
    val items: List<News>
)