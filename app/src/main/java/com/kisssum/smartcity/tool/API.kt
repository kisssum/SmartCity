package com.kisssum.smartcity.tool

import android.content.Context
import android.content.SharedPreferences

object API {
    fun getBaseUrl(context: Context): String {
        val sp: SharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        return "http://${sp.getString("ip", "")}:${sp.getString("duankou", "")}"
    }

    fun getRotationListUrl(context: Context) = "${getBaseUrl(context)}/userinfo/rotation/lists?pageNum=1&pageSize=10&type=47"
}