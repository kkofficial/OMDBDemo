package com.kishor.omdbdemo

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.kishor.omdbdemo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var model: MovieViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        model  = ViewModelProviders.of(this).get( MovieViewModel::class.java  )


        btnSearch.setOnClickListener{
            if(et_search.text.isNotEmpty()) {

                try {
                    val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
                } catch (e: Exception) {
                }
                model.volleyStringRequest(et_search.text.toString().trim(), this)
            }else
                Toast.makeText(this,R.string.enter_movie_name,Toast.LENGTH_SHORT).show()
        }

        initObserver()

    }

    private fun initObserver() {

        model.listdata.observe(this, Observer {
            rvMoviesList.apply {
                layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                adapter = MovieAdapter(this@MainActivity, it)
            }
        })
    }


}

