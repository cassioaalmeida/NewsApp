package com.example.newsapp

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.NewsItemBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class NewsListAdapter(
        private val context: Context,
        private val onItemClickListener: ((news: News) -> Unit)
) : GroupAdapter<GroupieViewHolder>() {

    fun setNews(newsList: List<News>) {
        clear()
        newsList.forEach { add(NewsItem(it)) }
    }

    private inner class NewsItem(val news: News) : BindableItem<NewsItemBinding>() {

        override fun bind(viewBinding: NewsItemBinding, position: Int) {
            viewBinding.newsTitle.text = news.title
            Glide
                    .with(context)
                    .load(news.imageURL)
                    .centerCrop()
                    .into(viewBinding.newsImage)

            viewBinding.root.setOnClickListener {
                onItemClickListener.invoke(news)
            }
        }

        override fun getLayout(): Int = R.layout.news_item

        override fun initializeViewBinding(view: View): NewsItemBinding = NewsItemBinding.bind(view)

    }

}