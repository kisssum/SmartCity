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

class FilmPopularAdapater(val context: Context, val data: ArrayList<Map<String, String>>) : RecyclerView.Adapter<FilmPopularAdapater.MyViewHolder>() {
    private val IS_POPULAR = 0
    private val IS_UPCOMING = 1

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.lsfpImg)
        val name = itemView.findViewById<TextView>(R.id.lsfpName)
        val buy = itemView.findViewById<CardView>(R.id.lsfpBuy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_film_popular, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]
        holder.name.text = map["name"]

        holder.img.setOnClickListener {
            val bundle = Bundle().apply {
                this.putInt("position", position)
                this.putInt("type", IS_POPULAR)
            }

            Navigation.findNavController(context as Activity, R.id.fragment_main).navigate(R.id.action_filmFragment_to_filmDetailFragment, bundle)
        }

        holder.buy.setOnClickListener {
            val bundle = Bundle().apply {
                this.putInt("position", position)
                this.putInt("type", IS_POPULAR)
            }

            Navigation.findNavController(context as Activity, R.id.fragment_main).navigate(R.id.action_filmFragment_to_filmChoiceTheaterFragment, bundle)
        }
    }

    override fun getItemCount() = data.size
}