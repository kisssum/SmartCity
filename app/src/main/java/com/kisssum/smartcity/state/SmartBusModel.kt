package com.kisssum.smartcity.state

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SmartBusModel(application: Application) : AndroidViewModel(application) {
    private val groupData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val childData = MutableLiveData<ArrayList<ArrayList<String>>>()

    init {
        groupData.value = setGroupData()
        childData.value = setChildData()
    }

    private fun setChildData(): ArrayList<ArrayList<String>> {
        val d = ArrayList<ArrayList<String>>()

        val d1 = ArrayList<String>().apply {
            this.add("起点:   先谷金融街")
            this.add("戎军路南")
            this.add("戎军路下")
            this.add("戎军路北")
            this.add("利军路南")
            this.add("终点:   南湖商厦")
        }

        val d2 = ArrayList<String>().apply {
            this.add("起点:   先谷金融街")
            this.add("戎军路南")
            this.add("戎军路下")
            this.add("戎军路北")
            this.add("利军路南")
            this.add("终点:   南湖商厦")
        }

        val d3 = ArrayList<String>().apply {
            this.add("起点:   先谷金融街")
            this.add("戎军路南")
            this.add("戎军路下")
            this.add("戎军路北")
            this.add("利军路南")
            this.add("终点:   南湖商厦")
        }

        d.add(d1)
        d.add(d2)
        d.add(d3)

        return d
    }

    private fun setGroupData(): ArrayList<Map<String, String>> {
        val d = ArrayList<Map<String, String>>()
        var m: Map<String, String>

        m = HashMap()
        m["XianName"] = "1号线"
        m["StartPlace"] = "先谷金融街"
        m["EndPlace"] = "南湖商厦"
        m["Time1"] = "06:45-19:45"
        m["Time2"] = "06:45-19:45"
        m["TicketPrice"] = "8.0"
        m["Mileage"] = "20.0"
        d.add(m)

        m = HashMap()
        m["XianName"] = "2号线"
        m["StartPlace"] = "先谷金融街"
        m["EndPlace"] = "南湖商厦"
        m["Time1"] = "06:45-19:45"
        m["Time2"] = "06:45-19:45"
        m["TicketPrice"] = "8.0"
        m["Mileage"] = "20.0"
        d.add(m)


        m = HashMap()
        m["XianName"] = "3号线"
        m["StartPlace"] = "先谷金融街"
        m["EndPlace"] = "南湖商厦"
        m["Time1"] = "06:45-19:45"
        m["Time2"] = "06:45-19:45"
        m["TicketPrice"] = "8.0"
        m["Mileage"] = "20.0"
        d.add(m)


        return d
    }

    fun getGroupData() = groupData

    fun getChildData() = childData
}