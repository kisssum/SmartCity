package com.kisssum.smartcity.adapter.allservice.film

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kisssum.smartcity.R
import com.kisssum.smartcity.state.film.FilmModel

class FilmChoiceSessionFilmListAdapater(val context: Context, val filmType: Int, val viewModel: FilmModel) : RecyclerView.Adapter<FilmChoiceSessionFilmListAdapater.MyViewHolder>() {
    private val IS_POPULAR = 0
    private val IS_UPCOMING = 1

    val data = when (filmType) {
        IS_POPULAR -> viewModel.getPopularData().value!!
        else -> viewModel.getUpcomingData().value!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.lsfcsflImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.list_style_film_choice_session_file_list, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            viewModel.getSessionFilmPosition().value = position
        }
    }

    override fun getItemCount() = data.size
}