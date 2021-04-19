package com.example.newsapp.data.repository

import com.example.newsapp.data.model.News
import com.example.newsapp.data.model.NewsList
import com.example.newsapp.data.remote.RetrofitInitializer
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    companion object {
        private const val NEWS_LIST_KEY = "NEWS_LIST_KEY"
    }

    private val service = RetrofitInitializer.createNewsService()

    fun getNewsList(
        onSuccess: (List<News>) -> Unit,
        onError: () -> Unit
    ) {
        service.getTopHeadlines("br").enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.items.isNotEmpty()) {
                    val remoteNews: List<News> = response.body()!!.items
                    saveInCache(remoteNews)
                    onSuccess(remoteNews)
                } else {
                    val cacheNews: List<News>? = getDataFromCache()
                    if (cacheNews != null) {
                        onSuccess(cacheNews)
                    } else {
                        onError()
                    }
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                val cacheNews: List<News>? = getDataFromCache()
                if (cacheNews != null) {
                    onSuccess(cacheNews)
                } else {
                    onError()
                }
            }
        })
    }

    private fun saveInCache(newsList: List<News>) {
        Paper.book().write(NEWS_LIST_KEY, newsList)
    }

    private fun getDataFromCache(): List<News>? {
        return Paper.book().read(NEWS_LIST_KEY)
    }
}