package com.example.retrofit_kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserAdapter(var context: Context, var list: List<DTO>, var onclickSua: OnclickSua) : RecyclerView.Adapter<UserAdapter.MyAdapter>() {


   inner class MyAdapter(v : View): RecyclerView.ViewHolder(v){
        var tv_name =v.findViewById<TextView>(R.id.tv_name)
        var imageitem  = v.findViewById<ImageView>(R.id.img_image)
       var btnxoa = v.findViewById<Button>(R.id.btn_xoa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return MyAdapter(itemView)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        val dto = list[position]
       Glide.with(context).load(list[position].image).into(holder.imageitem)
        holder.tv_name.text =   list[position].name

        holder.btnxoa.setOnClickListener {
        onclickSua.Clicksua(dto)
        }
    }

    interface OnclickSua {
        fun Clicksua(dto: DTO)
    }

}