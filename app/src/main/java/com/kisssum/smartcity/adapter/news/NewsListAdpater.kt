package com.kisssum.smartcity.adapter.news

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kisssum.smartcity.R
import com.kisssum.smartcity.tool.API
import kotlin.collections.ArrayList

class NewsListAdpater(
    val context: Context,
    val data: ArrayList<Map<String, String>>,
    val isHome: Boolean,
    val isDetail: Boolean = false,
    val isSearch: Boolean = false
) : RecyclerView.Adapter<NewsListAdpater.DefaultViewModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list2, parent, false)
        return DefaultViewModel(view)
    }

    override fun onBindViewHolder(holder: DefaultViewModel, position: Int) {
        val map = data[position]

        Glide.with(context)
            .load(API.getBaseUrl() + map["cover"].toString())
            .override(200,300)
            .centerCrop()
            .into(holder.img)

        holder.title.text = map["title"].toString()
        holder.text.text = map["content"].toString()
        holder.comment.text = "${map["readNum"].toString()}人看过"

        if (isHome) {
            holder.time.text = map["createTime"].toString()
        } else {
            holder.time.text = "${map["likeNum"].toString()}人点赞"
        }

        holder.itemView.setOnClickListener { v: View? -> navNewsInformation(position) }
    }

    private fun navNewsInformation(i: Int) {
        findNavController((context as Activity), R.id.fragment_main).apply {
            val bundle = Bundle().apply {
                this.putInt("id", data[i]["id"].toString().toInt())
            }

            if (isSearch) {
                this.navigate(R.id.action_newsSearchFragment_to_newsDetailFragment, bundle)
            } else if (!isDetail) {
                this.navigate(R.id.action_navControlFragment_to_newsDetailFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class DefaultViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img = itemView.findViewById<ImageView>(R.id.newsImg)
        var title = itemView.findViewById<TextView>(R.id.newsTitle)
        var text = itemView.findViewById<TextView>(R.id.newsText)
        var comment = itemView.findViewById<TextView>(R.id.newsComment)
        var time = itemView.findViewById<TextView>(R.id.newsTime)
    }
}