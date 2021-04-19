package com.example.newsapp.presentation.scene.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.News
import com.example.newsapp.data.model.NewsList
import com.example.newsapp.data.remote.RetrofitInitializer
import com.example.newsapp.presentation.common.Event
import com.example.newsapp.presentation.common.ScreenState
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListViewModel : ViewModel() {
    companion object {
        private const val NEWS_LIST_KEY = "NEWS_LIST_KEY"
    }

    private val _screenState: MutableLiveData<ScreenState<List<News>>> = MutableLiveData()
    val screenState: LiveData<ScreenState<List<News>>>
        get() = _screenState

    private val _navigationDetail: MutableLiveData<Event<News>> = MutableLiveData()
    val navigationDetail: LiveData<Event<News>>
        get() = _navigationDetail

    private val _navigationSearchNews: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigationSearchNews: LiveData<Event<Unit>>
        get() = _navigationSearchNews

    private val service = RetrofitInitializer.createNewsService()

    init {
        getDataFromService()
    }

    fun onSearchNewsClicked() {
        _navigationSearchNews.value = Event(Unit)
    }

    fun getDataFromService() {
        _screenState.value = ScreenState.Loading()

        service.getTopHeadlines("us").enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.items.isNotEmpty()) {
                    val remoteNews: List<News> = response.body()!!.items
                    saveInCache(remoteNews)
                    _screenState.value = ScreenState.Success(remoteNews)
                } else {
                    val cacheNews: List<News>? = getDataFromCache()
                    if (cacheNews != null) {
                        _screenState.value = ScreenState.Success(cacheNews)
                    } else {
                        _screenState.value = ScreenState.Error()
                    }
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                val cacheNews: List<News>? = getDataFromCache()
                if (cacheNews != null) {
                    _screenState.value = ScreenState.Success(cacheNews)
                } else {
                    _screenState.value = ScreenState.Error()
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

    fun onNewsItemClicked(news: News) {
        _navigationDetail.value = Event(news)
    }
}

