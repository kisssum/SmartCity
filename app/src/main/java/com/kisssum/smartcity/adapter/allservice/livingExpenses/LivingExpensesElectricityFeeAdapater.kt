package com.kisssum.smartcity.adapter.allservice.livingExpenses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class LivingExpensesElectricityFeeAdapater(val context: Context) : RecyclerView.Adapter<LivingExpensesElectricityFeeAdapater.MyViewHolder>() {
    private val data = ArrayList<Map<String, String>>()

    init {
        data.clear()
        data.addAll(getData())
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val unit = itemView.findViewById<TextView>(R.id.lsleefUnit)
        val accountNumber = itemView.findViewById<TextView>(R.id.lsleefAccountNumber)
        val accountName = itemView.findViewById<TextView>(R.id.lsleefAccountName)
        val address = itemView.findViewById<TextView>(R.id.lsleefAddress)
        val availableBalance = itemView.findViewById<TextView>(R.id.lsleefAvailableBalance)
        val arrearsBalance = itemView.findViewById<TextView>(R.id.lsleefArrearsBalance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_living_expenses_electricity_fee, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]

        holder.unit.text = "单位:${map["unit"].toString()}"
        holder.accountNumber.text = "户号:${map["accountNumber"].toString()}"
        holder.accountName.text = "户名:${map["accountName"].toString()}"
        holder.address.text = "住址:${map["address"].toString()}"
        holder.availableBalance.text = "余额:${map["availableBalance"].toString()}￥"
        holder.arrearsBalance.text = "欠费:-${map["arrearsBalance"].toString()}￥"
    }

    override fun getItemCount() = data.size

    private fun getData(): ArrayList<Map<String, String>> {
        val d = ArrayList<Map<String, String>>()
        var map: HashMap<String, String>

        for (i in 1..20) {
            map = HashMap()
            map["unit"] = "社区办"
            map["accountNumber"] = "192839274"
            map["accountName"] = "张三"
            map["address"] = "花园小区4栋楼613室"
            map["availableBalance"] = "600"
            map["arrearsBalance"] = "40"

            d.add(map)
        }

        return d
    }
}