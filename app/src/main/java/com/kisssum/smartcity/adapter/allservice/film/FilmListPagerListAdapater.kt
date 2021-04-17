package com.kisssum.smartcity.adapter.allservice.film

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import kotlin.collections.ArrayList

class FilmListPagerListAdapater(val context: Context, val data: ArrayList<Map<String, String>>, val fileType: Int) : RecyclerView.Adapter<FilmListPagerListAdapater.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.lsflpImg)
        val name = itemView.findViewById<TextView>(R.id.lsflplName)
        val type = itemView.findViewById<TextView>(R.id.lsflplType)
        val playType = itemView.findViewById<TextView>(R.id.lsflplPlayType)
        val score = itemView.findViewById<TextView>(R.id.lsflplScore)
        val buy = itemView.findViewById<CardView>(R.id.lsflplBuy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_film_list_pager_list, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]

        holder.name.text = map["name"]
        holder.type.text = map["type"]
        holder.playType.text = map["playType"]
        holder.score.text = "${map["score"]}分"

        val bundle = Bundle().apply {
            this.putInt("position", position)
            this.putInt("type", fileType)
        }
        holder.itemView.setOnClickListener { Navigation.findNavController(context as Activity, R.id.fragment_main).navigate(R.id.action_filmListFragment_to_filmDetailFragment, bundle) }
        holder.img.setOnClickListener { Navigation.findNavController(context as Activity, R.id.fragment_main).navigate(R.id.action_filmListFragment_to_filmPlayTrailerFragment, bundle) }
        holder.buy.setOnClickListener { Navigation.findNavController(context as Activity, R.id.fragment_main).navigate(R.id.action_filmListFragment_to_filmChoiceTheaterFragment, bundle) }
    }

    override fun getItemCount() = data.size
}