package com.carloscoding.newsapp.home.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carloscoding.newsapp.R
import com.carloscoding.newsapp.data.Article
import com.google.android.material.tabs.TabLayout
import java.util.*

interface OnTabSelectedInterface {
    fun invoke(tab: HomePageEvent)
}

@BindingAdapter(value = ["categories","onTabSelected"],requireAll = true)
fun bindTextToTabLayout(view: TabLayout, categories: List<String>, onTabSelectedInterface: OnTabSelectedInterface) {
    categories.forEach {
        view.addTab(view.newTab().setText(it))
    }
    view.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                onTabSelectedInterface.invoke(HomePageEvent.OnToggleCategory(tab.text.toString()))
            }
        }
        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    })
}

@BindingAdapter(value=["dataset"],requireAll = true)
fun bindDataToRecyclerView(view: RecyclerView, dataset: List<Article>){
    val adapter = view.adapter as ArticleAdapter
    adapter.submitList(dataset)
}

@BindingAdapter(value = ["imageUrl"], requireAll = true)
fun bindUrlToImage(imageView: ImageView, imageUrl: String?){
    imageUrl?.let{
        if (it.isNotEmpty()){
            Glide.with(imageView).load(it).into(imageView)
        }
    }
}
@BindingAdapter(value = ["date"],requireAll = true)
fun bindDateToText(textView: TextView, date: Date){
    val writtenAt = Calendar.getInstance().apply {
        time = date
    }
    val currentTime = Calendar.getInstance()
    val dayDifference = currentTime.get(Calendar.DATE) - writtenAt.get(Calendar.DATE)
    if(dayDifference == 0){
        val hour = currentTime.get(Calendar.HOUR_OF_DAY) - writtenAt.get(Calendar.HOUR_OF_DAY)
        if (hour == 0){
            textView.text = textView.resources.getString(R.string.written_now)
        } else {
            textView.text = textView.resources.getQuantityString(R.plurals.written_in_hour, hour, hour)
        }
    } else {
        textView.text = textView.resources.getQuantityString(R.plurals.written_in_day, dayDifference, dayDifference)
    }
}