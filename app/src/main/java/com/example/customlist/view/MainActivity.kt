package com.example.customlist.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customlist.R
import com.example.customlist.adapter.CustomerAdapter
import com.example.customlist.databinding.ActivityMainBinding
import com.example.customlist.viewmodel.CustomerViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var  viewModel: CustomerViewModel
    private lateinit var adapter: CustomerAdapter
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView( this,R.layout.activity_main)

        viewModel = ViewModelProvider(this)[CustomerViewModel::class.java]

        setSupportActionBar(binding.toolbar)



        setupRecyclerView()


    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun setupRecyclerView() {

        adapter = CustomerAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        GlobalScope.launch {
            viewModel.customerPagingData.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }


        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }


        binding.searchEditText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.setSearchQuery(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.setSearchQuery(it) }
                return true
            }
        })
    }


}

