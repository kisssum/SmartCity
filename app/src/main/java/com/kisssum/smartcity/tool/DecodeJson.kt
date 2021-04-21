package com.kisssum.smartcity.tool

import android.util.Log
import org.json.JSONObject
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object DecodeJson {
    fun decodeServiceList(json: String): ArrayList<Map<String, String>> {
        val obj = ArrayList<Map<String, String>>()
        var map: HashMap<String, String>

        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    map = HashMap()
                    map["id"] = this.getInt("id").toString()
                    map["serviceName"] = this.getString("serviceName")
                    map["serviceDesc"] = this.getString("serviceDesc")
                    map["serviceType"] = this.getString("serviceType")
                    map["imgUrl"] = this.getString("imgUrl")
                    map["link"] = this.getString("link")
                    obj.add(map)
                }
            }
        }

        return obj
    }

    fun decodeNewsCommentsList(json: String): ArrayList<Map<String, String>> {
        val obj = ArrayList<Map<String, String>>()
        var map: HashMap<String, String>

        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    map = HashMap()
                    map["id"] = this.getInt("id").toString()
                    map["newsId"] = this.getInt("newsId").toString()
                    map["content"] = this.getString("content")
                    map["commentDate"] = this.getString("commentDate")
                    map["userId"] = this.getInt("userId").toString()
                    map["likeNum"] = this.getInt("likeNum").toString()
                    map["userName"] = this.getString("userName")
                    obj.add(map)
                }
            }
        }

        return obj
    }

    fun decodeNewsCommentAdd(json: String): String {
        val jsonObject = JSONObject(json)

        return if (jsonObject.getString("code") == "200") {
            "ok"
        } else {
            ""
        }
    }

    fun decodeNewsDetail(json: String): Map<String, String> {
        val jsonObject = JSONObject(json)
        val map = HashMap<String, String>()

        if (jsonObject.getInt("code") == 200) {
            val obj = jsonObject.getJSONObject("data")

            map["id"] = obj.getInt("id").toString()
            map["cover"] = obj.getString("cover")
            map["title"] = obj.getString("title")
            map["subTitle"] = obj.getString("subTitle")
            map["content"] = obj.getString("content")
            map["publishDate"] = obj.getString("publishDate")
            map["likeNum"] = obj.getString("likeNum")
            map["readNum"] = obj.getString("readNum")
            map["commentNum"] = obj.getString("commentNum")
        }

        return map
    }

    fun decodeCommentAdd(json: String): String {
        val jsonObject = JSONObject(json)

        return if (jsonObject.getInt("code") == 200) {
            "ok"
        } else {
            ""
        }
    }

    fun decodeNewsCategoryList(json: String): ArrayList<Map<String, String>> {
        val data = ArrayList<Map<String, String>>()
        var map: HashMap<String, String>

        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("data")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    map = HashMap()
                    map["id"] = this.getInt("id").toString()
                    map["name"] = this.getString("name")

                    data.add(map)
                }
            }
        }

        return data
    }

    fun decodeNewsTypeList(json: String): ArrayList<Map<String, String>> {
        val data = ArrayList<Map<String, String>>()
        var map: HashMap<String, String>
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    map = HashMap()
                    map["id"] = this.getInt("id").toString()
                    map["cover"] = this.getString("cover")
                    map["title"] = this.getString("title")
                    map["content"] = this.getString("content")
                    map["createTime"] = this.getString("createTime")
                    map["commentNum"] = this.getString("commentNum")
                    map["likeNum"] = this.getInt("likeNum").toString()
                    map["readNum"] = this.getInt("readNum").toString()
                    map["type"] = this.getString("type")

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

    fun decodeGuideRotationList(json: String): ArrayList<String> {
        val imgs = ArrayList<String>()
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {

            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    imgs.add(this.getString("advImg"))
                }
            }
        }

        return imgs
    }

    fun decodeHomeRotationList(json: String): ArrayList<Map<String, String>> {
        val data = ArrayList<Map<String, String>>()
        var map: HashMap<String, String>

        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            val jsonArray = jsonObject.getJSONArray("rows")

            for (i in 0 until jsonArray.length()) {
                jsonArray.getJSONObject(i).apply {
                    map = HashMap()
                    map["advImg"] = this.getString("advImg")
                    map["targetId"] = this.getInt("targetId").toString()
                    data.add(map)
                }
            }
        }

        return data
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

    fun decodeUserLogin(json: String): String {
        val jsonObject = JSONObject(json)

        return if (jsonObject.getInt("code") == 200) {
            jsonObject.getString("token")
        } else {
            ""
        }
    }

    fun decodeUserRegister(json: String): String {
        val jsonObject = JSONObject(json)

        return if (jsonObject.getString("code") == "200") {
            "ok"
        } else {
            ""
        }
    }

    fun decodeUserInfo(json: String): Map<String, String> {
        val map = HashMap<String, String>()
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            jsonObject.getJSONObject("user").apply {
                map["userId"] = this.getInt("userId").toString()
                map["userName"] = this.getString("userName")
                map["nickName"] = this.getString("nickName")
                map["email"] = this.getString("email")
                map["phonenumber"] = this.getString("phonenumber")
                map["sex"] = this.getString("sex")
                map["avatar"] = this.getString("avatar")
                map["idCard"] = this.getString("idCard")
                map["balance"] = this.getInt("balance").toString()
                map["score"] = this.getInt("score").toString()
            }
        }

        return map
    }

    fun decodeUserUpdate(json: String): String {
        val jsonObject = JSONObject(json)

        return if (jsonObject.getInt("code") == 200) {
            "ok"
        } else {
            ""
        }
    }

    fun decodeFeedBack(json: String): String {
        val jsonObject = JSONObject(json)

        return if (jsonObject.getInt("code") == 200) {
            "ok"
        } else {
            ""
        }
    }

    fun decodeUserForId(json: String): Map<String, Any> {
        val map = HashMap<String, Any>()
        val jsonObject = JSONObject(json)

        if (jsonObject.getInt("code") == 200) {
            jsonObject.getJSONObject("data").apply {
                map["userId"] = this.getInt("userId")
                map["avatar"] = this.getString("avatar")
                map["nickName"] = this.getString("nickName")
                map["sex"] = this.getInt("sex")
                map["phonenumber"] = this.getString("phonenumber")
                map["idCard"] = this.getString("idCard")
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