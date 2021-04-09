package com.kisssum.smartcity.state

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class OutpatientAppointmentPatientCardModel(application: Application) : AndroidViewModel(application) {
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

        for (i in 0..6) {
            map = HashMap()
            map["name"] = "李小萌"
            map["gender"] = "女"
            map["personalNumber"] = "210211198706158765"
            map["bronDate"] = "2000.01.01"
            map["phoneNumber"] = "18576398765"
            map["address"] = "中和路"
            map["patientNumber"] = "2020001"

            list.add(map)
        }

        return list
    }
}