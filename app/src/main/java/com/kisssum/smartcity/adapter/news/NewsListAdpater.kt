package com.kisssum.smartcity.adapter.news

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.kisssum.smartcity.R
import com.kisssum.smartcity.tool.API
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class NewsListAdpater(val context: Context, val data: ArrayList<Map<String, Any>>) : RecyclerView.Adapter<NewsListAdpater.DefaultViewModel>() {
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
        holder.comment.text = "${map["likeNumber"].toString()}人看过"
        holder.time.text = map["createTime"].toString()

//        holder.itemView.setOnClickListener { v: View? -> navNewsInformation(position) }
    }

//    private fun navNewsInformation(i: Int) {
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
//    }

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