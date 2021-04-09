package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.ActivityNewsListBinding

class NewsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsListBinding
    private lateinit var adapter: NewsListAdapter
    private lateinit var viewModel: NewsListViewModel
//    private val service = RetrofitInitializer.createNewsService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = NewsListAdapter(this) {
            viewModel.onNewsItemClicked(it)
        }

        binding.newsList.layoutManager = layoutManager
        binding.newsList.adapter = adapter

        binding.btnRefresh.setOnClickListener {
            viewModel.getDataFromService()
        }

        viewModel.newsList.observe(this) { updatedNewsList ->
            showList(updatedNewsList)
        }
        viewModel.screenState.observe(this) { screenState ->
            updateState(screenState)
        }

        viewModel.navigationDetail.observe(this) { news ->
            val navigateToDetailsIntent = Intent(this, NewsDetailsActivity::class.java)
            navigateToDetailsIntent.putExtra(NewsDetailsActivity.NEWS_KEY, news)
            startActivity(navigateToDetailsIntent)
        }

    }

    private fun updateState(screenState: ScreenState) {
        when (screenState) {
            ScreenState.SUCCESS -> {
                binding.progressIndicator.visibility = View.GONE
                binding.emptyStateIndicator.visibility = View.GONE
                binding.newsList.visibility = View.VISIBLE
            }
            ScreenState.LOADING -> {
                binding.newsList.visibility = View.GONE
                binding.emptyStateIndicator.visibility = View.GONE
                binding.progressIndicator.visibility = View.VISIBLE
            }
            ScreenState.ERROR -> {
                binding.progressIndicator.visibility = View.GONE
                binding.newsList.visibility = View.GONE
                binding.emptyStateIndicator.visibility = View.VISIBLE
            }
        }
    }

    private fun showList(newsList: List<News>) {
        adapter.setNews(newsList)
    }
}