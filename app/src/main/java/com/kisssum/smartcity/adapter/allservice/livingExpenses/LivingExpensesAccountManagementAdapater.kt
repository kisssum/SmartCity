package com.kisssum.smartcity.adapter.allservice.livingExpenses

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

class LivingExpensesAccountManagementAdapater(val context: Context, d: ArrayList<Map<String, String>>) : RecyclerView.Adapter<LivingExpensesAccountManagementAdapater.MyViewHolder>() {
    private var data = ArrayList<Map<String, String>>()

    init {
        setData(d)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.lsleamName)
        val payType = itemView.findViewById<TextView>(R.id.lsleamPayType)
        val accountNumber = itemView.findViewById<TextView>(R.id.lsleamAccountNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_living_expenses_account_management, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val map = data[position]

        holder.name.text = map["name"].toString()
        holder.payType.text = map["payType"].toString()
        holder.accountNumber.text = map["accountNumber"].toString()
    }

    fun setData(j: ArrayList<Map<String, String>>) {
        data.clear()
        data.addAll(j)
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size
}