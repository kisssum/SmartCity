package com.kisssum.smartcity.tool

import org.json.JSONObject

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
}