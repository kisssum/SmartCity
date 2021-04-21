package com.kisssum.smartcity.tool

import android.app.Activity
import android.content.Context
import android.widget.Toast

object UpdateUI {
    fun toastUi(context: Context, string: String) {
        (context as Activity).runOnUiThread {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
        }
    }
}