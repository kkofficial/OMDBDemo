package com.kishor.omdbdemo

import android.app.ProgressDialog
import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object SharingCode {
    lateinit var progressDialog: ProgressDialog
    fun showDailog(context : Context){
         progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Wait")
        progressDialog.setMessage("Loading data, please wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.show()

    }

    fun dismissDailog(){
        progressDialog.dismiss()
    }

}