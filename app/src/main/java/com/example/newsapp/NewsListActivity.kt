package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.ActivityNewsListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListActivity : AppCompatActivity() {

    companion object {
        const val NEWS_KEY = "NEWS_DETAILS"
    }

    private lateinit var binding: ActivityNewsListBinding
    lateinit var newsList: ArrayList<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = RetrofitInitializer.createNewsService()

        service.getTopHeadlines().enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        newsList = savedInstanceState?.getParcelableArrayList<News>(NEWS_KEY)
            ?: arrayListOf(
                News(
                    "Elon Musk tweets about ‘new drug called Regretamine’, triggering wave of memes and Dogecoin references",
                    "‘New drug coming out called Regretamine. Pop one & all regrets are gone’",
                    "https://static.independent.co.uk/2021/03/03/10/2021-02-15T135548Z_2064936280_RC21TL9X2BL9_RTRMADP_3_RUSSIA-PUTIN-CLUBHOUSE.JPG?width=1200&auto=webp&quality=75",
                    "Tesla and SpaceX chief Elon Musk on Tuesday posted about a new drug called Regretamine, baffling some users and triggering a wave of memes and references to his power as a market tipster.\r\nNew drug c… [+2380 chars]",
                    "Namita Singh",
                    "Independent",
                    "2021-03-03T12:24:07Z",
                    "https://www.independent.co.uk/news/world/americas/elon-musk-regretamine-twitter-b1811623.html"
                ),
                News(
                    "Elon Musk tweets about ‘new drug called Regretamine’, triggering wave of memes and Dogecoin references",
                    "‘New drug coming out called Regretamine. Pop one & all regrets are gone’",
                    "https://static.independent.co.uk/2021/03/03/10/2021-02-15T135548Z_2064936280_RC21TL9X2BL9_RTRMADP_3_RUSSIA-PUTIN-CLUBHOUSE.JPG?width=1200&auto=webp&quality=75",
                    "Tesla and SpaceX chief Elon Musk on Tuesday posted about a new drug called Regretamine, baffling some users and triggering a wave of memes and references to his power as a market tipster.\r\nNew drug c… [+2380 chars]",
                    "Namita Singh",
                    "Independent",
                    "2021-03-03T12:24:07Z",
                    "https://www.independent.co.uk/news/world/americas/elon-musk-regretamine-twitter-b1811623.html"
                ),
                News(
                    "Elon Musk tweets about ‘new drug called Regretamine’, triggering wave of memes and Dogecoin references",
                    "‘New drug coming out called Regretamine. Pop one & all regrets are gone’",
                    "https://static.independent.co.uk/2021/03/03/10/2021-02-15T135548Z_2064936280_RC21TL9X2BL9_RTRMADP_3_RUSSIA-PUTIN-CLUBHOUSE.JPG?width=1200&auto=webp&quality=75",
                    "Tesla and SpaceX chief Elon Musk on Tuesday posted about a new drug called Regretamine, baffling some users and triggering a wave of memes and references to his power as a market tipster.\r\nNew drug c… [+2380 chars]",
                    "Namita Singh",
                    "Independent",
                    "2021-03-03T12:24:07Z",
                    "https://www.independent.co.uk/news/world/americas/elon-musk-regretamine-twitter-b1811623.html"
                )
            )

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapter = NewsListAdapter(this) {
            val navigateToDetailsIntent = Intent(this, NewsDetailsActivity::class.java)
            navigateToDetailsIntent.putExtra(NewsDetailsActivity.NEWS_KEY, it)
            startActivity(navigateToDetailsIntent)
        }
        adapter.setHeader("Header") { text ->
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
        adapter.setNews(newsList)

        binding.newsList.layoutManager = layoutManager
        binding.newsList.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(NEWS_KEY, newsList)
    }
}