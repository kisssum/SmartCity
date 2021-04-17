package com.kisssum.smartcity.adapter.allservice.film

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import kotlin.collections.ArrayList

class FilmSurroundingTheaterAdapater(val context: Context, val data: ArrayList<Map<String, String>>) : RecyclerView.Adapter<FilmSurroundingTheaterAdapater.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.lsfutName)
        val address = itemView.findViewById<TextView>(R.id.lsfutAddress)
        val star = itemView.findViewById<RatingBar>(R.id.lsfutStar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_film_surrounding_theater, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]
        holder.name.text = map["name"]
        holder.address.text = map["address"]
        holder.star.rating = map["star"].toString().toFloat()

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                this.putInt("theaterPosition", position)
                this.putInt("filmType", 0)
                this.putInt("filmPosition", 0)
            }

            Navigation.findNavController(context as Activity, R.id.fragment_main).navigate(R.id.action_filmFragment_to_filmChoiceSessionFragment, bundle)
        }
    }

    override fun getItemCount() = data.size
}