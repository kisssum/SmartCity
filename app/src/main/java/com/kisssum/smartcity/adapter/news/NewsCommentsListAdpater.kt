package com.kisssum.smartcity.adapter.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import com.kisssum.smartcity.tool.UpdateUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class NewsCommentsListAdpater(
    val context: Context,
    val data: ArrayList<Map<String, String>>
) : RecyclerView.Adapter<NewsCommentsListAdpater.DefaultViewModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewModel {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_comments_list, parent, false)
        return DefaultViewModel(view)
    }

    override fun onBindViewHolder(holder: DefaultViewModel, position: Int) {
        val map = data[position]
        var like = map["likeNum"].toString().toInt()

        holder.nclUserName.text = "用户:${map["userName"].toString()}"
        holder.nclContent.text = map["content"].toString()
        holder.nclTime.text = map["commentDate"].toString()
        holder.nclLike.text = "赞${like}"

        holder.nclLike.setOnClickListener {
            val toInt = map["id"].toString().toInt()

            GlobalScope.launch(Dispatchers.Main) {
                val newsCommentString =
                    withContext(Dispatchers.IO) { MRString.getNewsCommentLike(context, toInt) }
                val newsCommentObj = DecodeJson.decodeNewsCommentAdd(newsCommentString)
                if (newsCommentObj == "") {
                    UpdateUI.toastUi(context, "点赞失败!")
                } else {
                    UpdateUI.toastUi(context, "+1")
                    holder.nclLike.text = "赞${++like}"
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class DefaultViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nclUserName = itemView.findViewById<TextView>(R.id.nclUserName)
        var nclContent = itemView.findViewById<TextView>(R.id.nclContent)
        var nclTime = itemView.findViewById<TextView>(R.id.nclTime)
        var nclLike = itemView.findViewById<TextView>(R.id.nclLike)
    }
}