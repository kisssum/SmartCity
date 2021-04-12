package com.kisssum.smartcity.adapter.allservice.smartBus

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import androidx.navigation.Navigation
import com.kisssum.smartcity.R

class SmartBusExtendableListAdapater(
        val context: Context,
        val groups: ArrayList<Map<String, String>>,
        val childs: ArrayList<ArrayList<String>>
) : BaseExpandableListAdapter() {
    fun setGroupData(data: ArrayList<Map<String, String>>) {
        groups.clear()
        groups.addAll(data)
    }

    fun setChildData(data: ArrayList<ArrayList<String>>) {
        childs.clear()
        childs.addAll(data)
    }

    override fun getGroupCount() = groups.size

    override fun getChildrenCount(p0: Int) = childs[p0].size

    override fun getGroup(p0: Int) = groups[p0]

    override fun getChild(p0: Int, p1: Int) = childs[p0][p1]

    override fun getGroupId(p0: Int) = p0.toLong()

    override fun getChildId(p0: Int, p1: Int) = p1.toLong()

    override fun hasStableIds() = true

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        val item = LayoutInflater.from(p3?.context).inflate(R.layout.list_style_smart_bus_exlist_parent, p3, false)

        val groupViewHolder = GroupViewHolder().apply {
            this.XianName = item.findViewById(R.id.lsolupepNXianName)
            this.StartPlace = item.findViewById(R.id.lsolupepStartPlace)
            this.EndPlace = item.findViewById(R.id.lsolupepEndPlace)
            this.Time1 = item.findViewById(R.id.lssbepTime1)
            this.Time2 = item.findViewById(R.id.lssbepTime2)
            this.TicketPrice = item.findViewById(R.id.lsolupepTicketPrice)
            this.Mileage = item.findViewById(R.id.lssbepMileage)

            this.XianName.text = groups[p0]["XianName"]
            this.StartPlace.text = groups[p0]["StartPlace"]
            this.EndPlace.text = groups[p0]["EndPlace"]
            this.Time1.text = groups[p0]["Time1"]
            this.Time2.text = groups[p0]["Time2"]
            this.TicketPrice.text = "票价:￥${groups[p0]["TicketPrice"]}"
            this.Mileage.text = "里程:${groups[p0]["Mileage"]}km"

            this.StartPlace.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("position", p0)

                Navigation.findNavController(context as Activity, R.id.fragment_main).apply {
                    this.navigate(R.id.action_smartBusFragment_to_smartBusStep1Fragment, bundle)
                }
            }
        }

        item.tag = groupViewHolder
        return item
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val item = LayoutInflater.from(p4?.context).inflate(R.layout.list_style_smart_bus_exlist_child, p4, false)

        val childViewHolder = ChildViewHolder().apply {
            this.Place = item.findViewById(R.id.lssbecPlace)
            this.Place.text = childs[p0][p1]
        }

        item.tag = childViewHolder
        return item
    }

    override fun isChildSelectable(p0: Int, p1: Int) = false

    class GroupViewHolder {
        lateinit var XianName: TextView
        lateinit var StartPlace: TextView
        lateinit var EndPlace: TextView
        lateinit var Time1: TextView
        lateinit var Time2: TextView
        lateinit var TicketPrice: TextView
        lateinit var Mileage: TextView
    }

    class ChildViewHolder {
        lateinit var Place: TextView
    }
}