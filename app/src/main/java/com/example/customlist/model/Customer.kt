package com.example.customlist.model

data class Customer(
    val customerId: Int,
    val fName: String,
    val lName: String,
    val mobileNo: String,
    val isCow: Int,
    val isBuffalo: Int
) {
    fun fullName() = "$fName $lName"
}
