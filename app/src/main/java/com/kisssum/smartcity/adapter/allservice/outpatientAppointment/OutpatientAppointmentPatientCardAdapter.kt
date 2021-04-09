package com.kisssum.smartcity.adapter.allservice.outpatientAppointment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import kotlin.collections.ArrayList

class OutpatientAppointmentPatientCardAdapter(val context: Context, d: ArrayList<Map<String, String>>) : RecyclerView.Adapter<OutpatientAppointmentPatientCardAdapter.MyViewHolder>() {
    private var data = ArrayList<Map<String, String>>()

    init {
        setData(d)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.lsoapcGo)
        val name = itemView.findViewById<TextView>(R.id.lsoapcName)
        val personalNumber = itemView.findViewById<TextView>(R.id.lsoapcPersonalNumber)
        val phoneNumber = itemView.findViewById<TextView>(R.id.lsoapcPhoneNumber)
        val patientNumber = itemView.findViewById<TextView>(R.id.lsoapcPatientNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_outpatient_appointment_patient_card, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]

        holder.name.text = map["name"].toString()
        holder.personalNumber.text = map["personalNumber"].toString()
        holder.phoneNumber.text = map["phoneNumber"].toString()
        holder.patientNumber.text = map["patientNumber"].toString()

        holder.img.setOnClickListener {
            val navController = Navigation.findNavController(context as Activity, R.id.fragment_main)
            navController.navigate(R.id.action_outpatientAppointmentPatientCardFragment_to_outpatientAppointmentDepartmentTriageFragment)
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("isCreate", false)
            bundle.putInt("position", position)

            val navController = Navigation.findNavController(context as Activity, R.id.fragment_main)
            navController.navigate(R.id.action_outpatientAppointmentPatientCardFragment_to_outpatientAppointmentPerfectPatientInformationFragment, bundle)
        }
    }

    fun setData(j: ArrayList<Map<String, String>>) {
        data.clear()
        data.addAll(j)
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size
}