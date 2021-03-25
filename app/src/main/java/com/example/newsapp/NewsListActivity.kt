package com.example.newsapp

import android.content.Intent
import android.os.Bundle
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
    private lateinit var adapter: NewsListAdapter
    lateinit var newsList: ArrayList<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val service = RetrofitInitializer.createNewsService()

        adapter = NewsListAdapter(this) {
            val navigateToDetailsIntent = Intent(this, NewsDetailsActivity::class.java)
            navigateToDetailsIntent.putExtra(NewsDetailsActivity.NEWS_KEY, it)
            startActivity(navigateToDetailsIntent)
        }

        binding.newsList.layoutManager = layoutManager
        binding.newsList.adapter = adapter

        if (savedInstanceState?.getParcelableArrayList<News>(NEWS_KEY) == null) {
            service.getTopHeadlines("us").enqueue(object : Callback<NewsList> {
                override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                    if (response.isSuccessful && response.body() != null) {
                        newsList = response.body()!!.items as ArrayList<News>
                        showList()
                    } else {
                        //Toast.makeText(this, "Internal Server Error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<NewsList>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        } else {
            newsList = savedInstanceState.getParcelableArrayList<News>(NEWS_KEY) as ArrayList<News>
            showList()
        }
    }

    fun showList() {
        adapter.setNews(newsList)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(NEWS_KEY, newsList)
    }
}