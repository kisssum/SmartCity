package com.kisssum.smartcity.adapter.allservice.film

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import kotlin.collections.ArrayList

class FilmChoiceSeesionListAdapater(val context: Context, val data: ArrayList<Map<String, String>>, val theaterPosition: Int, val filmType: Int, val filmPosition: Int) : RecyclerView.Adapter<FilmChoiceSeesionListAdapater.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val startTime = itemView.findViewById<TextView>(R.id.lsfcsStartTime)
        val endTime = itemView.findViewById<TextView>(R.id.lsfcsEndTime)
        val filmType = itemView.findViewById<TextView>(R.id.lsfcsFilmType)
        val playType = itemView.findViewById<TextView>(R.id.lsfcsPlayType)
        val price = itemView.findViewById<TextView>(R.id.lsfcsPrice)
        val buy = itemView.findViewById<CardView>(R.id.lsfcsBuy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_film_choice_session, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]

        holder.startTime.text = map["startTime"]
        holder.endTime.text = "${map["endTime"]}散场"
        holder.filmType.text = map["fileType"]
        holder.playType.text = map["playType"]
        holder.price.text = "${map["price"]}元"

        holder.buy.setOnClickListener {
            val bundle = Bundle().apply {
                this.putInt("theaterPosition", theaterPosition)
                this.putInt("filmType", filmType)
                this.putInt("filmPosition", filmPosition)
                this.putInt("seesionPosition", position)
            }

            Navigation.findNavController(context as Activity, R.id.fragment_main).navigate(R.id.action_filmChoiceSessionFragment_to_filmChoiceSeatFragment, bundle)
        }
    }

    override fun getItemCount() = data.size
}

