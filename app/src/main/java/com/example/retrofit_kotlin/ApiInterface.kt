package com.example.retrofit_kotlin

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiInterface {

    @GET("sanpham")
    fun getData(): Call<List<DTO>>

    @PUT("sanpham")
    fun putData(@Body objU: DTO): Call<DTO>

    @DELETE("sanpham/{id}")
    fun delData(@Path("id") id : String ) : Call<DTO>

}

