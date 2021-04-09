package com.kisssum.smartcity.adapter.allservice.outpatientAppointment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class OutpatientAppointmentAdapter(val context: Context) : RecyclerView.Adapter<OutpatientAppointmentAdapter.MyViewHolder>() {
    private val data = ArrayList<Map<String, Any>>()

    init {
        data.clear()
        data.addAll(getData())
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.lsoaImg)
        val name = itemView.findViewById<TextView>(R.id.lsoaName)
        val rating = itemView.findViewById<RatingBar>(R.id.lsoaRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_outpatient_appointment, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]

        holder.name.text = map["name"].toString()
        holder.rating.rating = map["rating"].toString().toFloat()

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", map["name"].toString())
            bundle.putString("describe", map["describe"].toString())

            val navController = Navigation.findNavController(context as Activity, R.id.fragment_main)
            navController.navigate(R.id.action_outpatientAppointmentFragment_to_outpatientAppointmentHospitalIntroductionFragment, bundle)
        }
    }

    override fun getItemCount() = data.size

    private fun getData(): ArrayList<Map<String, Any>> {
        val d = ArrayList<Map<String, Any>>()
        var map: HashMap<String, Any>

        for (i in 1..15) {
            map = HashMap()
            map["name"] = "大连市儿童医院"
            map["rating"] = 3
            map["describe"] = "医院分设院本部、东院和南院，编制病床共1949张。"

            d.add(map)
        }

        return d
    }
}