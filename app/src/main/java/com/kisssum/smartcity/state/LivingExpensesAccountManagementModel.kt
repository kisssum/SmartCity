package com.kisssum.smartcity.state

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class LivingExpensesAccountManagementModel(application: Application) : AndroidViewModel(application) {
    private val data = MutableLiveData<ArrayList<Map<String, String>>>()

    init {
        data.value = setData()
    }

    fun getData(): MutableLiveData<ArrayList<Map<String, String>>> {
        return data
    }

    private fun setData(): ArrayList<Map<String, String>> {
        val list = ArrayList<Map<String, String>>()
        var map: HashMap<String, String>

        for (i in 0..4) {
            map = HashMap()
            map["name"] = "我家"
            map["payType"] = "水费"
            map["accountNumber"] = "84688468|4-2-1"

            list.add(map)
        }

        return list
    }
}