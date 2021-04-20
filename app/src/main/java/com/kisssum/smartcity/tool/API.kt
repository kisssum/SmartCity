package com.kisssum.smartcity.tool

import android.content.Context
import android.content.SharedPreferences

object API {
    fun getBaseUrl(context: Context): String {
        val sp: SharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        return "http://${sp.getString("ip", "")}:${sp.getString("duankou", "")}"
    }

    fun getToken(context: Context): String {
        val sp: SharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
        return sp.getString("token", "")!!
    }

    fun getUserId(context: Context): Int {
        val sp: SharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
        return sp.getInt("userId", 0)
    }

    fun getGuideRotationListUrl(context: Context) = "${getBaseUrl(context)}/userinfo/rotation/lists?pageNum=1&pageSize=10&type=47"
    fun getHomeeRotationListUrl(context: Context) = "${getBaseUrl(context)}/userinfo/rotation/list?pageNum=1&pageSize=10&type=45"
    fun getServiceRecommendListUrl(context: Context) = "${getBaseUrl(context)}/service/service/list?pageNum=1&pageSize=10"
    fun getHotThemeListUrl(context: Context) = "${getBaseUrl(context)}/press/press/list?pageNum=1&pageSize=10&pressCategory=48"
    fun getNewsTypeUrl(context: Context) = "${getBaseUrl(context)}/system/dict/data/type/press_category"
    fun getNewsTypeListUrl(context: Context, pressCategory: Int) = "${getBaseUrl(context)}/press/press/list?pageNum=1&pageSize=10&pressCategory=${pressCategory}"
    fun getUserLoginUrl(context: Context) = "${getBaseUrl(context)}/login"
    fun getUserRegisterUrl(context: Context) = "${getBaseUrl(context)}/system/user/register"
    fun getUserInfoUrl(context: Context) = "${getBaseUrl(context)}/getInfo"
    fun getUserUpdata(context: Context) = "${getBaseUrl(context)}/system/user/updata"
    fun getUserResetPwd(context: Context) = "${getBaseUrl(context)}/system/user/resetPwd"
    fun getUserAddFeedBack(context: Context) = "${getBaseUrl(context)}/userinfo/feedback"
    fun getServiceFirst(context: Context) = "${getBaseUrl(context)}/system/dict/data/type/sys_service"
    fun getServiceAllList(context: Context) = "${getBaseUrl(context)}/service/service/list"
}