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
import com.kisssum.smartcity.adapter.home.HotServiceListAdpater.DefaultViewMode
import com.kisssum.smartcity.tool.API
import java.util.*
import kotlin.collections.ArrayList

class HotServiceListAdpater(val context: Context, val data: ArrayList<Map<String, Any>>) : RecyclerView.Adapter<DefaultViewMode>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewMode {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_style_hot_service, parent, false)
        return DefaultViewMode(view)
    }

    override fun onBindViewHolder(holder: DefaultViewMode, position: Int) {
        Volley.newRequestQueue(context).apply {
            val imageRequest = ImageRequest(
                    API.getBaseUrl(context) + data[position]["imgUrl"].toString(),
                    {
                        holder.img.setImageBitmap(it)
                    },
                    0, 0, Bitmap.Config.RGB_565,
                    {}
            )

            this.add(imageRequest)
        }

        holder.name.text = data[position]["title"].toString()
        holder.itemView.setOnClickListener { v: View? ->
            val view: BottomNavigationView = (context as Activity).findViewById(R.id.bottomNavigationView)
            view.selectedItemId = R.id.item_allservice
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class DefaultViewMode(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.hotImg)
        val name = itemView.findViewById<TextView>(R.id.hotName)
    }
}