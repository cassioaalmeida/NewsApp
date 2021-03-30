package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
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
    private val service = RetrofitInitializer.createNewsService()
    lateinit var newsList: ArrayList<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = NewsListAdapter(this) {
            val navigateToDetailsIntent = Intent(this, NewsDetailsActivity::class.java)
            navigateToDetailsIntent.putExtra(NewsDetailsActivity.NEWS_KEY, it)
            startActivity(navigateToDetailsIntent)
        }

        binding.newsList.layoutManager = layoutManager
        binding.newsList.adapter = adapter

        binding.btnRefresh.setOnClickListener {
            getDataFromService()
        }


        if (savedInstanceState?.getParcelableArrayList<News>(NEWS_KEY) == null) {
            getDataFromService()
        } else {
            newsList = savedInstanceState.getParcelableArrayList<News>(NEWS_KEY) as ArrayList<News>
            showList()
        }

    }

    fun getDataFromService() {
        binding.newsList.visibility = View.GONE
        binding.emptyStateIndicator.visibility = View.GONE
        binding.progressIndicator.visibility = View.VISIBLE

        service.getTopHeadlines("us").enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.items.isNotEmpty()) {
                    newsList = response.body()!!.items as ArrayList<News>
                    showList()
                } else {
                    showEmptyState()
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                showEmptyState()
            }
        })
    }

    fun showList() {
        adapter.setNews(newsList)
        binding.progressIndicator.visibility = View.GONE
        binding.emptyStateIndicator.visibility = View.GONE
        binding.newsList.visibility = View.VISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(NEWS_KEY, newsList)
    }

    fun showEmptyState() {
        binding.progressIndicator.visibility = View.GONE
        binding.newsList.visibility = View.GONE
        binding.emptyStateIndicator.visibility = View.VISIBLE
    }
}