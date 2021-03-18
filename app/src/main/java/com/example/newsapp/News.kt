package com.example.newsapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
        var title: String,
        var description: String,
        var imageURL: String,
        var content: String,
        var author: String,
        var source: String,
        var lastUpdate: String,
        var newsUrl: String
) : Parcelable