package com.example.customlist.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.customlist.adapter.CustomerPagingSource
import com.example.customlist.model.Customer
import com.example.customlist.retrofi.RetrofitInstance
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest


class CustomerViewModel : ViewModel() {

    private val apiService = RetrofitInstance.api
    private val searchQuery = MutableLiveData("")


    @OptIn(ExperimentalCoroutinesApi::class)
    val customerPagingData: Flow<PagingData<Customer>> = searchQuery.asFlow().flatMapLatest { query ->
        Pager(PagingConfig(pageSize = 10)) {
            CustomerPagingSource(apiService, 787, query)
        }.flow.cachedIn(viewModelScope)
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }


}
