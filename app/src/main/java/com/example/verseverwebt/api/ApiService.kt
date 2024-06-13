package com.example.verseverwebt.api

import com.example.verseverwebt.user.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Body

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @POST("users")
    fun createUser(@Body user: User): Call<User>

    @GET("users/rankings")
    fun getRankedUsers(): Call<List<User>>

    @PUT("users/calculate-rankings")
    fun calculateRankings(): Call<List<User>>
}
