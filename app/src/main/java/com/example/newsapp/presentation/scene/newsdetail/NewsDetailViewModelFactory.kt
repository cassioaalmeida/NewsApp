package com.example.newsapp.presentation.scene.newsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.model.News

class NewsDetailViewModelFactory(val news: News) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel(news) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}