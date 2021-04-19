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
    var notHandled: Boolean = true

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
        viewModel.screenState.observe(this) { screenState ->
            updateState(screenState)
        }

        viewModel.navigationDetail.observe(this) { newsEvent ->
            newsEvent.handledEvent { news ->
                val navigateToDetailsIntent = Intent(this, NewsDetailsActivity::class.java)
                navigateToDetailsIntent.putExtra(NewsDetailsActivity.NEWS_KEY, news)
                startActivity(navigateToDetailsIntent)
                notHandled = false
            }

        }

    }

    private fun updateState(screenState: ScreenState<List<News>>) {
        when (screenState) {
            is ScreenState.Success<List<News>> -> {
                adapter.setNews(screenState.Data)
                binding.progressIndicator.visibility = View.GONE
                binding.emptyStateIndicator.visibility = View.GONE
                binding.newsList.visibility = View.VISIBLE
            }
            is ScreenState.Loading -> {
                binding.newsList.visibility = View.GONE
                binding.emptyStateIndicator.visibility = View.GONE
                binding.progressIndicator.visibility = View.VISIBLE
            }
            is ScreenState.Error -> {
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