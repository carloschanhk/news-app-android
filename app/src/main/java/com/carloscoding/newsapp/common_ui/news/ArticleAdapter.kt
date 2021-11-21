package com.carloscoding.newsapp.common_ui.news

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carloscoding.newsapp.data.Article
import com.carloscoding.newsapp.databinding.ArticleCardBinding

class ArticleAdapter : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback) {
    var onNewsItemClickListener: ((article: Article) -> Unit)? = null
    var onNewsItemLongClickListener: ((article: Article) -> Unit)? = null

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem


        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.content == newItem.content

    }

    class ArticleViewHolder(val binding: ArticleCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.article = article
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ArticleCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.apply {
            bind(article)
            itemView.apply {
                setOnClickListener {
                    onNewsItemClickListener?.invoke(article)
                }
                setOnLongClickListener{
                    onNewsItemLongClickListener?.invoke(article)
                    true
                }
            }
        }

    }
}