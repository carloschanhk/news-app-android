package com.carloscoding.newsapp.setting

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["dataset"], requireAll = true)
fun bindCategoriesToRecyclerView(recyclerView: RecyclerView, dataset: List<CheckboxModel>){
    Log.d("Testing", "bindCategoriesToRecyclerView: ")
    val adapter = recyclerView.adapter as CheckboxAdapter
    adapter.submitList(dataset)
}