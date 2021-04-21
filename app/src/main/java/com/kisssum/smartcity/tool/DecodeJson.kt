package com.kisssum.smartcity.tool

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object DecodeJson {
    fun decodeGuideRotationList(json: String): ArrayList<String> {
        val imgs = ArrayList<String>()
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {

            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    imgs.add(this.getString("imgUrl"))
                }
            }
        }

        return imgs
    }

    fun decodeHomeRotationList(json: String): ArrayList<String> {
        val imgs = ArrayList<String>()
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {

            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    imgs.add(this.getString("imgUrl"))
                }
            }
        }

        return imgs
    }

    fun decodeServiceRecommendList(json: String): ArrayList<Map<String, Any>> {
        val data = ArrayList<Map<String, Any>>()
        var map: HashMap<String, Any>
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    map = HashMap()
                    map["id"] = this.getInt("id")
                    map["serviceName"] = this.getString("serviceName")
                    map["serviceDesc"] = this.getString("serviceDesc")
                    map["serviceType"] = this.getString("serviceType")
                    map["imgUrl"] = this.getString("imgUrl")
                    map["pid"] = this.getInt("pid")
                    map["isRecommend"] = this.getInt("isRecommend")
                    map["link"] = this.getString("link")

                    data.add(map)
                }
            }

            for (i in 0 until data.size - 1) {
                for (j in 0 until data.size - i - 1) {
                    if (data[j]["id"].toString().toInt() < data[j + 1]["id"].toString().toInt()) {
                        val k = data[j]
                        data[j] = data[j + 1]
                        data[j + 1] = k
                    }
                }
            }
        }

        map = HashMap()
        map["id"] = ""
        map["serviceName"] = "更多服务"
        map["serviceDesc"] = ""
        map["serviceType"] = ""
        map["imgUrl"] = ""
        map["pid"] = ""
        map["isRecommend"] = ""
        map["link"] = ""
        data.add(map)

        return data
    }

    fun decodeHotThemeList(json: String): ArrayList<Map<String, Any>> {
        val data = ArrayList<Map<String, Any>>()
        var map: HashMap<String, Any>
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    map = HashMap()
                    map["id"] = this.getInt("id")
                    map["title"] = this.getString("title")
                    map["content"] = this.getString("content")
                    map["imgUrl"] = this.getString("imgUrl")

                    data.add(map)
                }
            }
        }

        return data
    }

    fun decodeNewsType(json: String): ArrayList<Map<String, Any>> {
        val data = ArrayList<Map<String, Any>>()
        var map: HashMap<String, Any>
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("data")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    map = HashMap()
                    map["dictCode"] = this.getInt("dictCode")
                    map["dictLabel"] = this.getString("dictLabel")
                    map["dictType"] = this.getString("dictType")

                    data.add(map)
                }
            }
        }

        return data
    }

    fun decodeNewsTypeList(json: String): ArrayList<Map<String, Any>> {
        val data = ArrayList<Map<String, Any>>()
        var map: HashMap<String, Any>
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    map = HashMap()
                    map["id"] = this.getInt("id")
                    map["title"] = this.getString("title")
                    map["content"] = this.getString("content")
                    map["imgUrl"] = this.getString("imgUrl")
                    map["pressCategory"] = this.getString("pressCategory")
                    map["likeNumber"] = this.getInt("likeNumber")
                    map["viewsNumber"] = this.getInt("viewsNumber")
                    map["createTime"] = this.getString("createTime")

                    data.add(map)
                }
            }
        }

        for (i in 0 until data.size - 1) {
            for (j in 0 until data.size - i - 1) {
                val d = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
                val d1 = d.parse(data[j]["createTime"].toString()).time
                val d2 = d.parse(data[j + 1]["createTime"].toString()).time

                if (d1 < d2) {
                    val k = data[j]
                    data[j] = data[j + 1]
                    data[j + 1] = k
                }
            }
        }

        return data
    }

    fun decodeUserInfo(json: String): Map<String, Any> {
        val map = HashMap<String, Any>()
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            jsonObject.getJSONObject("user").apply {
                map["userId"] = this.getInt("userId")
                map["nickName"] = this.getInt("nickName")
                map["avatar"] = this.getString("avatar")
            }
        }

        return map
    }

    fun decodeUserInfoInformation(json: String): Map<String, Any> {
        val map = HashMap<String, Any>()
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            jsonObject.getJSONObject("user").apply {
                map["userId"] = this.getInt("userId")
                map["avatar"] = this.getString("avatar")
                map["nickName"] = this.getInt("nickName")
                map["sex"] = this.getInt("sex")
                map["phonenumber"] = this.getString("phonenumber")
            }
        }

        return map
    }

    fun decodeServiceFirst(json: String): ArrayList<Map<String, Any>> {
        val data = ArrayList<Map<String, Any>>()
        var map: HashMap<String, Any>
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("data")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    map = HashMap()
                    map["dictLabel"] = this.getString("dictLabel")
                    map["dictValue"] = this.getString("dictValue")

                    data.add(map)
                }
            }
        }

        return data
    }

    fun decodeServiceAllList(json: String, serviceType: String): ArrayList<Map<String, Any>> {
        val data = ArrayList<Map<String, Any>>()
        var map: HashMap<String, Any>
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    if (this["serviceType"] == serviceType) {
                        map = HashMap()
                        map["id"] = this.getInt("id")
                        map["serviceName"] = this.getString("serviceName")
                        map["serviceDesc"] = this.getString("serviceDesc")
                        map["serviceType"] = this.getString("serviceType")
                        map["imgUrl"] = this.getString("imgUrl")
                        map["pid"] = this.getInt("pid")
                        map["link"] = this.getString("link")

                        data.add(map)
                    }
                }
            }
        }

        return data
    }

    fun decodeNewsCommentList(json: String): ArrayList<Map<String, Any>> {
        val data = ArrayList<Map<String, Any>>()
        var map: HashMap<String, Any>
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    map = HashMap()
                    map["pressId"] = this.getInt("pressId")
                    map["createTime"] = this.getString("createTime")
                    map["content"] = this.getString("content")
                    map["nickName"] = this.getString("nickName")
                    map["userName"] = this.getString("userName")
                    map["avatar"] = this.getString("avatar")

                    data.add(map)
                }
            }
        }

        return data
    }

}