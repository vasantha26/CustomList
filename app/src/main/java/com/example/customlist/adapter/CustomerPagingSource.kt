package com.example.customlist.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.customlist.model.Customer
import com.example.customlist.retrofi.ApiService


import retrofit2.HttpException
import java.io.IOException

class CustomerPagingSource(
    private val apiService: ApiService,
    private val unitId: Int,
    private val searchQuery: String,
) : PagingSource<Int, Customer>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Customer> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getCustomerDetails(page, params.loadSize, unitId)

            val filteredData = if (searchQuery.isEmpty()) {
                response.responseData
            } else {
                response.responseData.filter { it.fullName().contains(searchQuery, ignoreCase = true) ||  it.mobileNo.contains(searchQuery, ignoreCase = true) }
            }



            LoadResult.Page(
                data = filteredData,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (filteredData.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Customer>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

