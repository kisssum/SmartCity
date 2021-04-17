package com.kisssum.smartcity.adapter.allservice.film

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import com.kisssum.smartcity.state.film.FilmModel

class FilmTheaterListAdapater(val context: Context, val viewModel: FilmModel, val theaterPosition: Int, val filmType: Int, val filmPosition: Int) : RecyclerView.Adapter<FilmTheaterListAdapater.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.lsfutName)
        val address = itemView.findViewById<TextView>(R.id.lsfutAddress)
        val star = itemView.findViewById<RatingBar>(R.id.lsfutStar)
        val price = itemView.findViewById<TextView>(R.id.lsfuPrice)
    }

    private val theaterData: ArrayList<Map<String, String>> = viewModel.getSurroundingTheaterData().value!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_film_theater_list, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = theaterData[position]
        holder.name.text = map["name"]
        holder.address.text = map["address"]
        holder.star.rating = map["star"].toString().toFloat()
        holder.price.text = "${map["price"]}元起"

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                this.putInt("theaterPosition", theaterPosition)
                this.putInt("filmType", filmType)
                this.putInt("filmPosition", filmPosition)
            }

            Navigation.findNavController(context as Activity, R.id.fragment_main).navigate(R.id.action_filmChoiceTheaterFragment_to_filmChoiceSessionFragment, bundle)
        }
    }

    override fun getItemCount() = theaterData.size
}