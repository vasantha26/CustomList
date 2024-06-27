package com.example.customlist.model

data class ApiResponse(
    val statusCode: String,
    val statusMessage: String,
    val responseData: List<Customer>,
    val responseData1: PageInfo
)