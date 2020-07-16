package com.kishor.omdbdemo

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.kishor.omdbdemo.databinding.ActivityMainBinding
import com.kishor.omdbdemo.databinding.ActivityMovieDescriptionBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_description.*
import org.json.JSONObject


class MovieDescriptionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMovieDescriptionBinding
    lateinit var model: MovieDataClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_description)

        val intent = intent
        val id = intent.getStringExtra("ID")

        buildURL(id)
    }

    private fun buildURL(id: String?) {

        val builder = Uri.Builder()
        builder.scheme("http")
            .authority("www.omdbapi.com")
            .appendPath("")
            .appendQueryParameter("apikey", "4d8bfb4")
            .appendQueryParameter("i", id)
        val myUrl = builder.build().toString()
        Log.d(ContentValues.TAG, "BuildMovieUrl: $myUrl")
        volleyStringRequest(myUrl)

    }

    private fun volleyStringRequest(url: String) {

        val REQUEST_TAG = "com.kishor.omdbdemo"


        val strReq =
            StringRequest(url, Response.Listener { response ->
                Log.d("kk", response!!)
                val jsonObject  = JSONObject(response)
                if (jsonObject.getString("Response").equals("False")) {
                    Toast.makeText(
                        this,
                        "Something wen wrong, Please try again..",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    try {
//                        txtName.text = JSONObject(response).getString("Title")

                        Glide.with(this)
                            .load(jsonObject.getString("Poster"))
                            .into(poster)

//                        binding.imageUrl = jsonObject.getString("Poster")
                        val data = MovieDataClass(
                            JSONObject(response).getString("Title"),
                            JSONObject(response).getString("Actors"),
                            JSONObject(response).getString("Plot")
                        )
                        binding.setVariable(BR.dataclass, data)
                        binding.executePendingBindings()


//                        txtActorName.text = JSONObject(response).getString("Actors")
//                        txtPlot.text = JSONObject(response).getString("Plot")
                    } catch (e: Exception) {
                    }
                }
            }, Response.ErrorListener { error ->
                VolleyLog.d(ContentValues.TAG, "Error: " + error.message)


            })
        AppSingleton.getInstance(this)?.addToRequestQueue(strReq, REQUEST_TAG)


    }
}