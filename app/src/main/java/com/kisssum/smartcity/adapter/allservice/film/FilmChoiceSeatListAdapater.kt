package com.kisssum.smartcity.adapter.allservice.film

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import kotlin.collections.ArrayList

class FilmChoiceSeatListAdapater(val context: Context, var data: ArrayList<ArrayList<Int>>, val price: String) : RecyclerView.Adapter<FilmChoiceSeatListAdapater.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text = itemView.findViewById<TextView>(R.id.lsfcslText)
        val price = itemView.findViewById<TextView>(R.id.lsfcslPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_film_choice_seat_list, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.text.text = "${data[0][position]}排${data[1][position]}座"
        holder.price.text = "${price.toFloat()}元"

        holder.itemView.setOnClickListener {
            data[0].removeAt(position)
            data[1].removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = data[0].size

    fun setseatData(seatData: ArrayList<ArrayList<Int>>) {
        this.data = seatData
        notifyDataSetChanged()
    }
}