package com.example.newsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsDetailViewModel(news: News) : ViewModel() {

    private val _newsDetail: MutableLiveData<News> = MutableLiveData()
    val newsDetail: LiveData<News>
        get() = _newsDetail

    private val _navigationUrl: MutableLiveData<String> = MutableLiveData()
    val navigationUrl: LiveData<String>
        get() = _navigationUrl

    private val _share: MutableLiveData<String> = MutableLiveData()
    val share: LiveData<String>
        get() = _share

    init {
        _newsDetail.value = news
    }

    fun onNavigationUrlClicked(url: String) {
        _navigationUrl.value = url
    }

    fun onShareClicked(url: String) {
        _share.value = url
    }

}