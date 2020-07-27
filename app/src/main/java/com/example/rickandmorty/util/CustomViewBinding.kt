package com.example.rickandmorty.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rickandmorty.R

/**
 * Created by PR72510 on 26/7/20.
 */

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    if (url != null && url.isNotBlank()) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.android)
            .error(R.drawable.android)
            .circleCrop()
            .into(this)
    }
}

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T?){
    if(recyclerView.adapter is BindableAdapter<*>){
        if (data != null) {
            (recyclerView.adapter as BindableAdapter<T>).setData(data)
        }
    }
}