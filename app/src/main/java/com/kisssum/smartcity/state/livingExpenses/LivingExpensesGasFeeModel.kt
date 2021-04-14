package com.kisssum.smartcity.state.livingExpenses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class LivingExpensesGasFeeModel(application: Application) : AndroidViewModel(application) {
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
            map["area"] = "0"
            map["gasNumber"] = "21029090329"
            map["date"] = "2021.4.13"
            map["pay"] = "80"

            list.add(map)
        }

        return list
    }
}