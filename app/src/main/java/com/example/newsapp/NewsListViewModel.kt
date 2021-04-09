package com.example.newsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListViewModel : ViewModel() {

    private val _newsList: MutableLiveData<List<News>> = MutableLiveData()
    val newsList: LiveData<List<News>>
        get() = _newsList

    private val _screenState: MutableLiveData<ScreenState> = MutableLiveData()
    val screenState: LiveData<ScreenState>
        get() = _screenState

    private val _navigationDetail: MutableLiveData<News> = MutableLiveData()
    val navigationDetail: LiveData<News>
        get() = _navigationDetail

    private val service = RetrofitInitializer.createNewsService()

    init {
        getDataFromService()
    }

    fun getDataFromService() {
        _screenState.value = ScreenState.LOADING

        service.getTopHeadlines("us").enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.items.isNotEmpty()) {
                    _newsList.value = response.body()!!.items as ArrayList<News>
                    _screenState.value = ScreenState.SUCCESS
                } else {
                    _screenState.value = ScreenState.ERROR
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                _screenState.value = ScreenState.ERROR
            }
        })
    }

    fun onNewsItemClicked(news: News) {
        _navigationDetail.value = news
    }
}

