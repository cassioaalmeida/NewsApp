package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ActivityNewsDetailsBinding


class NewsDetailsActivity : AppCompatActivity() {

    companion object {
        const val NEWS_KEY = "NEWS_DETAILS"
    }

    private lateinit var binding: ActivityNewsDetailsBinding
    private lateinit var viewModel: NewsDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = NewsDetailViewModelFactory(intent.getParcelableExtra<News>(NEWS_KEY)!!)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsDetailViewModel::class.java)


        binding.txtTitle.text = viewModel.news.title
        binding.txtSubTitle.text = viewModel.news.description ?: ""
        binding.txtContent.text = viewModel.news.content ?: ""
        binding.txtAuthor.text = String.format(getString(R.string.news_source), viewModel.news.author, viewModel.news.source.name)
        binding.txtDate.text = String.format(getString(R.string.news_last_update), viewModel.news.lastUpdate)

        val imgView = findViewById<ImageView>(R.id.image)
        Glide
                .with(this)
                .load(viewModel.news.imageURL)
                .placeholder(R.drawable.ic_no_image)
                .into(imgView);

        binding.btnViewMore.setOnClickListener {
            val webpage: Uri = Uri.parse(viewModel.news.newsUrl)
            val seeEntireNewsIntent = Intent(Intent.ACTION_VIEW, webpage)
            if (seeEntireNewsIntent.resolveActivity(packageManager) != null) {
                startActivity(seeEntireNewsIntent)
            } else {
                Toast.makeText(this, getString(R.string.browser_needed), Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnShare.setOnClickListener {
            val webpage: Uri = Uri.parse(viewModel.news.newsUrl)
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, webpage)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

    }

}