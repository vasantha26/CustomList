package com.example.customlist.retrofi

import com.example.customlist.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/CustomerDetails/GetCustomerDetails")
    suspend fun getCustomerDetails(
        @Query("pageno") pageNo: Int,
        @Query("pagesize") pageSize: Int,
        @Query("UnitId") unitId: Int
    ): ApiResponse
}
