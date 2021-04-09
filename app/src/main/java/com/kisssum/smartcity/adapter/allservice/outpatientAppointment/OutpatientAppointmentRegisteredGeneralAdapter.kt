package com.kisssum.smartcity.adapter.allservice.outpatientAppointment

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
import kotlin.collections.HashMap

class OutpatientAppointmentRegisteredGeneralAdapter(val context: Context) : RecyclerView.Adapter<OutpatientAppointmentRegisteredGeneralAdapter.MyViewHolder>() {
    private val data = ArrayList<Map<String, String>>()

    init {
        data.clear()
        data.addAll(getData())
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val can = itemView.findViewById<CardView>(R.id.lsoargCan)
        val date = itemView.findViewById<TextView>(R.id.lsoargDate)
        val type = itemView.findViewById<TextView>(R.id.lsoargType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_outpatient_appointment_registered_general, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]

        holder.date.text = map["date"].toString()
        holder.type.text = map["type"].toString()

        holder.can.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("date", map["date"].toString())
            bundle.putString("type", map["type"].toString())
            bundle.putString("type2", map["type2"].toString())

            val navController = Navigation.findNavController(context as Activity, R.id.fragment_main)
            navController.navigate(R.id.action_outpatientAppointmentRegisteredFragment_to_outpatientAppointmentRegisteredDetailFragment, bundle)
        }
    }

    override fun getItemCount() = data.size

    private fun getData(): ArrayList<Map<String, String>> {
        val d = ArrayList<Map<String, String>>()

        var map: HashMap<String, String> = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "神经内科"
        map["type2"] = "普通"
        d.add(map)

        map = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "心肾内科"
        map["type2"] = "普通"
        d.add(map)

        map = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "呼吸消化科"
        map["type2"] = "普通"
        d.add(map)

        map = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "慢性病科"
        map["type2"] = "普通"
        d.add(map)

        map = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "普外科"
        map["type2"] = "普通"
        d.add(map)

        map = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "骨外科"
        map["type2"] = "普通"
        d.add(map)

        map = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "妇产科"
        map["type2"] = "普通"
        d.add(map)

        map = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "儿科"
        map["type2"] = "普通"
        d.add(map)

        map = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "眼耳鼻喉科"
        map["type2"] = "普通"
        d.add(map)

        map = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "神经内科"
        map["type2"] = "普通"
        d.add(map)

        map = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "口腔科皮肤科"
        map["type2"] = "普通"
        d.add(map)

        map = HashMap()
        map["date"] = "2020-9-21（今天）周一，下午14:00"
        map["type"] = "急诊"
        map["type2"] = "普通"
        d.add(map)

        return d
    }
}