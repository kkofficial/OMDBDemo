package com.kishor.omdbdemo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_list_rv_layout.view.*
import org.json.JSONException
import org.json.JSONObject


class MovieAdapter(var context: Context, private val listdata: ArrayList<String>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_rv_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = listdata[position]
        try {
            val jsonObject = JSONObject(parent)
            Glide.with(context)
                .load(jsonObject.getString("Poster"))
                .into(holder.moviePic)
            holder.title.text = jsonObject.getString("Title")
            holder.year.text = "Released  :" + jsonObject.getString("Year")

            holder.cvDescriptiom.setOnClickListener {
                val intent = Intent(context, MovieDescriptionActivity::class.java)
                intent.putExtra("ID", jsonObject.getString("imdbID"))
                context.startActivity(intent)
            }

        } catch (err: JSONException) {

        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePic: ImageView = itemView.movie_poster
        val title: TextView = itemView.title
        val year: TextView = itemView.year
        val cvDescriptiom: CardView = itemView.card_view
    }
}
