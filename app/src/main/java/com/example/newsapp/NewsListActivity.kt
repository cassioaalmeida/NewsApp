package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.databinding.ActivityNewsListBinding

class NewsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNavigationTest.setOnClickListener {
            val navigateToDetailsIntent = Intent(this, MainActivity::class.java)
            startActivity(navigateToDetailsIntent)
        }
    }
}