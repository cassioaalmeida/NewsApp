package com.example.newsapp.presentation.scene.newsdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.News
import com.example.newsapp.presentation.common.Event

class NewsDetailViewModel(news: News) : ViewModel() {

    private val _newsDetail: MutableLiveData<News> = MutableLiveData()
    val newsDetail: LiveData<News>
        get() = _newsDetail

    private val _navigationUrl: MutableLiveData<Event<String>> = MutableLiveData()
    val navigationUrl: LiveData<Event<String>>
        get() = _navigationUrl

    private val _share: MutableLiveData<Event<String>> = MutableLiveData()
    val share: LiveData<Event<String>>
        get() = _share

    init {
        _newsDetail.value = news
    }

    fun onNavigationUrlClicked(url: String) {
        _navigationUrl.value = Event(url)
    }

    fun onShareClicked(url: String) {
        _share.value = Event(url)
    }

}