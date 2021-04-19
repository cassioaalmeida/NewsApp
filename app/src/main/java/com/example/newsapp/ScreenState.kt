package com.example.newsapp

sealed class ScreenState<out T> {
    class Success<out T>(val Data: T) : ScreenState<T>()
    class Error : ScreenState<Nothing>()
    class Loading : ScreenState<Nothing>()
}