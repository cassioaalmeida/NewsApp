package com.example.newsapp.presentation.scene.searchnews

import android.net.Uri
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.R
import com.example.newsapp.data.model.News
import com.example.newsapp.data.model.NewsList
import com.example.newsapp.data.remote.RetrofitInitializer
import com.example.newsapp.presentation.common.Event
import com.example.newsapp.presentation.common.ScreenState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule

class SearchNewsViewModel : ViewModel() {

    private val service = RetrofitInitializer.createNewsService()

    private var timer = Timer()

    private val _screenState: MutableLiveData<ScreenState<List<News>>> = MutableLiveData()
    val screenState: LiveData<ScreenState<List<News>>>
        get() = _screenState

    private val _navigationShowEntireNews: MutableLiveData<Event<Uri>> = MutableLiveData()
    val navigationShowEntireNews: LiveData<Event<Uri>>
        get() = _navigationShowEntireNews

    private val _message: MutableLiveData<Event<Int>> = MutableLiveData()
    val message: LiveData<Event<Int>>
        get() = _message

    fun onSearchEditTextChanged(text: CharSequence?) {

        timer.cancel()
        timer = Timer()
        timer.schedule(2000) {
            searchNews(text.toString())
        }
    }

    fun onNewsItemClicked(clickedNews: News) {
        if (URLUtil.isValidUrl(clickedNews.newsUrl)) {
            val webpage: Uri = Uri.parse(clickedNews.newsUrl)
            _navigationShowEntireNews.value = Event(webpage)
        } else {
            _message.value = Event(R.string.invalid_url)
        }
    }

    fun onShowNewsResolveActivityFail() {
        _screenState.postValue(ScreenState.Loading())
    }

    private fun searchNews(searchText: String) {
        service.getEverything(searchText).enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null) {
                    _screenState.postValue(ScreenState.Success(response.body()!!.items as ArrayList<News>))
                } else {
                    _screenState.postValue(ScreenState.Error())
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                _screenState.postValue(ScreenState.Error())
            }

        })
    }
}
