package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ActivityNewsDetailsBinding


class NewsDetailsActivity : AppCompatActivity() {

    companion object {
        const val NEWS_KEY = "NEWS_DETAILS"
    }

    private lateinit var binding: ActivityNewsDetailsBinding
    private lateinit var news: News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getParcelableExtra<News>(NEWS_KEY) != null) {

            news = intent.getParcelableExtra<News>(NEWS_KEY)!!
            binding.txtTitle.text = news.title
            binding.txtSubTitle.text = news.description
            binding.txtContent.text = news.content
            binding.txtAuthor.text = String.format(getString(R.string.news_source), news.author, news.source)
            binding.txtDate.text = String.format(getString(R.string.news_last_update), news.lastUpdate)

            val imgView = findViewById<ImageView>(R.id.image)
            Glide
                    .with(this)
                    .load(news.imageURL)
                    .placeholder(R.drawable.ic_no_image)
                    .into(imgView);

            binding.btnViewMore.setOnClickListener {
                val webpage: Uri = Uri.parse(news.newsUrl)
                val seeEntireNewsIntent = Intent(Intent.ACTION_VIEW, webpage)
                if (seeEntireNewsIntent.resolveActivity(packageManager) != null) {
                    startActivity(seeEntireNewsIntent)
                } else {
                    Toast.makeText(this, getString(R.string.browser_needed), Toast.LENGTH_SHORT).show()
                }
            }
            binding.btnShare.setOnClickListener {
                val webpage: Uri = Uri.parse(news.newsUrl)
                val shareIntent = Intent(Intent.ACTION_SEND, webpage)

                startActivity(Intent.createChooser(shareIntent, webpage.toString()));
            }

        } else {
            Toast.makeText(this, getString(R.string.no_name), Toast.LENGTH_SHORT).show()
        }

    }

}