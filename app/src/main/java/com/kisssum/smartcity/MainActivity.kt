package com.kisssum.smartcity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kisssum.smartcity.tool.API
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private val LOGIN = 0
    private val REGISTER = 1
    private val userName = "root12"
    private var userPassword = "123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                val jsonObject = JSONObject(msg.obj as String)

                when (msg.what) {
                    LOGIN -> {
                        if (jsonObject.getInt("code") == 200) {
                            getSharedPreferences("User", MODE_PRIVATE).edit()
                                    .putString("username", userName)
                                    .putString("password", userPassword)
                                    .putString("token", jsonObject.getString("token"))
                                    .apply()
                        } else if (jsonObject.getInt("code") == 500) {
                            register()
                        }
                    }
                    REGISTER -> {
                        if (jsonObject.getInt("code") == 200) {
                            login()
                        }
                    }
                    else -> ""
                }
            }
        }

        // 初始化数据
        initLocalData()
    }

    private fun initLocalData() {
        // 学校  http://106.12.160.221:8080
        // 省赛  http://124.93.196.45:10002
        // 国赛  http://182.92.105.116:8080

        getSharedPreferences("Settings", MODE_PRIVATE).apply {
            if (this.getString("ip", "") == "" || this.getString("duankou", "") == "") {
                this.edit()
                        .putString("ip", "124.93.196.45")
                        .putString("duankou", "10002")
                        .apply()
            }
        }

        getSharedPreferences("User", MODE_PRIVATE).apply {
            userPassword = this.getString("password", "123")!!

            if (this.getString("username", "") == "") {
                login()
            }
        }
    }

    private fun register() {
        Thread {
            OkHttpClient().apply {
                val jsonobject = JSONObject()
                jsonobject.put("userName", userName)
                jsonobject.put("password", userPassword)
                jsonobject.put("nickName", "张三")
                jsonobject.put("nickName", 1)

                val parse = "application/json;charset=utf-8".toMediaTypeOrNull()
                val requestBody = RequestBody.create(parse, jsonobject.toString())

                val request = okhttp3.Request.Builder()
                        .url(API.getUserRegisterUrl(this@MainActivity))
                        .post(requestBody)
                        .build()

                try {
                    val string = this.newCall(request).execute().body?.string()

                    val message = Message().apply {
                        this.what = REGISTER
                        this.obj = string
                    }

                    handler.sendMessage(message)
                } catch (e: Exception) {

                }
            }
        }.start()
    }

    private fun login() {
        Thread {
            OkHttpClient().apply {
                val jsonobject = JSONObject()
                jsonobject.put("username", userName)
                jsonobject.put("password", userPassword)

                val parse = "application/json;charset=utf-8".toMediaTypeOrNull()
                val requestBody = RequestBody.create(parse, jsonobject.toString())

                val request = okhttp3.Request.Builder()
                        .url(API.getUserLoginUrl(this@MainActivity))
                        .post(requestBody)
                        .build()

                try {
                    val string = this.newCall(request).execute().body?.string()

                    val message = Message().apply {
                        this.what = LOGIN
                        this.obj = string
                    }

                    handler.sendMessage(message)
                } catch (e: Exception) {

                }
            }
        }.start()
    }
}