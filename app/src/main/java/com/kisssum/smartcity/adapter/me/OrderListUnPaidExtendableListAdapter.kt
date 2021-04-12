package com.kisssum.smartcity.adapter.me

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.kisssum.smartcity.R
import kotlin.collections.ArrayList

class OrderListUnPaidExtendableListAdapter(
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
        val item = LayoutInflater.from(p3?.context).inflate(R.layout.list_style_order_list_un_paid_exlist_parent, p3, false)

        val groupViewHolder = GroupViewHolder().apply {
            this.Id = item.findViewById(R.id.lsolupepId)
            this.XianName = item.findViewById(R.id.lsolupepNXianName)
            this.StartPlace = item.findViewById(R.id.lsolupepStartPlace)
            this.EndPlace = item.findViewById(R.id.lsolupepEndPlace)
            this.TicketPrice = item.findViewById(R.id.lsolupepTicketPrice)

            this.Id.text = "订单编号:${groups[p0]["id"]}"
            this.XianName.text = groups[p0]["XianName"]
            this.StartPlace.text = groups[p0]["StartPlace"]
            this.EndPlace.text = groups[p0]["EndPlace"]
            this.TicketPrice.text = "票价:￥${groups[p0]["TicketPrice"]}"
        }

        item.tag = groupViewHolder
        return item
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val item = LayoutInflater.from(p4?.context).inflate(R.layout.list_style_smart_bus_exlist_child, p4, false)

        val childViewHolder = ChildViewHolder().apply {
            this.Date = item.findViewById(R.id.lssbecPlace)

            if (p1 == 0)
                this.Date.text = "乘车日期:${childs[p0][p1]}"
            else
                this.Date.text = childs[p0][p1]
        }

        item.tag = childViewHolder
        return item
    }

    override fun isChildSelectable(p0: Int, p1: Int) = false

    class GroupViewHolder {
        lateinit var Id: TextView
        lateinit var XianName: TextView
        lateinit var StartPlace: TextView
        lateinit var EndPlace: TextView
        lateinit var TicketPrice: TextView
    }

    class ChildViewHolder {
        lateinit var Date: TextView
    }
}