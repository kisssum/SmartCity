package com.kisssum.smartcity.state

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class BillModel(application: Application) : AndroidViewModel(application) {
    private val groupData = MutableLiveData<ArrayList<Map<String, String>>>()
    private val childData = MutableLiveData<ArrayList<ArrayList<String>>>()

    fun getGroupData() = groupData
    fun getChildData() = childData

    init {
        val mapGroup = HashMap<String, String>().apply {
            this["id"] = UUID.randomUUID().toString()
            this["XianName"] = "一号线"
            this["StartPlace"] = "先谷金融街"
            this["EndPlace"] = "南湖商厦"
            this["TicketPrice"] = "8.0"
        }

        val mapChild = ArrayList<String>().apply {
            this.add("2019-10-03")
            this.add("2019-10-04")
            this.add("2019-10-08")
        }

        groupData.value = ArrayList()
        childData.value = ArrayList()
        
        groupData.value?.add(mapGroup)
        childData.value?.add(mapChild)
    }
}