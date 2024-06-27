package com.example.customlist.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.customlist.R
import com.example.customlist.model.Customer


class CustomerAdapter :
    PagingDataAdapter<Customer, CustomerAdapter.CustomerViewHolder>(CustomerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = getItem(position)
        if (customer != null) {
            holder.bind(customer, position)
        }
    }

    class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val customerName: TextView = itemView.findViewById(R.id.customerName)
        private val customerPhone: TextView = itemView.findViewById(R.id.customerPhone)
        private val customerImage: ImageView = itemView.findViewById(R.id.customerImage)

        fun bind(customer: Customer, position: Int) {
            customerName.text = customer.fullName()
            customerPhone.text = customer.mobileNo
            customerName.setTextColor(
                when (position % 3) {
                    0 -> Color.RED
                    1 -> Color.GREEN
                    else -> Color.BLUE
                }
            )
            customerImage.setImageResource(if (customer.isCow == 1) R.drawable.cow else R.drawable.bufflo)
        }
    }


    class CustomerDiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.customerId == newItem.customerId
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem == newItem
        }
    }
}

