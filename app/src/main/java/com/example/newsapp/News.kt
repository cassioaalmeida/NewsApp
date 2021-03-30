package com.example.newsapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
        var title: String,
        var description: String?,
        @SerializedName("urlToImage")
        var imageURL: String?,
        var content: String?,
        var author: String,
        var source: Source,
        @SerializedName("publishedAt")
        var lastUpdate: String,
        @SerializedName("url")
        var newsUrl: String
) : Parcelable {
    @Parcelize
    data class Source(
            val id: String?,
            val name: String
    ) : Parcelable
}