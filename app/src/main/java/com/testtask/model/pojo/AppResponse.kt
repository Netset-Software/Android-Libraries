package com.testtask.model.pojo


class HomeResponse : ArrayList<HomeResponseItem>()

data class HomeResponseItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)