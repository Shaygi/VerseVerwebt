package com.example.verseverwebt.api

import com.example.verseverwebt.user.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    fun getUsers(): Call<List<User>>

    @POST("users")
    fun createUser(@Body user: User): Call<User>

    @GET("users/rankings")
    fun getRankedUsers(): Call<List<User>>

    @PUT("users/calculate-rankings")
    fun calculateRankings(): Call<List<User>>

    @PUT("users/{id}/chapter/{chapter}/time")
    fun updateChapterTime(@Path("id") id: Long, @Path("chapter") chapter: Int, @Body time: Float): Call<User>

    @GET("users/{id}/chapter/{chapter}/time")
    fun getChapterTime(@Path("id") id: Long, @Path("chapter") chapter: Int): Call<Float>

    @PUT("users/{id}/intro")
    fun updateIntroCompleted(@Path("id") id: Long): Call<User>

    @GET("users/{id}/intro")
    fun getIntroCompleted(@Path("id") id: Long): Call<Boolean>

    @GET("users/name/{name}")
    fun checkIfExistsName(@Path("name") name: String): Call<Boolean>

    @GET("users/mail/{mail}")
    fun checkIfExistsMail(@Path("mail") mail: String): Call<Boolean>

}
