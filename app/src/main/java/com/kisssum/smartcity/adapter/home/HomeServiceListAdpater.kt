package com.kisssum.smartcity.adapter.home

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kisssum.smartcity.R
import com.kisssum.smartcity.tool.API
import kotlin.collections.ArrayList

class HomeServiceListAdpater(
    val context: Context,
    val data: ArrayList<Map<String, String>>,
    val isHome: Boolean = false,
    val isSearch: Boolean = false
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (isSearch) {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_style_search_service_list, parent, false)
        SearchViewMode(view)
    } else {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_style_home_service_list, parent, false)
        NewsViewMode(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val map = data[position]

        if (isSearch) {
            holder as SearchViewMode

            Glide.with(context).load(API.getBaseUrl() + map["imgUrl"]).into(holder.img)
            holder.name.text = map["serviceName"]
            holder.desc.text = map["serviceDesc"]
            holder.type.text = map["serviceType"]
            holder.itemView.setOnClickListener {
                val controller =
                    Navigation.findNavController(context as Activity, R.id.fragment_main)

                when (map["id"]?.toInt()) {
                    2 -> {
                        // 城市地铁
                    }
                    3 -> {
                        // 智慧巴士
                        controller.navigate(R.id.action_newsSearchFragment_to_smartBusFragment)
                    }
                    5 -> {
                        // 门诊预约
                        controller.navigate(R.id.action_newsSearchFragment_to_outpatientAppointmentFragment)
                    }
                    7 -> {
                        // 生活缴费
                        controller.navigate(R.id.action_newsSearchFragment_to_livingExpensesFragment)
                    }
                    9 -> {
                        // 智慧交管
                    }
                    17 -> {
                        // 停哪儿
                    }
                    18 -> {
                        // 看电影
                        controller.navigate(R.id.action_newsSearchFragment_to_filmFragment)
                    }
                    19 -> {
                        // 外卖订餐
                    }
                    20 -> {
                        // 找房子
                    }
                    21 -> {
                        // 找工作
                    }
                    22 -> {
                        // 活动管理
                    }
                }
            }
        } else {
            holder as NewsViewMode

            if (isHome && position == data.size - 1) {
                Glide.with(context).load(context.getDrawable(R.drawable.more)).into(holder.img)
            } else {
                Glide.with(context).load(API.getBaseUrl() + map["imgUrl"]).into(holder.img)
            }
            holder.name.text = map["serviceName"]
            holder.itemView.setOnClickListener {
                val controller =
                    Navigation.findNavController(context as Activity, R.id.fragment_main)

                when (map["id"]?.toInt()) {
                    -1 -> {
                        // 更多服务
                        context.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                            .selectedItemId = R.id.item_allservice
                    }
                    2 -> {
                        // 城市地铁
                    }
                    3 -> {
                        // 智慧巴士
                        controller.navigate(R.id.action_navControlFragment_to_smartBusFragment)
                    }
                    5 -> {
                        // 门诊预约
                        controller.navigate(R.id.action_navControlFragment_to_outpatientAppointmentFragment)
                    }
                    7 -> {
                        // 生活缴费
                        controller.navigate(R.id.action_navControlFragment_to_livingExpensesFragment)
                    }
                    9 -> {
                        // 智慧交管
                    }
                    17 -> {
                        // 停哪儿
                    }
                    18 -> {
                        // 看电影
                        controller.navigate(R.id.action_navControlFragment_to_filmFragment)
                    }
                    19 -> {
                        // 外卖订餐
                    }
                    20 -> {
                        // 找房子
                    }
                    21 -> {
                        // 找工作
                    }
                    22 -> {
                        // 活动管理
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class NewsViewMode(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.hotImg)
        val name = itemView.findViewById<TextView>(R.id.hotName)
    }

    inner class SearchViewMode(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.hotImg)
        val name = itemView.findViewById<TextView>(R.id.hotName)
        val desc = itemView.findViewById<TextView>(R.id.hotDesc)
        val type = itemView.findViewById<TextView>(R.id.hotTYpe)
    }
}