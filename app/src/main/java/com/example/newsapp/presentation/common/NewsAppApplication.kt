package com.example.newsapp.presentation.common

import android.app.Application
import io.paperdb.Paper

class NewsAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Paper.init(this)
    }
}