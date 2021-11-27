package com.carloscoding.newsapp.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carloscoding.newsapp.databinding.CategoryCheckboxItemBinding

class CheckboxAdapter :
    ListAdapter<CheckboxModel, CheckboxAdapter.CheckboxItemViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<CheckboxModel>() {
        override fun areItemsTheSame(oldItem: CheckboxModel, newItem: CheckboxModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CheckboxModel, newItem: CheckboxModel): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }
    }

    class CheckboxItemViewHolder(val binding: CategoryCheckboxItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckboxItemViewHolder {
        return CheckboxItemViewHolder(
            CategoryCheckboxItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CheckboxItemViewHolder, position: Int) {
        val checkboxItem = getItem(position)
        holder.binding.model = checkboxItem
    }
}