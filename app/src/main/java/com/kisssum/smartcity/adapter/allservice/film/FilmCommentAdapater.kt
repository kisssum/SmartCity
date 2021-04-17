package com.kisssum.smartcity.adapter.allservice.film

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import kotlin.collections.ArrayList

class FilmCommentAdapater(val context: Context, var data: ArrayList<Map<String, String>>) : RecyclerView.Adapter<FilmCommentAdapater.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.lsfcImg)
        val userName = itemView.findViewById<TextView>(R.id.lsfcUserName)
        val userScore = itemView.findViewById<RatingBar>(R.id.lsfcUserScore)
        val likes = itemView.findViewById<TextView>(R.id.lsfcLikes)
        val commentText = itemView.findViewById<TextView>(R.id.lsfcCommentText)
        val commentTime = itemView.findViewById<TextView>(R.id.lsfcCommentTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_film_comment, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]

        holder.userName.text = map["userName"]
        holder.userScore.rating = map["userScore"]!!.toFloat() / 2
        holder.likes.text = map["likes"]
        holder.commentText.text = map["commentText"]
        holder.commentTime.text = map["commentTime"]
    }

    override fun getItemCount() = data.size

    fun setData1(it: ArrayList<Map<String, String>>) {
        val apply = ArrayList<Map<String, String>>().apply {
            this.addAll(it)
        }

        this.data = ArrayList()
        this.data.clear()
        this.data.addAll(apply)
        notifyDataSetChanged()
    }
}