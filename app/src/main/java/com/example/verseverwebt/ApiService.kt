package com.example.verseverwebt

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @POST("users")
    fun createUser(@Body user: User): Call<User>
}
