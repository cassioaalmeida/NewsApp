package com.example.newsapp.presentation.scene.newslist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.data.model.News
import com.example.newsapp.databinding.ActivityNewsListBinding
import com.example.newsapp.presentation.common.NewsListAdapter
import com.example.newsapp.presentation.common.ScreenState
import com.example.newsapp.presentation.scene.newsdetail.NewsDetailsActivity
import com.example.newsapp.presentation.scene.searchnews.SearchNewsActivity

class NewsListActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsListViewModel
    lateinit var binding: ActivityNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("ViewModel LifeCycle", "Obteve instancia do ViewModel")
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)

        binding.btnRefresh.setOnClickListener {
            viewModel.getDataFromService()
        }

        binding.fabSearchNews.setOnClickListener {
            viewModel.onSearchNewsClicked()
        }

        val adapter = NewsListAdapter(this)
        binding.newsList.adapter = adapter
        binding.newsList.layoutManager = LinearLayoutManager(this)

        viewModel.navigationDetail.observe(this) { newsEvent ->
            Log.i("LiveDataEvent", "Recebi navigation detail")
            newsEvent.handledEvent { news ->
                val navigateToDetailsIntent = Intent(this, NewsDetailsActivity::class.java)
                navigateToDetailsIntent.putExtra(NewsDetailsActivity.NEWS_KEY, news)
                startActivity(navigateToDetailsIntent)
            }
        }

        viewModel.navigationSearchNews.observe(this) { event ->
            event.handledEvent {
                val navigateToSearchNews = Intent(this, SearchNewsActivity::class.java)
                startActivity(navigateToSearchNews)
            }
        }

        viewModel.screenState.observe(this) { lastScreenState ->
            Log.i("LiveDataEvent", "Recebi screen state")
            when (lastScreenState) {
                is ScreenState.Success<List<News>> -> {
                    adapter.setData(lastScreenState.Data) { news ->
                        viewModel.onNewsItemClicked(news)
                    }
                    binding.progressIndicator.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.GONE
                    binding.newsList.visibility = View.VISIBLE
                }
                is ScreenState.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    binding.newsList.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.VISIBLE
                }
                is ScreenState.Loading -> {
                    binding.newsList.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.GONE
                    binding.progressIndicator.visibility = View.VISIBLE
                }
                else -> throw IllegalStateException("Unknown ScreenState")
            }
        }
    }
}