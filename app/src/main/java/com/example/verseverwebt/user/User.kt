package com.example.verseverwebt.user

data class User(
    val id: Long,
    val name: String,
    val email: String,
    //val password: String,
    val time1: Float,
    val time2: Float,
    val time3: Float,
    val time4: Float,
    val time5: Float,
    var rank: Int
)
