package com.carloscoding.newsapp.home.ui

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2

@BindingAdapter(value = ["categories"], requireAll = true)
fun bindCategoriesToViewPager(pager: ViewPager2, categories: List<String>) {
    val adapter = pager.adapter as HomePagerAdapter
    adapter.dataset = categories
    adapter.notifyDataSetChanged()
}
