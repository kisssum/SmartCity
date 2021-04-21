package com.kisssum.smartcity.adapter.news

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.*
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.kisssum.smartcity.R
import com.kisssum.smartcity.tool.API
import kotlin.collections.ArrayList

class NewsListAdpater(val context: Context, val data: ArrayList<Map<String, Any>>, val isHome: Boolean, val isDetail: Boolean = false) : RecyclerView.Adapter<NewsListAdpater.DefaultViewModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list2, parent, false)
        return DefaultViewModel(view)
    }

    override fun onBindViewHolder(holder: DefaultViewModel, position: Int) {
        val map = data[position]

        Volley.newRequestQueue(context).apply {
            val imageRequest = ImageRequest(
                    API.getBaseUrl(context) + map["imgUrl"].toString(),
                    {
                        holder.img.setImageBitmap(it)
                    },
                    0, 0, Bitmap.Config.RGB_565,
                    {}
            )

            this.add(imageRequest)
        }

        holder.title.text = map["title"].toString()
        holder.text.text = map["content"].toString()
        holder.comment.text = "${map["viewsNumber"].toString()}人看过"

        if (isHome) {
            holder.time.text = map["createTime"].toString()
        } else {
            holder.time.text = "${map["likeNumber"].toString()}人点赞"
        }

        holder.itemView.setOnClickListener { v: View? -> navNewsInformation(position) }
    }

    private fun navNewsInformation(i: Int) {
        findNavController((context as Activity), R.id.fragment_main).apply {
            val bundle = Bundle().apply {
                this.putInt("id", data[i]["id"].toString().toInt())
                this.putString("title", data[i]["title"].toString())
                this.putString("content", data[i]["content"].toString())
                this.putString("imgUrl", data[i]["imgUrl"].toString())
                this.putString("likeNumber", data[i]["likeNumber"].toString())
                this.putString("viewsNumber", data[i]["viewsNumber"].toString())
                this.putString("createTime", data[i]["createTime"].toString())
            }

            if (!isDetail) {
                this.navigate(R.id.action_navControlFragment_to_newsDetailFragment, bundle)
            }
        }

//        val bundle = Bundle()
//        try {
//            bundle.putString("title", data[i].getString("title"))
//            bundle.putString("url", data[i].getString("url"))
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        val controller = Navigation.findNavController((context as Activity), R.id.fragment_main)
//        if (count == 3) {
//            controller.popBackStack()
//            controller.navigate(R.id.newsDetailFragment, bundle)
//        } else if (count == 11) {
//            controller.navigate(R.id.action_filmFragment_to_newsDetailFragment, bundle)
//        } else {
//            controller.navigate(R.id.action_navControlFragment_to_newsDetailFragment, bundle)
//        }
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