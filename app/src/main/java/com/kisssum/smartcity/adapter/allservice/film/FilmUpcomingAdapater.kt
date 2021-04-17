package com.kisssum.smartcity.adapter.allservice.film

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import com.kisssum.smartcity.state.film.FilmModel
import kotlin.collections.ArrayList

class FilmUpcomingAdapater(val context: Context, val data: ArrayList<Map<String, String>>, val viewModel: FilmModel) : RecyclerView.Adapter<FilmUpcomingAdapater.MyViewHolder>() {
    private val IS_POPULAR = 0
    private val IS_UPCOMING = 1

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.lsfcsflImg)
        val name = itemView.findViewById<TextView>(R.id.lsfuName)
        val wantSee = itemView.findViewById<CardView>(R.id.lsfuWantSee)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_film_upcoming, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]
        holder.name.text = map["name"]

        holder.img.setOnClickListener {
            val bundle = Bundle().apply {
                this.putInt("position", position)
                this.putInt("type", IS_UPCOMING)
            }

            Navigation.findNavController(context as Activity, R.id.fragment_main).navigate(R.id.action_filmFragment_to_filmDetailFragment, bundle)
        }

        holder.wantSee.setOnClickListener {
            val favoriteMap = viewModel.getUpcomingData().value?.get(position)!!
            viewModel.getUpcomingFavoriteData().value?.add(favoriteMap)

            Toast.makeText(context, "您已收藏该影片！", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = data.size
}