package com.kisssum.smartcity.tool

import android.content.Context
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

object MRString {
    fun getGuideRotationList(context: Context): String {
        // 新闻专栏
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getGuideRotationListUrl())
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }


    fun getHomeRotationList(): String {
        // 轮播图
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getHomeRotationListUrl())
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getHomeServiceList(): String {
        // 服务列表
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getServiceListUrl())
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getNewsDetail(id: Int): String {
        // 新闻专栏
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getNewsDetailUrl(id))
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getNewsCommentAdd(context: Context, id: Int, content: String): String {
        val sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val token = sp.getString("token", "")

        val jsonObject = JSONObject()
        jsonObject.put("newsId", id)
        jsonObject.put("content", content)

        val JSON = "application/json;charset=utf-8".toMediaTypeOrNull()
        val requestBody = RequestBody.create(JSON, jsonObject.toString())

        val request: Request = Request.Builder()
            .url(API.getNewsCommentAddUrl())
            .post(requestBody)
            .header("Authorization", "Bearer $token")
            .build()

        val client = OkHttpClient()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getNewsComments(id: Int): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getNewsCommentsListUrl(id))
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getNewsCommentLike(context: Context, id: Int): String {
        val sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val token = sp.getString("token", "")

        val jsonObject = JSONObject()
        jsonObject.put("id", id)

        val JSON = "application/json;charset=utf-8".toMediaTypeOrNull()
        val requestBody = RequestBody.create(JSON, jsonObject.toString())

        val request: Request = Request.Builder()
            .url(API.getNewsCommentLikeUrl(id))
            .put(requestBody)
            .header("Authorization", "Bearer $token")
            .build()

        val client = OkHttpClient()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }


    fun getHomeNewsCategoryList(): String {
        // 新闻专栏
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getNewsCategoryListUrl())
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getNewsList(page: Int): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getNewsListUrl(page))
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getNewsListByTitle(title: String): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getNewsListByTitleUrl(title))
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getHomeNewsTypeList(type: Int, page: Int): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getNewsTypeListUrl(type, page))
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getLoginInfo(name: String, pwd: String): String? {
        val jsonObject = JSONObject()
        jsonObject.put("username", name)
        jsonObject.put("password", pwd)

        val JSON = "application/json;charset=utf-8".toMediaTypeOrNull()
        val requestBody = RequestBody.create(JSON, jsonObject.toString())

        val request: Request = Request.Builder()
            .url(API.getUserLoginUrl())
            .post(requestBody)
            .build()

        val client = OkHttpClient()
        val response = client.newCall(request).execute()
        return response.body?.string()
    }

    fun getUserInfo(context: Context): String {
        val sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val token = sp.getString("token", "")

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getUserInfoUrl())
            .header("Authorization", "Bearer $token")
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getUserUpdate(
        context: Context,
        email: String,
        idCard: String,
        nickname: String,
        Phone: String,
        sex: String
    ): String {
        val sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val token = sp.getString("token", "")

        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("idCard", idCard)
        jsonObject.put("nickName", nickname)
        jsonObject.put("phonenumber", Phone)
        jsonObject.put("sex", sex)
        val JSON = "application/json;charset=utf-8".toMediaTypeOrNull()
        val requestBody = RequestBody.create(JSON, jsonObject.toString())

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getUserUpdata())
            .header("Authorization", "Bearer $token")
            .put(requestBody)
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getFeedBack(context: Context, title: String, content: String): String {
        val sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val token = sp.getString("token", "")

        val jsonObject = JSONObject()
        jsonObject.put("title", title)
        jsonObject.put("content", content)
        val JSON = "application/json;charset=utf-8".toMediaTypeOrNull()
        val requestBody = RequestBody.create(JSON, jsonObject.toString())

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(API.getFeedBack())
            .header("Authorization", "Bearer $token")
            .post(requestBody)
            .build()
        val response = client.newCall(request).execute()
        return response.body?.string().toString()
    }

    fun getRegisterInfo(
        userName: String,
        nickName: String,
        password: String,
        phoneNumber: String,
        sex: String
    ): String? {
        val jsonObject = JSONObject()
        jsonObject.put("userName", userName)
        jsonObject.put("nickName", nickName)
        jsonObject.put("password", password)
        jsonObject.put("phonenumber", phoneNumber)
        jsonObject.put("sex", sex)

        val JSON = "application/json;charset=utf-8".toMediaTypeOrNull()
        val requestBody = RequestBody.create(JSON, jsonObject.toString())

        val request: Request = Request.Builder()
            .url(API.getUserRegisterUrl())
            .post(requestBody)
            .build()

        val client = OkHttpClient()
        val response = client.newCall(request).execute()
        return response.body?.string()
    }
}