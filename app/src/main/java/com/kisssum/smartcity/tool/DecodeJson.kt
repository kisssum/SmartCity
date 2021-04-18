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
}