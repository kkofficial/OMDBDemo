package com.kishor.omdbdemo

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class MovieViewModel : ViewModel() {
    var listdata = MutableLiveData<ArrayList<String>>()
    var data: ArrayList<String> = ArrayList()

    fun volleyStringRequest(movieName: String, context: Context) {

        // this conditon we can add when we want to fetch data only once
//        if (data.size >0) {
//            return data
//        } else {
        SharingCode.showDailog(context)
        val builder = Uri.Builder()
        builder.scheme("http")
            .authority("www.omdbapi.com")
            .appendPath("")
            .appendQueryParameter("apikey", "4d8bfb4")
            .appendQueryParameter("s", movieName)
        val url = builder.build().toString()
        val REQUEST_TAG = "com.kishor.omdbdemo"

        val strReq =
            StringRequest(url, Response.Listener { response ->
                Log.d("kk", response!!)
                val jsonObject = JSONObject(response)

                if (jsonObject.getString("Response").equals("False")) {
                    Toast.makeText(
                        context,
                        "Something wen wrong, Check Movie Name",
                        Toast.LENGTH_SHORT
                    ).show()

                    SharingCode.dismissDailog()
                } else {
                    data.clear()
                    val jsonArray = jsonObject.getJSONArray("Search")
                    for (i in 0 until jsonArray.length()) {

                        data.add(jsonArray.getString(i))

                       // setData(data)

                    }
                    listdata.value?.clear()
                    listdata.value = data
                    SharingCode.dismissDailog()
                }

            }, Response.ErrorListener { error ->
                VolleyLog.d(ContentValues.TAG, "Error: " + error.message)


            })
        AppSingleton.getInstance(context)?.addToRequestQueue(strReq, REQUEST_TAG)


    }

//    }

}



