package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    companion object {
        const val NEWS_KEY = "NEWS_KEY"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var news: News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState?.getParcelable<News>(NEWS_KEY) != null) {
            news = savedInstanceState.getParcelable<News>(NEWS_KEY) as News
        } else {
            news = News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at 15 pre-deal just weeks ago.",
                    "https://cdn.vox-cdn.com/thumbor/7YChMVyltceCz6Kv5UXXSoTpXBY=/0x146:2040x1214/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/15971132/elon_musk_tesla_3225.jpg",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen…",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12 11:38:12",
                    "https://seekingalpha.com/article/4413421-gsah-rumors-create-opportunity"
            )
        }

        binding.txtTitle.text = news.title
        binding.txtSubTitle.text = news.description
        binding.txtContent.text = news.content
        binding.txtAuthor.text = String.format(getString(R.string.news_source), news.author, news.source)
        binding.txtDate.text = String.format(getString(R.string.news_last_update), news.lastUpdate)

        val imgView = findViewById<ImageView>(R.id.image)
        Glide
                .with(this)
                .load("https://cdn.vox-cdn.com/thumbor/7YChMVyltceCz6Kv5UXXSoTpXBY=/0x146:2040x1214/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/15971132/elon_musk_tesla_3225.jpg")
                .placeholder(R.drawable.ic_no_image)
                .into(imgView);

    }

    fun OpenUrl(view: View) {
        val i = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://seekingalpha.com/article/4413421-gsah-rumors-create-opportunity")
        )
        startActivity(i)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(NEWS_KEY, news)
    }

}