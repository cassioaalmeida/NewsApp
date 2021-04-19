package com.example.newsapp.data.repository

import com.example.newsapp.data.cache.NewsCDS
import com.example.newsapp.data.model.News
import com.example.newsapp.data.model.NewsList
import com.example.newsapp.data.remote.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    private val newsRDS = RetrofitInitializer.getNewsApiService()
    private val newsCDS = NewsCDS()

    fun getNewsList(
        onSuccess: (List<News>) -> Unit,
        onError: () -> Unit
    ) {
        newsRDS.getTopHeadlines("br").enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.items.isNotEmpty()) {
                    val remoteNews: List<News> = response.body()!!.items
                    newsCDS.saveNewsList(remoteNews)
                    onSuccess(remoteNews)
                } else {
                    val cacheNews: List<News>? = newsCDS.getNewsList()
                    if (cacheNews != null) {
                        onSuccess(cacheNews)
                    } else {
                        onError()
                    }
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                val cacheNews: List<News>? = newsCDS.getNewsList()
                if (cacheNews != null) {
                    onSuccess(cacheNews)
                } else {
                    onError()
                }
            }
        })
    }
}