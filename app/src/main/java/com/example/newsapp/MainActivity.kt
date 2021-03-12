package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun OpenUrl(view: View) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://seekingalpha.com/article/4413421-gsah-rumors-create-opportunity"))
        startActivity(i)
    }
}