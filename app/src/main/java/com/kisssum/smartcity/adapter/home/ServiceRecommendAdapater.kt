package com.kisssum.smartcity.adapter.home

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kisssum.smartcity.R
import com.kisssum.smartcity.tool.API
import java.util.*

class ServiceRecommendAdapater(val context: Context, val data: ArrayList<Map<String, Any>>, val isRecommend: Boolean) : RecyclerView.Adapter<ServiceRecommendAdapater.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.ssImg)
        val name = itemView.findViewById<TextView>(R.id.ssName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_service_style, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]

        if (position == data.size - 1 && isRecommend) {
            holder.img.setImageResource(R.drawable.more)
            holder.itemView.setOnClickListener {
                val bottomNavigationView = (context as Activity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                bottomNavigationView.selectedItemId = R.id.item_allservice
            }
        } else {
            Volley.newRequestQueue(context).apply {
                val imageRequest = ImageRequest(
                        API.getBaseUrl(context) + data[position]["imgUrl"],
                        {
                            holder.img.setImageBitmap(it)
                        },
                        0, 0, Bitmap.Config.RGB_565,
                        {}
                )

                this.add(imageRequest)
            }
        }


        holder.name.text = map["serviceName"].toString()
//        holder.itemView.setOnClickListener {
//            viewModel.getSessionFilmPosition().value = position
//        }
    }

    override fun getItemCount() = data.size
}