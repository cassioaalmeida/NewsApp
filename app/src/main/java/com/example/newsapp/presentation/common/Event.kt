package com.example.newsapp.presentation.common

class Event<T>(private val content: T) {
    private var hasBeenHandled: Boolean = false

    fun handledEvent(handleFunction: (T) -> Unit) {
        if (!hasBeenHandled) {
            handleFunction(content)
            hasBeenHandled = true
        }
    }
}