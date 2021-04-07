package com.example.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsDetailViewModelFactory(val news: News) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel(news) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}